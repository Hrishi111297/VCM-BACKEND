package com.bill.tech.constants;

import lombok.NoArgsConstructor;
import static lombok.AccessLevel.PRIVATE;
@NoArgsConstructor(access = PRIVATE)
public class ApiConstants {
	public static final String SEPERATOR = "/";
	public static final String API = "api";
	public static final String VERSION = "v1";
	public static final String BASE = SEPERATOR + API + SEPERATOR + VERSION + SEPERATOR;
	
	public static final String USER = "user";
	public static final String STUDENT = "student";
	public static final String ADMIN = "admin";
	
//User
	public static final String USER_BASE =BASE+ SEPERATOR + USER;
	
//Student
	public static final String STUDENT_BASE =BASE+ SEPERATOR + STUDENT;
	
	//Admin
public static final String ADMIN_BASE =BASE+ SEPERATOR + ADMIN;

//Login PAGE
public static final String AUTH = BASE + "auth" + SEPERATOR;
public static final String LOGIN = SEPERATOR + "login";
public static final String REGISTER = SEPERATOR + "register";
public static final String CAPTCHA = SEPERATOR + "captcha";

//change password page
public static final String CHANGEPASSWORD = SEPERATOR +"changePassword/{email}/{otp}";
public static final String VERIFY_OTP = SEPERATOR+"verifyOtp/{emailId}";

//Profile
public static final String PROFILE = BASE + "profile" + SEPERATOR;
public static final String UPLOAD_PROFILE = SEPERATOR+"upload-profile/{userId}";
public static final String UPLOAD_OTHER_DOCUMENT = SEPERATOR+"upload-other";
public static final String RETRIVE_DOCS = SEPERATOR+"retirveDocs";
public static final String RETRIVE_PROFILE_PHOTO=SEPERATOR+"profile-picture/{id}";
public static final String UPDATE_PERSONAL_DETAILS=SEPERATOR+"update-profile";

public static final String UPDATE_ADDRESS=SEPERATOR+"update_address";
public static final String UPDATE_GAURDIAN_DETAILS=SEPERATOR+"update-gaurdian";
public static final String UPDATE_EDUCATION_DETAILS=SEPERATOR+"update-education";

//Category Constants
public static final String CATEGORY = BASE + "category" + SEPERATOR;
public static final String CREATE_CATEGORY = SEPERATOR + "create-category";
public static final String UPDATE_CATEGORY = SEPERATOR + "update-category/{id}";
public static final String DELETE_CATEGORY = SEPERATOR + "delete-category/{id}";
public static final String GET_CATEGORY = SEPERATOR + "get-category/{id}";
public static final String GET_ALL_CATEGORIES = SEPERATOR + "get-all-categories";


// Course Constants
public static final String COURSE = BASE + "course" + SEPERATOR;
public static final String CREATE_COURSE = SEPERATOR + "create-course";
public static final String UPDATE_COURSE = SEPERATOR + "update-course/{id}";
public static final String DELETE_COURSE = SEPERATOR + "delete-course/{id}";
public static final String GET_COURSE = SEPERATOR + "get-course/{id}";
public static final String GET_ALL_COURSES = SEPERATOR + "get-all-courses";
public static final String GET_COURSES_BY_CATEGORY = SEPERATOR + "get-courses-by-category/{categoryId}";
public static final String GET_COURSES_BY_STATUS = SEPERATOR + "get-courses-by-status/{status}";

//Course Constants
public static final String KEY_VALUE = BASE + "Key_value" + SEPERATOR;
public static final String CREATE_KEY_VALUE = SEPERATOR + "Key_value";
public static final String DELETE_KEY_VALUE = SEPERATOR + "delete-Key_value/{id}";
public static final String GET__KEY_VALUE= SEPERATOR + "get-Key_value/{key}";
public static final String GET_KEY_VALUE_ALL = SEPERATOR + "get-all-Key-values";

}
