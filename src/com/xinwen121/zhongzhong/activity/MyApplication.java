package com.xinwen121.zhongzhong.activity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.xinwen121.zhongzhong.config.Config;
import com.xinwen121.zhongzhong.util.LogUtil;


import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

	private static String sFilesPath;
	private static Context sMainContext;

	private static Thread.UncaughtExceptionHandler mDefaultExceptionHandler = null;
	private static Thread.UncaughtExceptionHandler mNewExceptionHandler = new Thread.UncaughtExceptionHandler() {
		public void uncaughtException(Thread thread, Throwable error) {
			
			
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyy年MM月dd日_HH时mm分ss秒");
			String date = sDateFormat.format(new java.util.Date());
			LogUtil.dumpCrashLog(MyApplication.sFilesPath + "/" + date
					+ ".txt", error);
			
//			AppManage.mainActivity.exit();
//			AppManage.mainActivity.finish();
			System.exit(0);
		}
	};

	public void onCreate() {
		super.onCreate();
		try {
			if ((!new BufferedReader(new FileReader("/proc/self/cmdline")).readLine().trim().contains(":"))
					&& (mDefaultExceptionHandler == null)) {
				Thread thread = getMainLooper().getThread();
				mDefaultExceptionHandler = thread.getUncaughtExceptionHandler();
				thread.setUncaughtExceptionHandler(mNewExceptionHandler);
				sMainContext = this;
				sFilesPath = Config.sMenuFolderPath;
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
}
