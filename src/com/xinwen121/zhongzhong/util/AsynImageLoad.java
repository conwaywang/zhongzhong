package com.joyeon.smartmenu.util;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Stack;

import com.joyeon.smartmenu.R;
import com.joyeon.smartmenu.util.BitmapUtil;

import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class AsynImageLoad {

	public static AsynImageLoad asynImageLoad = null;
	public static final int pic = R.drawable.bg_default_pic_small;

	protected AsyncExecuter asyncExecuter;
	protected PhotosLoader photoLoaderThread;
	protected Stack<PhotoToLoad> photosToLoad;
	protected HashMap<String, SoftReference<Bitmap>> cache;
	
	protected Handler handler = new Handler();

	private AsynImageLoad() {
		photoLoaderThread = new PhotosLoader();
		photosToLoad = new Stack<PhotoToLoad>();
		photoLoaderThread.setPriority(Thread.NORM_PRIORITY - 1);
		cache = new HashMap<String, SoftReference<Bitmap>>();
		asyncExecuter = AsyncExecuter.getInstance();
		if (!photoLoaderThread.isAlive())
			photoLoaderThread.start();
	}

	public static AsynImageLoad getInstance() {
		if (asynImageLoad == null) {
			asynImageLoad = new AsynImageLoad();
		}
		return asynImageLoad;
	}

	public void DisplayImage(String url, ImageView imageView, ProgressBar pb) {
		if (cache.get(url) != null)
			if (cache.get(url).get() != null)
				imageView.setImageBitmap(cache.get(url).get());
			else {
				cache.remove(url);
				pb.setVisibility(View.VISIBLE);
				queuePhoto(url, imageView, pb);
			}
		else
		{
			cache.remove(url);
			pb.setVisibility(View.VISIBLE);
			queuePhoto(url, imageView, pb);
		}
	}

	private void queuePhoto(String url, ImageView imageView, ProgressBar pb) {
		PhotoToLoad p = new PhotoToLoad(url, imageView, pb);
		synchronized (photosToLoad) {
			photosToLoad.push(p);
			photosToLoad.notifyAll();
		}
	}

	private Bitmap getBitmap(String url) {
		try {
			return BitmapUtil.decodeBitmapByPath(url, 210, 157 , 0 , 0);
		} catch (Exception ex) {
			return null;
		}
	}

	private class PhotoToLoad {
		public String url;
		public ImageView imageView;
		public ProgressBar pb;

		public PhotoToLoad(String u, ImageView i, ProgressBar pb) {
			url = u;
			imageView = i;
			this.pb = pb;
		}
	}

	class PhotosLoader extends Thread {
		public void run() {
			try {
				while (true) {
					if (photosToLoad.size() == 0)
						synchronized (photosToLoad) {
							photosToLoad.wait();
						}
					if (photosToLoad.size() != 0) {
						final PhotoToLoad photoToLoad;
						synchronized (photosToLoad) {
							photoToLoad = photosToLoad.pop();
						}
						asyncExecuter.execute(new Thread() {
							@Override
							public void run() {
								this.setPriority(NORM_PRIORITY - 2);
								Bitmap bmp = getBitmap(photoToLoad.url);
								if (bmp != null)
									cache.put(photoToLoad.url, new SoftReference<Bitmap>(bmp));
//								while (MenuActivity.isFlipping != ViewPager.SCROLL_STATE_IDLE) {
//									try {
//										Thread.sleep(2);
//									} catch (InterruptedException e) {
//										MenuActivity.isFlipping = ViewPager.SCROLL_STATE_IDLE;
//									}
//								}
								handler.post(new BitmapDisplayer(bmp, photoToLoad.imageView, photoToLoad.pb));
							}
						});
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	class BitmapDisplayer implements Runnable {
		private Bitmap bitmap;
		private ImageView imageView;
		private ProgressBar pb;

		public BitmapDisplayer(Bitmap b, ImageView i, ProgressBar pb) {
			bitmap = b;
			imageView = i;
			this.pb = pb;
		}

		public void run() {
			pb.setVisibility(View.INVISIBLE);
			if (bitmap != null)
				imageView.setImageBitmap(bitmap);
			else
				imageView.setBackgroundResource(pic);
		}
	}

	public void clearCache() {
		cache.clear();
	}
}
