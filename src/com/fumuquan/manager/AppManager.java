package com.fumuquan.manager;

import java.util.ArrayList;
import java.util.Date;


public class AppManager {
	private static AppManager appManager = null;
	private static Object INSTANCE_LOCK = new Object();
	
	static public AppManager getInstance(){
		if(null == appManager){
			synchronized(INSTANCE_LOCK){
				if(null == appManager){
					appManager = new AppManager();
				}
			}
		}
		
		return appManager;
	}
	
	/********************服务通信用到***************************************/
	public static final int HTTP_SERVICE_ERROR_EXCEPTION = 401;
	public static final int NET_EXCEPTION = 402;
	public static final int SERVER_RESPONSE_EXCEPTION = 403;
	public static final int RESOLVE_EXCEPTION = 404;
	
	public static final int IS_NEW_VERSION = 405;
	public static final int UPDATE_GET_VERSION_FAILED = 407;		//获取版本信息失败
	
	public static final int SAVE_DATA_FINISH = 406;
	public static final int UPDATE_UNFINISH = 408;
	public static final int UPDATE_FAILED = 409;		//更新失败

	public static final int SERVER_DATA_IS_NULL = 410;
	public static final int UNKNOW_EXCEPTION = 411;
	public static final int RESOURCE_NOT_FIT = 412;
	
	public static final int UPDATE_RES_UPDATE_SERVER_FIRST = 413;	//需要先更新服务器IP，才能更新资源
	
	public static final int DISMISS_PROGRESS_DIALOG = 414;			//延时消失progress dialog
	
	//提交数据
	public static final int SUBMIT_DATA_SUCCESS = 420;				//新建或者修改数据提交到服务器成功
	public static final int RETRY_SUBMIT_DATA = 421;				//重新提交数据
	public static final int SUBMIT_DATA_FAILED = 422;				//提交数据到服务器失败
	public static final int START_SUBMIT_DATA = 423;				//开始提交数据
	public static final int DATA_2_JSON_FAILED = 424;				//数据序列化失败
	public static final int SUBMIT_DATA_TABLE_NOT_FREE = 425;		//桌台已被占用
	public static final int SUBMIT_BILL_ID_NOT_EXIST = 426;			//订单ID不存在
	//Get获取数据
	public static final int GET_DATA_START = 430;		//开始
	public static final int GET_DATA_RETRY = 431;
	public static final int GET_DATA_FAILED = 432;
	public static final int GET_DATA_SUCCESS = 433;
	//post方式
	public static final int POST_DATA_START = 440;
	public static final int POST_DATA_RETRY = 441;
	public static final int POST_DATA_FAILED = 442;
	public static final int POST_DATA_SUCCESS = 443;
	//登录
	public static final int LOGIN_SUCCESS = 450;		//登录成功
	public static final int LOGIN_FAILED = 451;			//登录失败
	//获取验证码
	public static final int GET_VALIDATION_SUCCESS = 460;	//获取验证码成功
	public static final int REGISTER_SUCCESS = 461;			//注册成功
	//加载初始信息成功
	public static final int LOAD_DATA_SUCCESS = 800;		//加载集团和门店数据成功
	public static final int LOAD_DISH_CATEGORIES_SUCCESS = 801;	//加载菜品数据
	/*************************更新APK*********************************************/
	public static final int HTTP_SERVICE_ERROR_EXCEPTION_BY_APK = 500;
	public static final int NET_EXCEPTION_BY_APK = 501;
	public static final int SERVER_RESPONSE_EXCEPTION_BY_APK = 502;
	public static final int RESOLVE_EXCEPTION_BY_APK = 503;
	public static final int UPDATE_APK_PROGRESS = 504;
	public static final int UPDATE_APK_EMPTY = 505;
	public static final int UPDATE_APK_SUCCESS = 506;
	public static final int UPDATE_APK_FAIL = 507;
	public static final int APK_NEED_UPDATE = 508;
	public static final int APK_NOT_NEED_UPDATE = 509;
	/**************************************************************************/
	
	public static final String GENDER_MALE_LABLE = "先生";			//男 称谓
	public static final String GENDER_FEMALE_LABLE = "女士";			//女 称谓
	
	
	
	
	
	public static Date selectDate;
}
