package com.chen.helper;

import java.io.IOException;

import android.util.Log;

import com.chen.dmandroid.DeviceResponse;
import com.chen.dmandroid.HistoryResponse;
import com.chen.dmandroid.ResponseWithoutData;
import com.chen.dmandroid.UserResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {
	static ObjectMapper objectMapper = new ObjectMapper();
	
	public static UserResponse convertToUserResponse(String generalSettingJsonSetting){
	
		UserResponse userResponse = null;
		try {
			userResponse = objectMapper.readValue(generalSettingJsonSetting, UserResponse.class);
		} catch (JsonParseException e) {
			Log.e("jackson", "Exception on JsonParseException in convertToGeneralSetting", e);
		} catch (JsonMappingException e) {
			Log.e("jackson","Exception on JsonMappingException in convertToGeneralSetting", e);
		} catch (IOException e) {
			Log.e("jackson","Exception on IOException in convertToGeneralSetting", e);
		}
		return userResponse;
	}
	
	public static DeviceResponse convertToDeviceResponse(String generalSettingJsonSetting){
		
		DeviceResponse deviceResponse = null;
		try {
			deviceResponse = objectMapper.readValue(generalSettingJsonSetting, DeviceResponse.class);
		} catch (JsonParseException e) {
			Log.e("jackson", "Exception on JsonParseException in convertToGeneralSetting", e);
		} catch (JsonMappingException e) {
			Log.e("jackson","Exception on JsonMappingException in convertToGeneralSetting", e);
		} catch (IOException e) {
			Log.e("jackson","Exception on IOException in convertToGeneralSetting", e);
		}
		return deviceResponse;
	}
	
public static ResponseWithoutData convertToResponseWithoutData(String generalSettingJsonSetting){
		
	ResponseWithoutData responseWithoutData = null;
		try {
			responseWithoutData = objectMapper.readValue(generalSettingJsonSetting, ResponseWithoutData.class);
		} catch (JsonParseException e) {
			Log.e("jackson", "Exception on JsonParseException in convertToGeneralSetting", e);
		} catch (JsonMappingException e) {
			Log.e("jackson","Exception on JsonMappingException in convertToGeneralSetting", e);
		} catch (IOException e) {
			Log.e("jackson","Exception on IOException in convertToGeneralSetting", e);
		}
		return responseWithoutData;
	}

public static HistoryResponse convertToHistoryResponse(String generalSettingJsonSetting){
	
	HistoryResponse hr = null;
		try {
			hr = objectMapper.readValue(generalSettingJsonSetting, HistoryResponse.class);
		} catch (JsonParseException e) {
			Log.e("jackson", "Exception on JsonParseException in convertToGeneralSetting", e);
		} catch (JsonMappingException e) {
			Log.e("jackson","Exception on JsonMappingException in convertToGeneralSetting", e);
		} catch (IOException e) {
			Log.e("jackson","Exception on IOException in convertToGeneralSetting", e);
		}
		return hr;
	}

	
}
