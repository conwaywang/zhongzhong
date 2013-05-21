package com.fumuquan.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;

import com.fumuquan.config.Config;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;

/**
 * 异步加载图片
 * @author conwaywang
 * @date
 */
public class AsynImageLoad {
	private static AsynImageLoad asynImageLoad = null;
	private static Object INSTANCE_LOCK = new Object();
	
	public static AsynImageLoad getInstance(Context context) {
		if (asynImageLoad == null) {
			synchronized(INSTANCE_LOCK){
				if(null == asynImageLoad){
					int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();  
					int cacheSize = 1024 * 1024 * memClass/4;	//
					asynImageLoad = new AsynImageLoad(cacheSize);
				}
			}
		}
		return asynImageLoad;
	}
	
	private static final int MAX_RETRY_TIMES = 3;	//网络最多请求次数
	
	private static final String IMG_PNG = ".png";
	private static final String IMG_JPEG = ".jpeg";
	
	private LruCache<String, Bitmap> cache;
	
	protected AsyncExecuter asyncExecuter;

	private AsynImageLoad(int cacheSize) {
		cache = new LruCache<String, Bitmap>(cacheSize){
			@Override
			protected int sizeOf(String key, Bitmap bitmap){
				return bitmap.getByteCount();
			}
		};
		
		asyncExecuter = AsyncExecuter.getInstance();
	}

	/**
	 * 加载图片。两级缓存。内存缓存没有，则查找磁盘缓存。都没有再请求网络
	 * @param imageName
	 * @param width
	 * @param height
	 * @param callback
	 * @return
	 */
	public Bitmap loadDrawable(final String imageName, final String imageUrl, final int width, final int height, final ImageCallback callback) {
		final Handler handler = new Handler(){
			public void handleMessage(Message message){
				callback.imageLoaded((Bitmap)message.obj);
			}
		};
		
		if(imageName.isEmpty()){
			return null;
		}
		
		//内存缓存
		Bitmap bitmap = cache.get(imageName);
		if (null != bitmap) {
			//LogUtil.i("cache", "notnull img:"+imageName);
			return bitmap;
		}
		
		asyncExecuter.execute(new Thread() {
			public void run() {
				this.setPriority(NORM_PRIORITY - 2);
				try {
					String imgFileName = Config.getInstance().sAppCatchPath + imageName;
					Bitmap bitmap = getBitmapFromSDCard(imgFileName);		//从磁盘加载图片
					if (null == bitmap){		//从网络加载图片
						bitmap = downloadBitmap(imageUrl, width, height);
						if(null != bitmap){
							//保存图片到SDCard中
			                saveImageToSD(bitmap, Config.getInstance().sAppCatchPath, imageName);
						}
					}
					
					if (bitmap != null){
						cache.put(imageName, bitmap);
						LogUtil.i("cache", "size:"+cache.size());
					}
					
					Message message = handler.obtainMessage(0, bitmap);
	                handler.sendMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return null;
	}

	/**
	 * 从SD卡中读取缓存
	 * @param imgName
	 * @return
	 */
	private Bitmap getBitmapFromSDCard(String imgFileName){
		Bitmap bitmap = null;
		
		//判断是否挂载了SD卡
		String storageState = Environment.getExternalStorageState();
		if(!storageState.equals(Environment.MEDIA_MOUNTED)){
			return null;
		}
		
		File imgFile = new File(imgFileName);
		if(!imgFile.exists()){
			return null;
		}
		
		//读取缓存
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(imgFile);
			bitmap = BitmapFactory.decodeStream(fis);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(OutOfMemoryError e){
			e.printStackTrace();
		}finally{
			try{
				fis.close();
			}catch(Exception e){}
		}
		
		return bitmap;
	}
	
	/**
	 * 保存图片信息到本地磁盘缓存
	 * @param bitmap
	 * @param cachePath
	 * @param imgName
	 */
	private void saveImageToSD(Bitmap bitmap, String cachePath, String imgName){
		if(null == bitmap){
			return;
		}
		
		//判断是否挂载了SD卡
		String storageState = Environment.getExternalStorageState();
		if(!storageState.equals(Environment.MEDIA_MOUNTED)){
			return;
		}
		
		try{
			File dir = new File(cachePath);
			if(!dir.exists()){
				dir.mkdirs();
			}
			File bitmapFile = new File(cachePath + imgName);
			if(!bitmapFile.exists()){
				bitmapFile.createNewFile();
			}
			
			FileOutputStream fos = new FileOutputStream(bitmapFile);
			fos = new FileOutputStream(bitmapFile);
			if(imgName.substring(imgName.length()-4, imgName.length()).equals(IMG_PNG)){
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			}
			else{
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			}
			
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 从网络获取图片, 并缩放到指定大小
	 * @param width
	 * @param height
	 * @return
	 */
	private Bitmap downloadBitmap(String imgUrl, int width, int height){
		Bitmap bitmap = null;
		HttpConnect httpConnect = HttpConnect.getInstance();
		
		boolean retryFlag = true;	//是否重发
		int retryTimes = 0;
		
		while(retryFlag && (retryTimes++ < MAX_RETRY_TIMES))
		{
			//只有一种情况会重发
			retryFlag = false;
			
			if(retryTimes>1){	//重发时不立即发送，等待2秒再发送
				try{
					Thread.sleep(500);
				}catch(Exception e){e.printStackTrace();}
			}
			
			InputStream in = null;
			try{
				HttpEntity entity = httpConnect.getFile(imgUrl);
				in = entity.getContent();
				bitmap = BitmapFactory.decodeStream(in);
				in.close();
				in = null;
				
				if(width > 0 && height > 0) {
					//指定显示图片的高宽
					bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
				} 
			}catch (IOException e) {
				//重发
				retryFlag = true;
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try{
					if(null != in){
						in.close();
					}
				}catch(Exception e){}
			}
		}
		
		return bitmap;
	}
	

	public interface ImageCallback {
		public void imageLoaded(Bitmap bitmap);
	}
}
