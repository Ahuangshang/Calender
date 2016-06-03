package cn.ltwc.cft.http;

import android.os.AsyncTask;

/**
 * 功能:发送网络请求的核心类：完成具体的网络发送功能 作者:mike 时间：2015-11-17 上午9:31:25 修改:
 */
public class HttpCore extends AsyncTask<RequestParams, Integer, String> {
	@SuppressWarnings("unused")
	private String TAG = "HttpCore";
	private ServiceResponce responce;
	private int requestFlag;// 请求码

	/**
	 * 更具请求参数发送网络请求
	 */
	@Override
	protected String doInBackground(RequestParams... params) {
		// 1.获取请求参数对象
		RequestParams requestParams = params[0];

		// Log.d(TAG, requestParams.getHttpUrl());
		// Log.d(TAG, requestParams.getHttpParam());

		String httpUrl = requestParams.getHttpUrl();
		String httpParam = requestParams.getHttpParam();
		String apiKey = requestParams.getApiKey();

		responce = requestParams.getResponce();

		// 2.更具配置发送不同的网络请求
		int requestMethod = requestParams.getRequestMethod();// 获取请求方式

		String result = null;// 服务器的返回结果

		if (requestMethod == HttpConfig.METHOD_GET_HUC_JSON) {
			result = HttpUtils.getInstance().sendHUCGet(httpUrl, httpParam, apiKey);
		} else if (requestMethod == HttpConfig.METHOD_POST_HUC_JSON) {

			result = HttpUtils.getInstance().sendHUCPost(httpUrl, httpParam);

		} else if (requestMethod == HttpConfig.METHOD_GET_HC_JSON) {

			result = HttpUtils.getInstance().sendHCGet(httpUrl, httpParam);

		} else if (requestMethod == HttpConfig.METHOD_POST_HC_JSON) {

			result = HttpUtils.getInstance().sendHCPost(httpUrl, requestParams.getParams(), httpParam);

		} else if (requestMethod == HttpConfig.METHOD_DOWNLOAD) {

			result = HttpUtils.getInstance().downloadsFile(httpUrl, httpParam);

		} else if (requestMethod == HttpConfig.METHOD_UPLOAD) {
			result = HttpUtils.getInstance().uploadAnyFile(httpUrl, httpParam);
		}
		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (result.equals("TIMEOUT")) {// 超时
			responce.httpTimeOut(requestFlag);
		} else if (result.equals("UNKNOW_ERROR")) {// 未知错误
			responce.httpError(requestFlag);
		} else {// 成功(服务器有应答数据)
			responce.httpSuccess(result, requestFlag);
		}
	}

	// 服务器返回的状态
	// 联网失败（没有网络）
	// 超时（有网络，但是网络比较差）
	// 成功
	// 未知错误
}
