package com.fumuquan.adapter;

import java.util.List;

import com.fumuquan.entity.Tweet;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ListViewTweetAdapter extends BaseAdapter{
	private Context context;
	private List<Tweet> mListItem;

	@Override
	public int getCount() {
		return mListItem.size();
	}

	@Override
	public Object getItem(int position) {
		if(position < mListItem.size()){
			return mListItem.get(position);
		}
		
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		
		if(convertView == null){
			
		}
		else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		
		return convertView;
	}

	//
	static class ViewHolder{
		
	}
}
