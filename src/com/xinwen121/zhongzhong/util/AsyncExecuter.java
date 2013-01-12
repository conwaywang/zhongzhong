package com.xinwen121.zhongzhong.util;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncExecuter {
	private static AsyncExecuter sAsyncExecuter;
	private ExecutorService mExecutor;

	private AsyncExecuter() {
		mExecutor = Executors.newFixedThreadPool(10);///////////////
	}

	public static AsyncExecuter getInstance() {
		if (sAsyncExecuter == null) {
			sAsyncExecuter = new AsyncExecuter();
		}

		return sAsyncExecuter;
	}

	public void execute(Runnable command) {
		if(mExecutor == null)
			mExecutor = Executors.newFixedThreadPool(10);
		mExecutor.execute(command);
	}
	
	public void shutdown()
	{
		mExecutor.shutdownNow();
		mExecutor = null;
		sAsyncExecuter = null;
		
	}
}
