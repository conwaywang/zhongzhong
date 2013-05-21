package com.fumuquan.net;

import android.os.Handler;

/**
 * 网络请求的返回处理接口
 * @author conwaywang
 * @date 20130329
 */
public interface IResponseProcessor {
	public void processResponse(Handler handler, String jsonResult);
}
