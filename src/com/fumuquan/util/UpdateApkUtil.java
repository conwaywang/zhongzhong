package com.fumuquan.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.fumuquan.config.Config;
import com.fumuquan.exception.ApkEmptyException;
import com.fumuquan.exception.ServerResponseException;


public class UpdateApkUtil {

	protected Context context;
	protected Handler handler;
	protected String fileName;
	protected AsyncExecuter asyncExecuter;
	private long fileSize;

	private static UpdateApkUtil updateApkUtil = null;

	public static UpdateApkUtil getInstance() {
		if (updateApkUtil == null) {
			updateApkUtil = new UpdateApkUtil();
		}
		return updateApkUtil;
	}

	public static UpdateApkUtil getNewInstance() {
		return new UpdateApkUtil();
	}

	private UpdateApkUtil() {

	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public void startUpdate(Handler handler) {
		this.handler = handler;
		asyncExecuter = AsyncExecuter.getInstance();
		asyncExecuter.execute(new UpdateApk());
	}

	public void checkVersion(Handler handler) {
		this.handler = handler;
		asyncExecuter = AsyncExecuter.getInstance();
//		asyncExecuter.execute(new checkVersion());
	}

	private class UpdateApk implements Runnable {
		public void run() {
			try {
				updateAPK();
			} catch (ClientProtocolException e) {
				//handler.sendEmptyMessage();
			} catch (IOException e) {
				//handler.sendEmptyMessage(AppManage.NET_EXCEPTION_BY_APK);
			} catch (ServerResponseException e) {
				//handler.sendEmptyMessage(AppManage.SERVER_RESPONSE_EXCEPTION_BY_APK);
			} catch (ApkEmptyException e) {
				//handler.sendEmptyMessage(AppManage.UPDATE_APK_EMPTY);
			}
		}
	}

	private void updateAPK() throws ClientProtocolException, IOException,
			ServerResponseException, ApkEmptyException {
		HttpConnect httpConnect = HttpConnect.getInstance();
		File rootDir = new File(Config.getInstance().sAppDataPath);
		if (!rootDir.exists()) {
			rootDir.mkdirs();
		}
		File file = new File(Config.getInstance().sApkPath);
		if (file.exists())
			file.delete();
		HttpEntity entity = httpConnect.getFile(Config.URL_UPDATE_APK);
		InputStream in = entity.getContent();

		fileSize = entity.getContentLength();
		if (fileSize == 0) {
			if(null!=in)
			{
				in.close();
			}
			throw new ApkEmptyException("apk为空");
		} else {
			long downLoadSize = 0;
			FileOutputStream fos = new FileOutputStream(file);
			byte[] buffer = new byte[1024 * 4];
			int len = 0;
			while ((len = in.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				downLoadSize += len;

				int progress = (int) ((downLoadSize / fileSize) * 100);
//				Message msg = handler.obtainMessage();
//				msg.what = AppManage.UPDATE_APK_PROGRESS;
//				msg.arg1 = progress;
//				handler.sendMessage(msg);
			}
			fos.flush();
			in.close();
			fos.close();

//			if (downLoadSize == fileSize)
//				handler.sendEmptyMessage(AppManage.UPDATE_APK_SUCCESS);
//			else
//				handler.sendEmptyMessage(AppManage.UPDATE_APK_FAIL);
		}
	}

	public void install(Context context) {
		File file = new File(Config.getInstance().sApkPath);
		Intent install = new Intent();
		install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		install.setAction(android.content.Intent.ACTION_VIEW);
		install.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		context.startActivity(install);// 安装
	}

	public void install(Context context, String path) {
		File file = new File(path);
		Intent install = new Intent();
		install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		install.setAction(android.content.Intent.ACTION_VIEW);
		install.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		context.startActivity(install);// 安装

//		if (null != AppManage.newVersion) {
//			int local_version = AppManage.newVersion
//					.getAndroidSkin97Version();
//			context.getSharedPreferences("version", Context.MODE_PRIVATE)
//					.edit().putInt("local_v", local_version).commit();
//		}
		
		context.getSharedPreferences("version", Context.MODE_PRIVATE)
		.edit().putBoolean("skin_installed", true).commit();
	}

}
