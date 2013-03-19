package com.fumuquan.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.widget.ImageView;

public class MyAsyncThreads {
	protected AsyncExecuter asyncExecuter;
	protected Handler handler = new Handler();

	public static MyAsyncThreads myAsyncThreads = null;

	private MyAsyncThreads() {
		asyncExecuter = AsyncExecuter.getInstance();
	}

	public static MyAsyncThreads getInstance() {
		if (myAsyncThreads == null) {
			myAsyncThreads = new MyAsyncThreads();
		}

		return myAsyncThreads;
	}

	public void loadDrawable(final String imageUrl, final ImageView iv , final int style) {
		asyncExecuter.execute(new Thread() {
			public void run() {
				this.setPriority(4);
				try {
						final Bitmap bitmap = loadImageFromUrl(imageUrl , style);
						handler.post(new Runnable() {
							@Override
							public void run() {
								imageLoaded(bitmap, iv);
							}
						});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	protected Bitmap loadImageFromUrl(String imageUrl , int style) {
		Bitmap bitmap = null;
		
		try {
			//bitmap = BitmapUtil.decodeBitmapByPath(imageUrl, DensityUtil.dip2px(648) , DensityUtil.dip2px(696) , 1 , style);
		} catch (Exception e) {
			return null;
		}
		
		return bitmap;
	}

	protected void imageLoaded(Bitmap bitmap, ImageView iv) {
		if (bitmap != null) {
//			iv.setBackgroundColor(Color.TRANSPARENT);
			iv.setImageBitmap(bitmap);
		} else{
			//iv.setImageResource(R.drawable.bg_default_pic_big);
		}
	}

}
