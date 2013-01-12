package com.xinwen121.zhongzhong.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.xinwen121.zhongzhong.config.Config;

import android.util.Log;

public class LogUtil {
	public static boolean lock = false;

	private static boolean write2FileLock = true;

	private static boolean isCreateFile = false; // log文件是否创建
	private static File logFile = null; // log文件

	public static void dumpCrashLog(String path, String log) {
		try {
			SDOperate.saveToSDCard(path, log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void dumpCrashLog(String path, Throwable error) {
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

		dumpCrashLog(path, build.toString());
	}

	public static void i(String tag, String msg) {
		if (lock) {
			Log.i(tag, msg);
		}

	}

	public static void e(String tag, String msg) {
		if (lock) {
			Log.e(tag, msg);
		}

	}

	public static void v(String tag, String msg) {
		if (lock) {
			Log.v(tag, msg);
		}

	}

	public static void d(String tag, String msg) {
		if (lock) {
			Log.d(tag, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (lock) {
			Log.w(tag, msg);
		}

	}

	// 写log到文件
	public static void write2File(String msg) {
		if (write2FileLock) {

			String logPath = Config.sMenuFolderPath + "log" + File.separator;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			if (!isCreateFile) { // 创建log文件
				File logPathFile = new File(logPath);
				if (!logPathFile.exists()) {
					logPathFile.mkdirs();
				}

				logFile = new File(logPath + "log.txt");

				isCreateFile = true;
			}

			PrintWriter pw;
			try {
				pw = new PrintWriter(new FileOutputStream(logFile, true));

				pw.println(sdf.format(new Date()) + "\t" + msg);
				pw.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}
	}
}
