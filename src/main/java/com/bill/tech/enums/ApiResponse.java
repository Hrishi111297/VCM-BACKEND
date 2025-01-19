package com.bill.tech.enums;
import com.bill.tech.constants.ApiResponseKeyConstant;
public enum ApiResponse {
DATA(ApiResponseKeyConstant.DATA),TOKEN(ApiResponseKeyConstant.TOKEN),MESSAGE(ApiResponseKeyConstant.MESSAGE),SUCCESS(ApiResponseKeyConstant.SUCCESS);
String data;
	ApiResponse(String data1){
		data=data1;
	}
}
