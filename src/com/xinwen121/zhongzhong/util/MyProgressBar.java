package com.joyeon.smartmenu.util;

import android.app.ProgressDialog;
import android.content.Context;

public class MyProgressBar {
	private Context context;
	private ProgressDialog pd;

	private String titleString;

	private String messageString;

	public MyProgressBar(Context context, String title, String message) {
		this.context = context;
		this.titleString = title;
		this.messageString = message;
	}

	public void show() {
		if (pd == null)
			pd = new ProgressDialog(this.context);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMax(100);
		pd.setProgress(0);
		pd.setIndeterminate(false);
		pd.setCancelable(false);

		pd.setTitle(titleString);
		pd.setMessage(messageString);
		pd.show();
	}

	public void setTitle(String title) {
		if (null != pd) {
			pd.setTitle(title);
		}
	}

	public void setMessage(String message) {
		if (null != pd) {
			pd.setMessage(message);
		}
	}

	public boolean isShowing() {
		if (pd != null)
			return pd.isShowing();
		return false;
	}

	public void setProgress(int value) {
		if (pd != null)
			pd.setProgress(value);
	}

	public void dismiss() {
		if (pd != null && pd.isShowing())
			pd.dismiss();
	}
}
