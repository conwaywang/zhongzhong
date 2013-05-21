package com.fumuquan.activity;

import com.fumuquan.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class WelcomeActivity extends Activity {

	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			
			switch(msg.what){
			default:
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent intent = new Intent(WelcomeActivity.this, SNSActivity.class);
				startActivity(intent);
			}
		}, 1000);
	}


}
