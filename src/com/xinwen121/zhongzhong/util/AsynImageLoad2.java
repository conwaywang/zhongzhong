package com.joyeon.smartmenu.util;

import java.util.concurrent.SynchronousQueue;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.joyeon.smartmenu.R;
import com.joyeon.smartmenu.dao.AppManage;

public class AsynImageLoad2 {

	public static AsynImageLoad2 asynImageLoad2 = null;

	public static final int pic = R.drawable.bg_default_pic_small;

	private final static int cacheSize = 1024 * 1024 * 100;

	protected AsyncExecuter asyncExecuter;

//	private static final int SOFT_CACHE_CAPACITY = 40;

	private SynchronousQueue<WorkItem> taskQueue;

	private final int THREAD_NUM = 5;

	private Handler mMainHandler;

//	private final static LinkedHashMap<String, SoftReference<Bitmap>> sSoftBitmapCache = new LinkedHashMap<String, SoftReference<Bitmap>>(
//			SOFT_CACHE_CAPACITY, 0.75f, true) {
//		private static final long serialVersionUID = 1L;
//
//		public SoftReference<Bitmap> put(String key, SoftReference<Bitmap> value) {
//			return super.put(key, value);
//		}
//
//		protected boolean removeEldestEntry(
//				Entry<String, SoftReference<Bitmap>> eldest) {
//			if (size() > SOFT_CACHE_CAPACITY) {
//				Log.v("tag", "Soft Reference limit , purge one");
//				return true;
//			}
//			return false;
//		};
//	};

	private final static LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(
			cacheSize) {

		protected int sizeOf(String key, Bitmap bitmap) {
			return bitmap.getByteCount();
		}

		protected void entryRemoved(boolean evicted, String key,
				Bitmap oldValue, Bitmap newValue) {

			super.entryRemoved(evicted, key, oldValue, newValue);
//			sSoftBitmapCache.put(key, new SoftReference<Bitmap>(oldValue));
		}
	};

	private AsynImageLoad2() {
		asyncExecuter = AsyncExecuter.getInstance();

		mMainHandler = new Handler(Looper.getMainLooper());
		taskQueue = new SynchronousQueue<WorkItem>();

		for (int i = 0; i < THREAD_NUM; i++) {
			asyncExecuter.execute(new Worker());
		}
	}

	class Worker implements Runnable {

		private static final String TAG = "Worker";

		public void run() {
			while (true) {
				try {
					final WorkItem item = taskQueue.take();
					if (null != item) {

						Log.d(TAG, Thread.currentThread().getName());
						final Bitmap bitmap = loadImageFromUrl(item.imageUr,
								AppManage.foodPages.get(item.page).style);

						mMainHandler.post(new Runnable() {
							public void run() {
								item.callback.imageLoaded(bitmap);
							}
						});

						if (null != bitmap) {
							cache.put(item.imageUr, bitmap);
						}

					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	class WorkItem {
		String imageUr;
		ImageCallback callback;

		int page;

		public WorkItem(String url, int page, ImageCallback callback) {
			this.imageUr = url;
			this.callback = callback;
			this.page = page;
		}
	}

	public static AsynImageLoad2 getInstance() {
		if (asynImageLoad2 == null) {

			synchronized (AsynImageLoad2.class) {

				if (null == asynImageLoad2) {
					asynImageLoad2 = new AsynImageLoad2();
				}

			}
		}
		return asynImageLoad2;
	}

	private Bitmap getBitmap(String imageUrl) {
		if (cache.get(imageUrl) != null) {
//			Log.i("cache", "notnull img:" + imageUrl);
			return cache.get(imageUrl);
		}

//		SoftReference<Bitmap> bitmapReference = sSoftBitmapCache.get(imageUrl);
//		if (bitmapReference != null) {
//			final Bitmap bitmap2 = bitmapReference.get();
//			if (bitmap2 != null) {
//				Log.v("tag", "soft reference hit");
//				return bitmap2;
//			} else {
//				Log.v("tag", "soft reference 已经被回收");
//				sSoftBitmapCache.remove(imageUrl);
//			}
//		}

		return null;
	}

	public Bitmap loadDrawable(final String imageUrl, final int page,
			final ImageCallback callback) {
		Bitmap bitmap = getBitmap(imageUrl);
		if (null != bitmap) {
			return bitmap;
		}

		try {
			taskQueue.put(new WorkItem(imageUrl, page, callback));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return null;
	}

	protected Bitmap loadImageFromUrl(String imageUrl, int style) {
		try {
			if (style == 91)
				return BitmapUtil.decodeBitmapByPath(imageUrl, DensityUtil.dip2px(210), DensityUtil.dip2px(157), 0,
						style);
			else if (style == 92)
				return BitmapUtil.decodeBitmapByPath(imageUrl, DensityUtil.dip2px(93), DensityUtil.dip2px(258), 1,
						style);
			else if (style == 71)
				return BitmapUtil.decodeBitmapByPath(imageUrl, DensityUtil.dip2px(454), DensityUtil.dip2px(249), 1,
						style);
			else if (style == 61)
				return BitmapUtil.decodeBitmapByPath(imageUrl, DensityUtil.dip2px(207), DensityUtil.dip2px(308), 1,
						style);
			else if (style == 41)
				return BitmapUtil.decodeBitmapByPath(imageUrl, DensityUtil.dip2px(328), DensityUtil.dip2px(300), 1,
						style);
			else if (style == 31)
				return BitmapUtil.decodeBitmapByPath(imageUrl, DensityUtil.dip2px(454), DensityUtil.dip2px(249), 1,
						style);
			else if (style == 21)
				return BitmapUtil.decodeBitmapByPath(imageUrl, DensityUtil.dip2px(517), (386), 1,
						style);
			else if (style == 11)
				return BitmapUtil.decodeBitmapByPath(imageUrl, DensityUtil.dip2px(678), DensityUtil.dip2px(814), 1,
						style);
			else if (style == 1000)
				return BitmapUtil.decodeBitmapByPath(imageUrl, DensityUtil.dip2px(678), DensityUtil.dip2px(850), 1,
						style);
			else
				return BitmapUtil.decodeBitmapByPath(imageUrl, DensityUtil.dip2px(210), DensityUtil.dip2px(157), 0,
						style);
		} catch (Exception e) {
			return null;
		}

	}

	public interface ImageCallback {

		public void imageLoaded(Bitmap bitmap);

		public String getImageUrl();
	}

	public void lock() {
		// allowLoad = false;
	}

	public void setDelay(int delay) {
		// this.delay = delay;
	}

	public void unLock() {
		// allowLoad = true;
		// synchronized (lock) {
		// lock.notifyAll();
		// }
	}
}
