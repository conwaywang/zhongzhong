package com.fumuquan.activity;

import com.fumuquan.R;

import widget.SlidingMenuView;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SNSActivity extends ActivityGroup implements OnClickListener{
	private ViewGroup tabContent;
	private SlidingMenuView slidingMenuView;
	private Button btnTweet;
	private Button btnHelp;
	private Button btnActivity;
	private Button btnPersonalPage;
	

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_sns);
		
		findViews();
		setupListeners();
		initData();
	}
	
	protected void findViews() {
		slidingMenuView = (SlidingMenuView) findViewById(R.id.sliding_menu_view);
		tabContent = (ViewGroup) slidingMenuView.findViewById(R.id.sliding_body);
		
		btnTweet = (Button) findViewById(R.id.btn_tweet);
		btnHelp = (Button) findViewById(R.id.btn_help);
		btnActivity = (Button) findViewById(R.id.btn_activity);
		btnPersonalPage = (Button) findViewById(R.id.btn_personal_page);
	}


	protected void setupListeners() {
		btnTweet.setOnClickListener(this);
		btnHelp.setOnClickListener(this);
		btnActivity.setOnClickListener(this);
		btnPersonalPage.setOnClickListener(this);
	}
	
	protected void initData(){
		
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_tweet:
			showTweetTab();
			break;
		case R.id.btn_help:
			break;
		case R.id.btn_activity:
			break;
		case R.id.btn_personal_page:
			break;
		}
	}

	private void showTweetTab(){
		Intent i = new Intent(this, TweetActivity.class);
    	View v = getLocalActivityManager().startActivity(TweetActivity.class.getName(), i).getDecorView();
		tabContent.removeAllViews();
		tabContent.addView(v);
	}
	
}
