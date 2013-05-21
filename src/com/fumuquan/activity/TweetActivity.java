package com.fumuquan.activity;

import widget.PullToRefreshListView;

import com.fumuquan.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 动态
 * @author conwaywang
 * @date 20130406
 */
public class TweetActivity extends BaseActivity{
	private PullToRefreshListView lvTweet;
	private View vFooter;
	private TextView tvFootMore;
	private ProgressBar pbFootProgress;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_tweet);
		
		findViews();
		setupListeners();
		
	}
	
	@Override
	protected void findViews() {
		lvTweet = (PullToRefreshListView) findViewById(R.id.lv_tweet);
		
		vFooter = LayoutInflater.from(getApplicationContext()).inflate(R.layout.listview_footer, null);
		tvFootMore = (TextView)vFooter.findViewById(R.id.listview_foot_more);
		pbFootProgress = (ProgressBar)vFooter.findViewById(R.id.listview_foot_progress);
		
		lvTweet.addFooterView(vFooter);
	}

	@Override
	protected void setupListeners() {
		lvTweet.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	protected void initData(){
		
	}

	
}
