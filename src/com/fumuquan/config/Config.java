package com.fumuquan.config;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

public class Config {
	private static Config mConfig = null;
	private static Object INSTANCE_LOCK = new Object();
	
	public static Config getInstance(){
		if(null == mConfig){
			synchronized(INSTANCE_LOCK){
				if(null == mConfig){
					mConfig = new Config();
				}
			}
		}
		
		return mConfig;
	}
	
	
	private static final String APP_FOLDER = "FMQFolder";
	private static final String APP_DATA_FOLDER = "Data";
	private static final String APP_CACHE_FOLDER = "Cache";
	private static final String APP_LOG_FOLDER = "Log";
	private static final String APK_NAME = "fmq.apk";

	public String sAppFolderPath;	
	public String sAppDataPath;		//原始数据目录
	public String sAppCatchPath;	//缓存目录
	public String sAppLogPath; 		//日志目录
	public String sApkPath;			//APK文件的路径
	
	private static final String URL_HTTP="http://fumuquan.info/";
	public static String URL_UPDATE_APK;
	
	private static String CONFIG_FILE_NAME = "config";
	private static String CONFIG_FIRST_LAUNCH = "firstLaunch";

	private Config(){
		
	}
	
	//初始化路径
	private void initPath(){
		//判断是否挂载了SD卡
		String storageState = Environment.getExternalStorageState();
		if(!storageState.equals(Environment.MEDIA_MOUNTED)){
			return;
		}
		
		String root = Environment.getExternalStorageDirectory().getPath();

		sAppFolderPath = root + File.separator + APP_FOLDER + File.separator; //
		sAppDataPath = sAppFolderPath + APP_DATA_FOLDER + File.separator; //
		sAppLogPath = sAppFolderPath + APP_LOG_FOLDER + File.separator; //
		sAppCatchPath = sAppFolderPath + APP_CACHE_FOLDER + File.separator;		//缓存目录
		sApkPath = sAppDataPath + APK_NAME;
	}
	
	//初始化URL
	private void initUrl(){
		StringBuilder sb = new StringBuilder();
		sb.append(URL_HTTP).append("API/updateAPK");
		URL_UPDATE_APK = sb.toString();
	}

	public static boolean isFirstLaunch(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				CONFIG_FILE_NAME, Activity.MODE_PRIVATE);
		return sharedPreferences.getBoolean(CONFIG_FIRST_LAUNCH, true);
	}

	public static void changeFirstLaunch(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				CONFIG_FILE_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(CONFIG_FIRST_LAUNCH, false);
		editor.commit();
	}

	public static void clearFirstLaunch(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				CONFIG_FILE_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(CONFIG_FIRST_LAUNCH, true);
		editor.commit();
	}


}
