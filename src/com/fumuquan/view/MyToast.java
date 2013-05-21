package com.fumuquan.view;

import com.fumuquan.R;
import com.fumuquan.util.DensityUtil;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MyToast {
	private static Toast toast = null;

	public static Toast makeText(Context context, int resId, int duration) {
		
		if (toast == null) {
			toast = new Toast(context);
			toast.setGravity(Gravity.CENTER, 0, DensityUtil.dip2px(context, 200));
        } 
		LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflate.inflate(R.layout.toast_layout, null);

		toast.setView(v);
		TextView tv = (TextView) v.findViewById(R.id.toast_text);
		tv.setText(context.getResources().getText(resId));
		return toast;
	}
}
