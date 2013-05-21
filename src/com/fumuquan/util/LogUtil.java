package com.fumuquan.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Date;

import com.fumuquan.config.Config;

import android.util.Log;

public class LogUtil {
	
	private static final int INFO = 1;		//
	private static final int DEBUG = 2;		//
	private static final int WARN = 3;		//
	private static final int ERROR = 4;		//
	private static int curLogLevel = INFO;	//大于等于该级别的才会被输出到文件
	

	public static void dumpCrashLog(Throwable error) {
		StringBuilder build = new StringBuilder();
		
		build.append("------------------------------------\n");
		build.append(error.toString() + "\n");
		StackTraceElement[] trace = error.getStackTrace();

		for (StackTraceElement ele : trace) {
			build.append("\t\t" + ele.getFileName() + ":" + ele.getMethodName()
					+ ":" + ele.getLineNumber() + "\n");
		}

		error = error.getCause();
		if (error != null) {
			build.append("Caused By : " + error.toString() + "\n");
			trace = error.getStackTrace();
			for (StackTraceElement ele : trace) {
				build.append("\t\t" + ele.getFileName() + ":"
						+ ele.getMethodName() + ":" + ele.getLineNumber()
						+ "\n");
			}
		}

		write2File(build.toString());
	}
	
	public static void i(String tag, String msg) {
		if (curLogLevel <= INFO) {
			Log.i(tag, msg);
		}

	}
	

	public static void e(String tag, String msg) {
		if (curLogLevel <= ERROR) {
			Log.e(tag, msg);
		}

	}


	public static void d(String tag, String msg) {
		if (curLogLevel <= DEBUG) {
			Log.d(tag, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (curLogLevel <= WARN) {
			Log.w(tag, msg);
		}

	}
	
	
	// 写log到文件
	private static synchronized void write2File(String msg) {
		Date date = new Date();
		String dateInfo = DateUtil.fomatToYYYYMMDD(date);
		String timeInfo = DateUtil.fomatToHHMMSS(date);
		
		// 创建log文件
		File logPathFile = new File(Config.getInstance().sAppLogPath);
		if (!logPathFile.exists()) {
			logPathFile.mkdirs();
		}

		File logFile = new File(Config.getInstance().sAppLogPath + dateInfo + ".txt");
		
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileOutputStream(logFile, true));

			pw.println(timeInfo + "\t" + msg + "\n");
			pw.close();
			pw = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		
	}
	
	/**
	 * 记录到 
	 * @param msg
	 */
	public static void fInfo(String msg){
		if(curLogLevel <= INFO ){
			write2File(msg);
		}
	}
	
	public static void fDebug(String msg){
		if(curLogLevel <= DEBUG){
			write2File(msg);
		}
	}
	
	public static void fWarn(String msg){
		if(curLogLevel <= WARN){
			write2File(msg);
		}
	}
	
	public static void fError(String msg){
		if(curLogLevel <= ERROR){
			write2File(msg);
		}
	}
	
	
}
