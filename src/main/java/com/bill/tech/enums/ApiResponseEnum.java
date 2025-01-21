package com.bill.tech.enums;
import com.bill.tech.constants.ApiResponseKeyConstant;
public enum ApiResponseEnum {
DATA(ApiResponseKeyConstant.DATA),TOKEN(ApiResponseKeyConstant.TOKEN),MESSAGE(ApiResponseKeyConstant.MESSAGE),SUCCESS(ApiResponseKeyConstant.SUCCESS);
String data;
	ApiResponseEnum(String data1){
		data=data1;
	}
}
