package com.fumuquan.activity;

import android.app.Activity;

/**
 * 基类，方便统一处理
 * @author conwaywang
 *
 */
public abstract class BaseActivity extends Activity {
	
	/**
	 * 获取控件
	 */
	protected abstract void findViews();
	
	/**
	 * 设置监听事件
	 */
	protected abstract void setupListeners();
	
	/**
	 * 刷新当前页面
	 */
	//protected abstract void refresh(); 
}
