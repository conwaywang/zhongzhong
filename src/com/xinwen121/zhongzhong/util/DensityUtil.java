package com.joyeon.smartmenu.util;

import com.joyeon.smartmenu.dao.AppManage;

public class DensityUtil {

	public static int dip2px(float dipValue) {
		final float scale = AppManage.appContext.getResources()
				.getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(float pxValue) {
		final float scale = AppManage.appContext.getResources()
				.getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

}
