package com.fumuquan.activity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fumuquan.config.Config;
import com.fumuquan.util.LogUtil;
import android.app.Application;

public class MyApplication extends Application {

	private static Thread.UncaughtExceptionHandler mDefaultExceptionHandler = null;
	private static Thread.UncaughtExceptionHandler mNewExceptionHandler = new Thread.UncaughtExceptionHandler() {
		public void uncaughtException(Thread thread, Throwable error) {
			
			
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyy年MM月dd日_HH时mm分ss秒");
			String date = sDateFormat.format(new java.util.Date());
			LogUtil.dumpCrashLog(Config.sAppLogPath + "/" + date
					+ ".txt", error);
			
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
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
}
