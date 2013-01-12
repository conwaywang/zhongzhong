package com.xinwen121.zhongzhong.config;

import java.io.File;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

public class Config {
	public static final String MENU_FOLDER = "MenuFolder";
	public static final String MENU_UNZIP_FOLDER = "MenuData";
	public static final String APK_NAME = "j.apk";
	public static final String SKIN_NAME = "skin97.apk";
	public static final String MENU_CECHE = "Cache";

	public static int local_version = 1;

	public static String SERVER_IP;
	public static String HTTP_URL;

	public static boolean DEBUG = false;

	public static String SYSTEM_VERSION;

	public static String SCREEN_SIZE;

	public static String URL_CHECK_RESOURCE_UPDATE;
	public static String URL_GET_ALL_FOOD;
	public static String URL_GET_FOOD_IMAGE;
	public static String URL_GET_All_FOODPAGE_INFO;
	public static String URL_GET_ALL_FOODCATEGORY_INFO;
	public static String URL_GET_ALL_ROOM_INFO;
	public static String URL_GET_ALL_WAITER_INFO;
	public static String URL_GET_SHOP_INFO;
	public static String URL_GET_ORDER_INFO;
	public static String URL_CREATE_NEW_ORDER;
	public static String URL_DELETE_FOOD;
	public static String URL_GET_SESSION_ID;
	//
	public static String URL_GET_SESSION;
	public static String URL_BILL_UP;
	public static String URL_PREPARE_BILL;
	public static String URL_GET_SPACE_STATE;
	public static String URL_UPDATE_APK;
	public static String URL_UPDATE_SKIN;
	public static String URL_GET_SET_MEAL_RELATION;
	public static String URL_GET_ALL_REALTIMEFOOD;
	public static String URL_CHECK_REALTIMEFOOD_UPDATE;
	public static String URL_CHECK_APK_VERSION;
	public static String URL_GET_ALL_SURVEY;
	public static String URL_CREAT_SURVEY_ANSWER;
	public static String URL_GET_CHECK_PACKAGEDISH;

	public static String URL_GET_ALL_PACKAGEDISHCATEGORY;

	public static String URL_GET_All_PACKAGEFOODRELATION;

	public static String sMenuFolderPath;
	public static String sMenuUnZipFolderPath;
	public static String sMenuCachePath;

	public static String APK_PATH;

	public static String SKIN_PATH;
	static {
		String root = Environment.getExternalStorageDirectory()
				.getAbsolutePath();

		/**
		 * android 4.1版本中 /mnt/sdcard 已经link为/storage/sdcard0， 不能再在前面添加/mnt。
		 */
		// if (!root.contains("mnt")) {
		// root = "/mnt" + root;
		// }

		sMenuFolderPath = root + File.separator + MENU_FOLDER + File.separator; // mnt/sdcard/MenuFolder
		sMenuUnZipFolderPath = sMenuFolderPath + MENU_UNZIP_FOLDER
				+ File.separator; // mnt/sdcard/MenuFolder/MenuData
		sMenuCachePath = sMenuFolderPath + MENU_CECHE + File.separator;
		APK_PATH = sMenuFolderPath + APK_NAME;

		SKIN_PATH = sMenuFolderPath + SKIN_NAME;
	}

	public static boolean isFirstLaunch(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"Config", Activity.MODE_PRIVATE);
		return sharedPreferences.getBoolean("isFirstLaunch", true);
	}

	public static void changeFirstLaunch(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"Config", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean("isFirstLaunch", false);
		editor.commit();
	}

	public static void clearFirstLaunch(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"Config", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean("isFirstLaunch", true);
		editor.commit();
	}

	public static String getServerIp(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"Config", Activity.MODE_PRIVATE);
		return sharedPreferences.getString("ServerIp", "192.168.0.105:8731");
	}

	public static void setServerIp(Context context, String ip) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"Config", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("ServerIp", ip);
		editor.commit();
	}

	public static String getLastUpdateDate(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"Config", Activity.MODE_PRIVATE);
		return sharedPreferences.getString("LastUpdateDate", null);
	}

	public static void setLastUpdateDate(Context context, String date) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"Config", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("LastUpdateDate", date);
		editor.commit();
	}

	public static int getRealTimeVersion(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"Config", Activity.MODE_PRIVATE);
		return sharedPreferences.getInt("RealTimeVersion", 0);
	}

	public static void setRealTimeVersion(Context context, int version) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"Config", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt("RealTimeVersion", version);
		editor.commit();
	}

	public static void initConfig(Context context) {
		Config.SERVER_IP = Config.getServerIp(context);

		setURL();
	}

	public static void setURL() {
		
		if (DEBUG) {
			SERVER_IP = "192.168.1.4:8731";
		}
		StringBuilder http = new StringBuilder();
		http.append("http://").append(SERVER_IP);

		// http.append("http://").append("192.168.0.107");
		HTTP_URL = http.toString();

		StringBuilder sb1 = new StringBuilder();
		sb1.append(HTTP_URL).append("/MobileService/Json/GetUpdateVersion");
		URL_CHECK_RESOURCE_UPDATE = sb1.toString();

		StringBuilder sb2 = new StringBuilder();
		sb2.append(HTTP_URL).append("/MobileService/Json/GetAllFood");
		URL_GET_ALL_FOOD = sb2.toString();

		StringBuilder sb3 = new StringBuilder();
		sb3.append(HTTP_URL).append("/MobileService/GetImage?fileName=");
		URL_GET_FOOD_IMAGE = sb3.toString();

		StringBuilder sb4 = new StringBuilder();
		sb4.append(HTTP_URL).append("/MobileService/Json/GetAllFoodPage");
		URL_GET_All_FOODPAGE_INFO = sb4.toString();

		StringBuilder sb5 = new StringBuilder();
		sb5.append(HTTP_URL).append("/MobileService/Json/GetAllFoodCategory");
		URL_GET_ALL_FOODCATEGORY_INFO = sb5.toString();

		StringBuilder sb6 = new StringBuilder();
		sb6.append(HTTP_URL).append("/MobileService/Json/GetAllRoom");
		URL_GET_ALL_ROOM_INFO = sb6.toString();

		StringBuilder sb7 = new StringBuilder();
		sb7.append(HTTP_URL).append("/MobileService/Json/GetAllWaiter");
		URL_GET_ALL_WAITER_INFO = sb7.toString();

		StringBuilder sb8 = new StringBuilder();
		sb8.append(HTTP_URL).append("/MobileService/Json/GetShopInfo");
		URL_GET_SHOP_INFO = sb8.toString();

		StringBuilder sb9 = new StringBuilder();
		sb9.append(HTTP_URL).append("/MobileService/Json/GetBill?spaceId=");
		URL_GET_ORDER_INFO = sb9.toString();

		StringBuilder sb10 = new StringBuilder();
		sb10.append(HTTP_URL).append("/MobileService/Json/CreateBill");
		URL_CREATE_NEW_ORDER = sb10.toString();
		if(DEBUG)
		{
			URL_CREATE_NEW_ORDER="http://192.168.1.4:8731/MobileService/Json/CreateBill";
		}

		StringBuilder sb11 = new StringBuilder();
		sb11.append(HTTP_URL).append("/MobileService/Json/RemoveFood?");
		URL_DELETE_FOOD = sb11.toString();

		StringBuilder sb12 = new StringBuilder();
		sb12.append(HTTP_URL).append("/MobileService/Json/GetSessionId");
		URL_GET_SESSION_ID = sb12.toString();

		StringBuilder sb = new StringBuilder();
		sb.append(HTTP_URL).append("/MobileService/Json/GetSession");
		URL_GET_SESSION = sb.toString();

		StringBuilder sb13 = new StringBuilder();
		sb13.append(HTTP_URL).append("/MobileService/Json/RequireBill?");
		URL_BILL_UP = sb13.toString();

		StringBuilder sb14 = new StringBuilder();
		sb14.append(HTTP_URL).append("/MobileService/Json/PrepareBill?");
		URL_PREPARE_BILL = sb14.toString();

		StringBuilder sb15 = new StringBuilder();
		sb15.append(HTTP_URL).append(
				"/MobileService/Json/GetSpaceState?spaceId=");
		URL_GET_SPACE_STATE = sb15.toString();

		StringBuilder sb16 = new StringBuilder();
		String ip = Config.SERVER_IP;
		if (DEBUG) {
			ip = "192.168.0.117";// 测试
		} else {
			if(null!=Config.SERVER_IP)
			{
				int index =Config.SERVER_IP.lastIndexOf(":");
				if(index!=-1)
				{
					ip = Config.SERVER_IP.substring(0,
							Config.SERVER_IP.lastIndexOf(":"));
				}
			}
		}
		sb16.append("http://" + ip + ":8732");
		sb16.append("/Service/GetAndroidApp?sysVersion="
				+ Config.SYSTEM_VERSION + "&screenSize=" + Config.SCREEN_SIZE
				+ "&appType=Android");
		URL_UPDATE_APK = sb16.toString();

		StringBuilder sb17 = new StringBuilder();
		sb17.append(HTTP_URL).append("/MobileService/Json/GetAllRelations");
		URL_GET_SET_MEAL_RELATION = sb17.toString();

		StringBuilder sb18 = new StringBuilder();
		sb18.append(HTTP_URL).append("/MobileService/Json/GetAllRealTimeFood");
		URL_GET_ALL_REALTIMEFOOD = sb18.toString();

		StringBuilder sb19 = new StringBuilder();
		sb19.append(HTTP_URL).append(
				"/MobileService/Json/GetRealTimeFoodVersion");
		URL_CHECK_REALTIMEFOOD_UPDATE = sb19.toString();

		StringBuilder sb20 = new StringBuilder();
		sb20.append("http://" + ip + ":8732");
		sb20.append("/Service/GetAndroidVersion?sysVersion="
				+ Config.SYSTEM_VERSION + "&screenSize=" + Config.SCREEN_SIZE
				+ "&appType=Android");
		URL_CHECK_APK_VERSION = sb20.toString();

		StringBuilder sb21 = new StringBuilder();
		sb21.append(HTTP_URL).append("/MobileService/Json/GetAllSurvey");
		URL_GET_ALL_SURVEY = sb21.toString();

		StringBuilder sb22 = new StringBuilder();
		sb22.append(HTTP_URL).append("/MobileService/Json/CreateSurveyResult");
		URL_CREAT_SURVEY_ANSWER = sb22.toString();
		StringBuilder sb23 = new StringBuilder();
		if (DEBUG) {
			sb23.append("http://192.168.1.4:8731/MobileService/Json/DownloadAndroidSkin?skinApkName=skin70.apk");
		} else {
			sb23.append(HTTP_URL).append(
					"/MobileService/Json/DownloadAndroidSkin?skinApkName="
							+ SKIN_NAME);
		}
		URL_UPDATE_SKIN = sb23.toString();

		StringBuilder sb24 = new StringBuilder();
		sb24.append(HTTP_URL).append(
				"/MobileService/Json/GetAllPackageDishCategory");
		URL_GET_ALL_PACKAGEDISHCATEGORY = sb24.toString();
		if (DEBUG) {
			URL_GET_ALL_PACKAGEDISHCATEGORY = "http://192.168.1.4:8731/MobileService/Json/GetAllPackageDishCategory";
		}

		StringBuilder sb25 = new StringBuilder();
		sb25.append(HTTP_URL).append(
				"/MobileService/Json/GetAllPackageFoodRelation");
		URL_GET_All_PACKAGEFOODRELATION = sb25.toString();
		if (DEBUG) {
			URL_GET_All_PACKAGEFOODRELATION = "http://192.168.1.4:8731/MobileService/Json/GetAllPackageFoodRelation";
		}
		
		
		StringBuilder sb26 = new StringBuilder();
		sb26.append(HTTP_URL).append(
				"/MobileService/Json/GetPackageFoodByPick?");
		URL_GET_CHECK_PACKAGEDISH = sb26.toString();

	}
}
