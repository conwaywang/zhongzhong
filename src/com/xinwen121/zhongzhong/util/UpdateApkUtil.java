package com.joyeon.smartmenu.util;

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
import com.joyeon.smartmenu.Config;
import com.joyeon.smartmenu.R;
import com.joyeon.smartmenu.dao.AppManage;
import com.joyeon.smartmenu.entity.ModuleVersion;
import com.joyeon.smartmenu.exception.ApkEmptyException;
import com.joyeon.smartmenu.exception.OperateStreamException;
import com.joyeon.smartmenu.exception.ServerResponseException;

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
		asyncExecuter.execute(new checkVersion());
	}

	private class UpdateApk implements Runnable {
		public void run() {
			try {
				updateAPK();
			} catch (ClientProtocolException e) {
				handler.sendEmptyMessage(AppManage.HTTP_SERVICE_ERROR_EXCEPTION_BY_APK);
			} catch (IOException e) {
				handler.sendEmptyMessage(AppManage.NET_EXCEPTION_BY_APK);
			} catch (ServerResponseException e) {
				handler.sendEmptyMessage(AppManage.SERVER_RESPONSE_EXCEPTION_BY_APK);
			} catch (ApkEmptyException e) {
				handler.sendEmptyMessage(AppManage.UPDATE_APK_EMPTY);
			}
		}
	}

	private void updateAPK() throws ClientProtocolException, IOException,
			ServerResponseException, ApkEmptyException {
		HttpConnect httpConnect = HttpConnect.getInstance();
		File rootDir = new File(Config.sMenuFolderPath);
		if (!rootDir.exists()) {
			rootDir.mkdirs();
		}
		File file = new File(Config.APK_PATH);
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
				Message msg = handler.obtainMessage();
				msg.what = AppManage.UPDATE_APK_PROGRESS;
				msg.arg1 = progress;
				handler.sendMessage(msg);
			}
			fos.flush();
			in.close();
			fos.close();

			if (downLoadSize == fileSize)
				handler.sendEmptyMessage(AppManage.UPDATE_APK_SUCCESS);
			else
				handler.sendEmptyMessage(AppManage.UPDATE_APK_FAIL);
		}
	}

	private void updateSkin() throws ClientProtocolException, IOException,
			ServerResponseException, ApkEmptyException {
		HttpConnect httpConnect = HttpConnect.getInstance();
		File rootDir = new File(Config.sMenuFolderPath);
		if (!rootDir.exists()) {
			rootDir.mkdirs();
		}
		File file = new File(Config.SKIN_PATH);
		if (file.exists())
			file.delete();
		HttpEntity entity = httpConnect.getFile(Config.URL_UPDATE_SKIN);
		InputStream in = entity.getContent();

		fileSize = entity.getContentLength();
		if (fileSize == 0) {
			if (null != in) {
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
				Message msg = handler.obtainMessage();
				msg.what = AppManage.UPDATE_SKIN_PROGRESS;
				msg.arg1 = progress;
				handler.sendMessage(msg);
			}
			fos.flush();
			in.close();
			fos.close();

			if (downLoadSize == fileSize)
				handler.sendEmptyMessage(AppManage.UPDATE_SKIN_SUCCESS);
			else
				handler.sendEmptyMessage(AppManage.UPDATE_SKIN_FAIL);
		}
	}

	public void asynDownLoadSkin(final Handler handler) {
		this.handler = handler;
		AsyncExecuter.getInstance().execute(new UpdateSkin());
	}

	private class UpdateSkin implements Runnable {

		public void run() {
			try {
				if (!checkSkin()) {
					handler.sendEmptyMessage(AppManage.UPDATE_SKIN_SAM_VERSION);
					return;
				}else {
					handler.sendEmptyMessage(AppManage.UPDATE_CHECK_NEW_VERSION);
				}
			} catch (Exception e1) {
				handler.sendEmptyMessage(AppManage.UPDATE_SKIN_CHECK_VERSION_FAIL);
				return;
			}

			try {
				updateSkin();
			} catch (ClientProtocolException e) {
				handler.sendEmptyMessage(AppManage.HTTP_SERVICE_ERROR_EXCEPTION_BY_APK);
			} catch (IOException e) {
				handler.sendEmptyMessage(AppManage.NET_EXCEPTION_BY_APK);
			} catch (ServerResponseException e) {
				handler.sendEmptyMessage(AppManage.SERVER_RESPONSE_EXCEPTION_BY_APK);
			} catch (ApkEmptyException e) {
				handler.sendEmptyMessage(AppManage.UPDATE_APK_EMPTY);
			}
		}
	}

	private boolean checkSkin() throws ClientProtocolException, IOException,
			ServerResponseException, OperateStreamException {
		int localSkinV = Config.local_version;
//		if (null != AppManage.newVersion) {
//			int serverSkinV = AppManage.newVersion.getAndroidSkin97Version();
//			return serverSkinV > localSkinV;
//		} else {
			ModuleVersion version = getServerSkinVersion();
			if (null != version) {
				int serverSkinV = version.getAndroidSkin97Version();
				return serverSkinV > localSkinV;
			}
//		}
		return true;
	}

	private ModuleVersion getServerSkinVersion()
			throws ClientProtocolException, IOException,
			ServerResponseException, OperateStreamException {
		HttpConnect httpConnect = HttpConnect.getInstance();
		String urlString;
		if(Config.DEBUG)
		{
			urlString="http://192.168.1.4:8731/MobileService/Json/GetUpdateVersion";
		}else {
			urlString=Config.URL_CHECK_RESOURCE_UPDATE;
		}
		InputStream in = httpConnect.get(urlString);
		String jsonText = ConvertStream.toJSONString(in);
		ModuleVersion newVersion = JSON.parseObject(jsonText,
				ModuleVersion.class);

		if (null != newVersion) {
			AppManage.newVersion = newVersion;
		}
//			else {
//			AppManage.newVersion.setAndroidSkin97Version(newVersion.getAndroidSkin97Version());
//		}
		return newVersion;
	}

	private class checkVersion implements Runnable {
		public void run() {
			try {
				if (needUpdate())
					handler.sendEmptyMessage(AppManage.APK_NEED_UPDATE);
				else
					handler.sendEmptyMessage(AppManage.APK_NOT_NEED_UPDATE);
			} catch (ClientProtocolException e) {
				handler.sendEmptyMessage(AppManage.HTTP_SERVICE_ERROR_EXCEPTION_BY_APK);
			} catch (IOException e) {
				handler.sendEmptyMessage(AppManage.NET_EXCEPTION_BY_APK);
			} catch (ServerResponseException e) {
				handler.sendEmptyMessage(AppManage.SERVER_RESPONSE_EXCEPTION_BY_APK);
			} catch (OperateStreamException e) {
				handler.sendEmptyMessage(AppManage.RESOLVE_EXCEPTION_BY_APK);
			}
		}
	}

	private boolean needUpdate() throws ClientProtocolException, IOException,
			ServerResponseException, OperateStreamException {
		HttpConnect httpConnect = HttpConnect.getInstance();
		InputStream in = httpConnect.get(Config.URL_CHECK_APK_VERSION);
		String version = ConvertStream.toString(in);
		LogUtil.i("version", Config.URL_CHECK_APK_VERSION);
		LogUtil.i("version", version);
		int result = Config.SYSTEM_VERSION.compareToIgnoreCase(version);
		// result <0 服务器版本号大，需要更新,check return true;
		return result < 0;
	}

	public void install(Context context) {
		File file = new File(Config.APK_PATH);
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

		if (null != AppManage.newVersion) {
			int local_version = AppManage.newVersion
					.getAndroidSkin97Version();
			context.getSharedPreferences("version", Context.MODE_PRIVATE)
					.edit().putInt("local_v", local_version).commit();
		}
		
		context.getSharedPreferences("version", Context.MODE_PRIVATE)
		.edit().putBoolean("skin_installed", true).commit();
	}

}
