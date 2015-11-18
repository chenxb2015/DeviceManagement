var express    = require("express");
var mysql      = require('mysql');
var querystring = require('querystring');

var connection = mysql.createConnection({
  host     : 'localhost',
  user     : 'root',
  password : '',
  database : 'dm'
});
var app = express();


connection.connect(function(err){
if(!err) {
    console.log("Database is connected ... \n\n");  
} else {
    console.log("Error connecting database ... \n\n");  
}
});

app.get("/dm/users",function(req,res){
	connection.query('SELECT * from user', function(err, rows, fields) {	
	  if (!err){
		  res.json(generateJsonResult(true,200,"",sortUsers(rows)));
	  }	else{
		  res.json(generateJsonResult(false,500,"get user error, please contact admin",[]));
	  } 
	});
});

function sortUsers(rows){
	var users = [];
	for(var i=0;i<rows.length;i++){
	   var user = {'name':rows[i]["name"], 'email':rows[i]["email"]};
	   users.push(user);
	}
	return users;
}

app.post("/dm/addUser",function(req,res){
	var queryData = ""
	req.on('data', function(data) {
            queryData += data;
    });

	req.on('end', function() {
            var postData = querystring.parse(queryData);	
			var name = postData['name'];
			var email = postData['email'];
			var password = postData['password'];
			
			if( email == null || email.trim() == "" || name == null || name.trim()==""||password==null||password.trim()==""){
				res.json(generateJsonResult(false, 402, "name, email, password can not be empty", []));
			}else{
				connection.query('SELECT * from user where email = ?', [email], function(err, rows, fields) {
					  if (!err){		
						if( rows.length == 0 ){
							connection.query('INSERT INTO user SET name=?,password=md5(?), email=?, role=2', [name, password, email], function(err, rows, fields){
							  if(err){
								 res.json(generateJsonResult(false, 500, "system error, please contact admin", []));
							  } else{
								 res.json(generateJsonResult(true, 200, "add successfully", []));
							  }
							});
						}else{
							res.json(generateJsonResult(false,407,"email already exist",[]));
						}				
					  }	else{
						  res.json(generateJsonResult(false,500,"get user error, please contact admin",[]));
					  } 
				});	
			}			
    });	
});

app.delete("/dm/deleteUser",function(req,res){
	var email = req.query.email;	
	if( email == null || email.trim() == ""){
		res.json(generateJsonResult(false, 405, "email can not be empty", []));
	}else{										
		connection.query('delete from user where email =?', [email], function(err, rows, fields){
		  if(err){
			 res.json(generateJsonResult(false, 500, "system error, please contact admin", []));
		  } else{
			 res.json(generateJsonResult(true, 200, "delete successfully", []));
		  }
		});		
	};	
});



app.post("/dm/registerDevice",function(req,res){
	var queryData = ""
	req.on('data', function(data) {
            queryData += data;
    });

	req.on('end', function() {
            var postData = querystring.parse(queryData);
			postData["status"] = "available";
			postData["owner"] = "1";
			postData["registerdate"] = new Date();
		
			var serialNum = postData['serialNumber']
		
			if(serialNum == null || serialNum.trim() == ""){
				res.end("serial num is empty");
			}else{					
					connection.query('SELECT * from device where serialNumber = ?', [serialNum], function(err, rows, fields) {
						  if (!err){				  
							  if(rows.length == 0){
									connection.query('INSERT INTO device SET ?', postData, function(err, rows, fields){
									  if(err){										
										  res.json(generateJsonResult(false, 500, "register error", []));
									  } else{
												if(rows.length == null){
													 res.json(generateJsonResult(false, 406, "insert borrow device error ,please contact admin", []));
												}else{
													var deviceId = rows["insertId"];
													var borrowedDate = new Date();			
													var borrowData = {"deviceId":deviceId, "borrowerId":1, "borrowedDate":borrowedDate};												
													connection.query('INSERT INTO device_borrow SET ?', borrowData, function(err, rows, fields){
													  if(err){
														 res.json(generateJsonResult(false, 406, "register successful, but can not initialize borrow", []));
													  } else{
														 res.json(generateJsonResult(true, 200, "register successfully", []));													
													  }
													});
												
												}									 
									  }
									});
							  }else{					
									res.json(generateJsonResult(false, 506, "device has been registerd already!", []));
							  }			
						  }	else{
							res.json(generateJsonResult(false, 402, "query with serial num error'!", []));
						  } 
					 });
			}
    });	
});

app.get("/dm/devices",function(req,res){
	connection.query('SELECT d.id, u.name owner, d.registerName, d.deviceType, d.deviceMode,d.imei,d.serialNumber,d.os,d.osVersion,d.status from device d, user u where d.owner=u.id', function(err, rows, fields) {
		  if (!err){
			  var devices = rows;
			  connection.query('select u.name borrowerName,u.email borrowerEmail, db.deviceId deviceId from (select * from device_borrow where id in (select max(id) from device_borrow group by deviceId))  db, user u where db.borrowerId=u.id;', [], function(err, rows, fields){
				  if(err){
					 res.json(generateJsonResult(false, 500, "query devices error,please contact admin", []));
				  } else{
				    initialBorrowDevices(devices);
					
					for(var i=0;i<rows.length;i++){
					   var deviceId = rows[i]["deviceId"];
					  
					   for(var j=0;j<devices.length;j++){
							var id = devices[j]["id"];
							if(deviceId == id){
								
								devices[j]["borrowedBy"] = rows[i]["borrowerName"];
								break;
							}
					   }		   
					}
					 res.json(generateJsonResult(true, 200, "", devices));
				  }
				});
		  }	else{		
			res.json(generateJsonResult(false, 500, "can not get devices, please contact admin", []));
		  } 	   
	  });
});

function initialBorrowDevices(rows){
	var devices = [];
	for(var i=0;i<rows.length;i++){
	   rows[i]["borrowedBy"] = "Nobody";
	   devices.push(rows[i]);
	}
	return devices;
}


app.post("/dm/borrowDevice",function(req,res){
	var queryData = ""
	req.on('data', function(data) {
            queryData += data;
    });

	req.on('end', function() {
            var postData = querystring.parse(queryData);
			var pinCode = postData['pinCode']
			var email = postData['email']
			var serialNum = postData['serialNumber']	
			
			if(serialNum == null || serialNum.trim() == ""){
				res.json(generateJsonResult(false, 406, "serial num is empty, can not borrow!", []));
			}else{					
					connection.query('SELECT * from user where email = ? AND password = md5(?)', [email, pinCode], function(err, rows, fields) {
						  if (!err){
							  if(rows.length == 0){
									res.json(generateJsonResult(false, 406, "your pinCode is not right!", []));
							  }else{
									var userId = rows[0]["id"]
									connection.query('select * from device where serialNumber=?', serialNum, function(err, rows, fields){
									  if(err){				
										res.json(generateJsonResult(false, 500, "query device error, please contact admin", []));
									  } else{
											if(rows.length == 0){
												res.json(generateJsonResult(false, 406, "can not find device according to serialNum you provided!", []));					
											}else{
												var deviceId = rows[0]["id"];
												var borrowedDate = new Date();
												var borrowData = {"deviceId":deviceId, "borrowerId":userId, "borrowedDate":borrowedDate};
												
												connection.query('INSERT INTO device_borrow SET ?', borrowData, function(err, rows, fields){
												  if(err){
													 res.json(generateJsonResult(false, 406, "borrow error", []));
												  } else{
													 res.json(generateJsonResult(true, 200, "borrow successfully", []));
												  }
												});
											}				  
									  }
									});
							  }	
						  }	else{
							res.json(generateJsonResult(false, 405, "query email passwrod error", []))
						  } 
					 });
			}
    });	
});

app.get("/dm/deviceStatus",function(req,res){
	connection.query('select u.name borrowerName,u.email borrowerEmail, d.registerName deviceName,d.deviceMode deviceMode , d.os, d.osVersion, d.serialNumber ,d.status from (select * from device_borrow where id in (select max(id) from device_borrow group by deviceId))  db, user u, device d where db.borrowerId=u.id AND db.deviceId = d.id;', function(err, rows, fields) {
		  if (!err){
			res.json(generateJsonResult(true, 200, "", rows))
		  }	else{
			res.json(generateJsonResult(false, 405, "can not get device_borrow", []))
		  } 	   
	  });
});

app.post("/dm/adminLogin",function(req,res){
	var queryData = ""
	req.on('data', function(data) {
            queryData += data;
    });

	req.on('end', function() {
            var postData = querystring.parse(queryData);
			var password = postData['password'];
			var email = postData['email']
					connection.query('SELECT * from user where email = ? AND password = md5(?) AND role=1', [email, password], function(err, rows, fields) {
						  if (!err){
							  if(rows.length == 0){
									res.json(generateJsonResult(false, 501, "check username and pass if you are an admin", []));;									
							  }else{
									res.json(generateJsonResult(true, 200, "login successfully", []));;
							  }
							  				
						  }	else{
							res.json(generateJsonResult(fale, 500, "query email password error", []));
						  } 
					 });
    });	
});

app.get("/dm/deviceHistory",function(req,res){
	var serialNum = req.query.serialNum;
	connection.query('select u.name borrowedBy,db.borrowedDate from device_borrow db, user u, device d where db.deviceId=d.id AND db.borrowerId=u.id AND serialNumber = ?', [serialNum],function(err, rows, fields) {
		  if (!err){
			  res.json(generateJsonResult(true,200,"",rows));
		  }	else{
			  res.json(generateJsonResult(false,500,"get history error, please contact admin",[]));
		  } 
	  });
});

function generateJsonResult(success, code, msg, data){
    return {"success":success, "code":code, "msg":msg, "data":data};
}

app.listen(3001);