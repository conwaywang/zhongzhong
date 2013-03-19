package com.fumuquan.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.fumuquan.config.Config;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtil {
	protected static AsyncExecuter asyncExecuter;

	/**
	 * 
	 * @param imageFile
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap decodeBitmapByPath(String imageFile, int width, int height) {
		asyncExecuter = AsyncExecuter.getInstance();

		StringBuilder sb = new StringBuilder();
		sb.append(Config.sAppCatchPath).append(imageFile);
		File pic = new File(sb.toString());
		if (!pic.exists()) {
			StringBuilder sb2 = new StringBuilder();
			sb2.append(Config.sAppDataPath).append(imageFile);
			File pic2 = new File(sb2.toString());
			pic2 = new File(sb2.toString());
			if (!pic2.exists())
				return null;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(sb2.toString(), opts);
			opts.inSampleSize = calculateInSampleSize(opts, width, height);
			opts.inPreferredConfig = Bitmap.Config.RGB_565;
			opts.inPurgeable = true;
			opts.inJustDecodeBounds = false;
			
			try {
				Bitmap bitmap;
				bitmap = BitmapFactory.decodeStream(new FileInputStream(pic2), null, opts);
				if (bitmap != null)
					creatCacheImage(imageFile, bitmap);
				return bitmap;
			} catch (FileNotFoundException e) {
				return null;
			}catch (OutOfMemoryError e) {
				e.printStackTrace();
				return null;
			}
		} else {
			try {
				try {
					return BitmapFactory.decodeStream(new FileInputStream(pic));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return null;
				}
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
		return inSampleSize;
	}

	private static void creatCacheImage(final String imageName, final Bitmap bitmap) {
		asyncExecuter.execute(new Thread() {
			@Override
			public void run() {
				StringBuilder sb = new StringBuilder();
				sb.append(Config.sAppCatchPath);
				File dir = new File(sb.toString());
				if (!dir.exists()) {
					dir.mkdirs();
				}
				sb.append(imageName);
				File bitmapFile = new File(sb.toString());
				if (!bitmapFile.exists()) {
					try {
						bitmapFile.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				FileOutputStream fos;
				try {
					fos = new FileOutputStream(bitmapFile);
					if (sb.substring(sb.length() - 4, sb.length()).equals(".png"))
						bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
					else
						bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
					fos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	

}
