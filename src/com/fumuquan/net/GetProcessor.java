package com.fumuquan.net;

import java.io.IOException;
import java.io.InputStream;

import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.fumuquan.manager.AppManager;
import com.fumuquan.util.AsyncExecuter;
import com.fumuquan.util.ConvertStream;
import com.fumuquan.util.HttpConnect;

/**
 * 处理Get类请求
 * @author conwaywang
 *
 */
public class GetProcessor {
	private static GetProcessor getProcessor = null;
	private static Object INSTANCE_LOCK = new Object();
	
	public static GetProcessor getInstance(){
		if(null == getProcessor){
			synchronized(INSTANCE_LOCK){
				if(null == getProcessor){
					getProcessor = new GetProcessor();
				}
			}
		}
		
		return getProcessor;
	}
	
	private AsyncExecuter asyncExecuter;
	private HttpConnect httpConnect;
	
	private GetProcessor(){
		asyncExecuter = AsyncExecuter.getInstance();
		httpConnect = HttpConnect.getInstance();
	}
	
	
	/**
	 *  采用Get方式提交数据
	 * @param handler
	 * @param urlInfo	urlInfo中需要填好采用get方式提交的数据
	 */
	public void startGet(Handler handler, String urlInfo, IResponseProcessor responseProcessor){
		if(null == urlInfo){
			return;
		}
		
		asyncExecuter.execute(new GetInfo(urlInfo, handler, responseProcessor));
	}
	
	/**
	 * 采用Get方式提交数据
	 * @author conwaywang
	 * @date  20130225
	 */
	private class GetInfo implements Runnable{
		private String mUrlInfo;
		private Handler mHandler;
		private IResponseProcessor mResponseProcessor;
		private static final int MAX_RETRY_TIMES = 3;
		
		public GetInfo(String urlInfo, Handler handler, IResponseProcessor responseProcessor){
			this.mUrlInfo = urlInfo;
			this.mHandler = handler;
			
			this.mResponseProcessor = responseProcessor;
		}
		
		public void run() {
			boolean retryFlag = true;	//是否重发
			int retryTimes = 0;
			
			mHandler.sendEmptyMessage(AppManager.GET_DATA_START);
			
			while(retryFlag && (retryTimes++ < MAX_RETRY_TIMES))
			{
				//只有一种情况会重发
				retryFlag = false;
				
				if(retryTimes>1){	//重发时不立即发送，等待2秒再发送
					try{
						Thread.sleep(500);
					}catch(Exception e){e.printStackTrace();}
				}
				
				try{
					/////////
					InputStream in = httpConnect.get(mUrlInfo);
					String jsonTextResponse = ConvertStream.toJSONString(in);
					in.close();
					
					mResponseProcessor.processResponse(mHandler, jsonTextResponse);
				}catch (IOException e) {
					//重发
					retryFlag = true;
					mHandler.sendEmptyMessage(AppManager.GET_DATA_RETRY);
				}catch(Exception e){
					e.printStackTrace();
					mHandler.sendEmptyMessage(AppManager.GET_DATA_FAILED);
					return;
				}
			
			}
			
			//获取失败
			if(retryTimes >= MAX_RETRY_TIMES){
				mHandler.sendEmptyMessage(AppManager.GET_DATA_FAILED);
			}
			
		} //end run
	}	//end 
	
}
