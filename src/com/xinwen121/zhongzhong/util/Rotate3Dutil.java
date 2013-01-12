package com.xinwen121.zhongzhong.util;

import android.view.View;

public class Rotate3Dutil {
	public static void rotate(View view,boolean reverse)
	{
		Animation3D animation3D = new Animation3D(0, 720,view.getWidth()/2.0f, view.getHeight()/2.0f,0,reverse);
		animation3D.setDuration(500);
		animation3D.setDuration(1000);
		view.startAnimation(animation3D);
	}
}
