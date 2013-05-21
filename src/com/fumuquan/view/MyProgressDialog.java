package com.fumuquan.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

public class MyProgressDialog {
	private Context context;
	private ProgressDialog pd;

	public MyProgressDialog(Context context) {
		this.context = context;
	}

	public void show(String message) {
		if (pd == null)
			pd = new ProgressDialog(this.context);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setMessage(message);
		pd.setIndeterminate(false);
		pd.setCancelable(false);
		pd.show();
		
		Log.i("show", "show");
	}

	public boolean isShowing() {
		if (pd != null)
			return pd.isShowing();
		return false;
	}

	public void setMessage(String message) {
		if (pd != null && pd.isShowing())
			pd.setMessage(message);
		else
			show(message);
	}

	public void dismiss() {
		if (pd != null && pd.isShowing()) {
			pd.dismiss();
			pd = null;
		}
	}

}
