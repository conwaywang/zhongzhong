package com.fumuquan.net;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.NameValuePair;

import com.fumuquan.manager.AppManager;
import com.fumuquan.util.AsyncExecuter;
import com.fumuquan.util.ConvertStream;
import com.fumuquan.util.HttpConnect;

import android.os.Handler;

/**
 * 
 * @author conwaywang
 *
 */
public class PostProcessor {
	private static PostProcessor postProcessor = null;
	private static Object INSTANCE_LOCK = new Object();
	
	public static PostProcessor getInstance(){
		if(null == postProcessor){
			synchronized(INSTANCE_LOCK){
				if(null == postProcessor){
					postProcessor = new PostProcessor();
				}
			}
		}
		
		return postProcessor;
	}
	
	private AsyncExecuter asyncExecuter;
	private HttpConnect httpConnect;
	
	private PostProcessor(){
		asyncExecuter = AsyncExecuter.getInstance();
		httpConnect = HttpConnect.getInstance();
	}
	
	
	/**
	 *  采用Post方式提交数据
	 * @param handler
	 * @param urlInfo	
	 */
	public void startPost(Handler handler, String url, ArrayList<NameValuePair> valuePairs, IResponseProcessor responseProcessor){
		if(null == url){
			return;
		}
		
		asyncExecuter.execute(new PostInfo(handler, url, valuePairs, responseProcessor));
	}
	
	/**
	 * 采用Post方式提交数据
	 * @author conwaywang
	 * @date  20130402
	 */
	private class PostInfo implements Runnable{
		private static final int MAX_RETRY_TIMES = 3;
		private String mUrlInfo;
		private ArrayList<NameValuePair> mValuePairs;
		private Handler mHandler;
		private IResponseProcessor mResponseProcessor;
		
		
		public PostInfo(Handler handler, String urlInfo, ArrayList<NameValuePair> valuePairs, IResponseProcessor responseProcessor){
			this.mUrlInfo = urlInfo;
			this.mHandler = handler;
			this.mValuePairs = valuePairs;
			
			this.mResponseProcessor = responseProcessor;
		}
		
		public void run() {
			boolean retryFlag = true;	//是否重发
			int retryTimes = 0;
			
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
					InputStream in = null;
					in = HttpConnect.getInstance().post(mUrlInfo, mValuePairs);
					String result = ConvertStream.toJSONString(in);
					in.close();
					
					mResponseProcessor.processResponse(mHandler, result);					
				}catch (IOException e) {
					//重发
					retryFlag = true;
					mHandler.sendEmptyMessage(AppManager.POST_DATA_RETRY);
				}catch(Exception e){
					e.printStackTrace();
					mHandler.sendEmptyMessage(AppManager.POST_DATA_FAILED);
					return;
				}
			
			}
			
			//获取失败
			if(retryTimes >= MAX_RETRY_TIMES){
				mHandler.sendEmptyMessage(AppManager.POST_DATA_FAILED);
			}
			
		} //end run
	}	//end 
	
}

//public class PostProcessor implements Runnable{
//	private Handler mHandler;
//	private ArrayList<NameValuePair> mPostInfo;
//	private IResponseProcessor mResponseProcessor;
//	
//	private static final int SUCC = 0;
//	
//	public PostProcessor(Handler handler, ArrayList<NameValuePair> valuePairs, IResponseProcessor responseProcessor){
//		this.mHandler = handler;
//		this.mPostInfo = valuePairs;
//	}
//	
//	@Override
//	public void run() {
//		try{
//			InputStream in = null;
//			in = HttpConnect.getInstance().post(Config.URL_POST_REGISTER, mPostInfo);
//			String result = ConvertStream.toJSONString(in);
//			in.close();
//			
//			mResponseProcessor.processResponse(mHandler, result);
//			
//		}catch(Exception e){
//			e.printStackTrace();
//			mHandler.sendEmptyMessage(AppManager.NET_EXCEPTION);
//		}
//		
//	}
//}