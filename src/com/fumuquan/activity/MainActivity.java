package com.fumuquan.activity;

import com.fumuquan.R;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TabHost;

/**
 * 标签页面
 * @author conwaywang
 *
 */
public class MainActivity extends TabActivity {
	private TabHost tabHost;
	private RadioGroup mainBtnGroup;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main_tab);
	}
}
