package cn.ltwc.cft.http;

/**
 * 功能:封装了所有的网络请求 作者:mike 时间：2015-11-17 上午9:07:03 修改:
 */
public class HttpFactory {
	/**
	 * 查询美女图片
	 * 
	 * @param responce
	 *            服务器返回接口对象
	 * @param num
	 *            返回数量，最大50
	 * @param requestFlag
	 *            请求标记
	 */
	public static void meinvTP(ServiceResponce responce, int num, int requestFlag) {
		// 1.创建请求参数对象
		RequestParams params = new RequestParams();
		// 2.设置请求参数
		params.setHttpUrl(HttpUrls.URL_GETMEINVSTJ);// 设置地址

		// 拼接请求参数
		String httpParams = "num=" + num;

		params.setHttpParam(httpParams);// 设置参数

		// 设置apikey 90fff149d5785e1cac8428e1895f0253
		params.setApiKey("90fff149d5785e1cac8428e1895f0253");
		// 设置服务器应答接口对象
		params.setResponce(responce);

		// 设置请求标记
		params.setRequestFlag(requestFlag);

		// 3.创建请求对象
		HttpCore core = new HttpCore();

		// 4.执行请求
		core.execute(params);
	}

	/**
	 * 
	 * @param responce
	 * @param "id=0&rows=20&classify=0"
	 * @param requestFlag
	 */
	public static void meinvTPatiangou(ServiceResponce responce, int id, int rows, int classify, int requestFlag) {
		// 1.创建请求参数对象
		RequestParams params = new RequestParams();
		// 2.设置请求参数
		params.setHttpUrl(HttpUrls.URL_GETMEINVSTJ);// 设置地址

		// 拼接请求参数
		String httpParams = "id=" + id + "&rows=" + rows + "&classify=" + classify;

		params.setHttpParam(httpParams);// 设置参数

		// 设置apikey 90fff149d5785e1cac8428e1895f0253
		params.setApiKey("90fff149d5785e1cac8428e1895f0253");
		// 设置服务器应答接口对象
		params.setResponce(responce);

		// 设置请求标记
		params.setRequestFlag(requestFlag);

		// 3.创建请求对象
		HttpCore core = new HttpCore();

		// 4.执行请求
		core.execute(params);
	}

	/**
	 * 查询电话号码
	 * 
	 * @param responce
	 *            服务器返回接口对象
	 * @param num
	 *            电话号码
	 * 
	 * @param requestFlag
	 *            请求标记
	 */
	public static void PhoneNumber(ServiceResponce responce, String tel, int requestFlag) {
		// 1.创建请求参数对象
		RequestParams params = new RequestParams();
		// 2.设置请求参数
		params.setHttpUrl(HttpUrls.URL_PHONENUMBER);// 设置地址

		// 拼接请求参数
		String httpParams = "tel=" + tel;

		params.setHttpParam(httpParams);// 设置参数

		// 设置apikey 90fff149d5785e1cac8428e1895f0253
		params.setApiKey("90fff149d5785e1cac8428e1895f0253");
		// 设置服务器应答接口对象
		params.setResponce(responce);

		// 设置请求标记
		params.setRequestFlag(requestFlag);

		// 3.创建请求对象
		HttpCore core = new HttpCore();

		// 4.执行请求
		core.execute(params);
	}

	/**
	 * 
	 * @param responce
	 *            服务器返回接口对象
	 * @param fangfa
	 *            格式//json
	 * @param requestFlag
	 *            请求标记
	 */
	public static void ZhaiYan(ServiceResponce responce, String fangfa, int requestFlag) {
		// 1.创建请求参数对象
		RequestParams params = new RequestParams();
		// 2.设置请求参数
		params.setHttpUrl(HttpUrls.URL_ZHAIYAN);// 设置地址

		// 拼接请求参数
		String httpParams = "fangfa=" + fangfa;

		params.setHttpParam(httpParams);// 设置参数

		// 设置apikey 90fff149d5785e1cac8428e1895f0253
		params.setApiKey("90fff149d5785e1cac8428e1895f0253");
		// 设置服务器应答接口对象
		params.setResponce(responce);

		// 设置请求标记
		params.setRequestFlag(requestFlag);

		// 3.创建请求对象
		HttpCore core = new HttpCore();

		// 4.执行请求
		core.execute(params);
	}

	/**
	 * 
	 * @param responce
	 *            服务器返回接口对象
	 * @param month
	 *            月份
	 * @param day
	 *            天
	 * @param requestFlag
	 *            请求标记
	 */
	public static void TodayHistory(ServiceResponce responce, int month, int day, int requestFlag) {
		// 1.创建请求参数对象
		RequestParams params = new RequestParams();
		// 2.设置请求参数
		params.setHttpUrl(HttpUrls.URL_ZHAIYAN);// 设置地址

		// 拼接请求参数
		// String httpArg =
		// "month=4&day=6&appkey=1307ee261de8bbcf83830de89caae73f";
		String httpParams = "month=" + month + "&day=" + day + "&appkey=90fff149d5785e1cac8428e1895f0253";

		params.setHttpParam(httpParams);// 设置参数

		// 设置apikey 90fff149d5785e1cac8428e1895f0253
		params.setApiKey("90fff149d5785e1cac8428e1895f0253");
		// 设置服务器应答接口对象
		params.setResponce(responce);

		// 设置请求标记
		params.setRequestFlag(requestFlag);

		// 3.创建请求对象
		HttpCore core = new HttpCore();

		// 4.执行请求
		core.execute(params);
	}

	/**
	 * 得到笑话的方法
	 * 
	 * @param responce
	 * @param page
	 */
	public static void Joke(ServiceResponce responce, int page) {
		RequestParams params = new RequestParams();
		params.setApiKey("90fff149d5785e1cac8428e1895f0253");
		String httpParam = "page=" + page;
		params.setHttpParam(httpParam);
		params.setHttpUrl(HttpUrls.URL_JOKE);
		params.setRequestMethod(HttpConfig.METHOD_GET_HUC_JSON);
		params.setResponce(responce);
		HttpCore core = new HttpCore();
		core.execute(params);
	}

	public static void History(ServiceResponce responce, String data) {
		RequestParams params = new RequestParams();
		//params.setApiKey("145da38f81409");
		String httpParam = "key=145da38f81409&"+"day=" + data;
		params.setHttpParam(httpParam);
		params.setHttpUrl(HttpUrls.URL_HISTORY_TODAY);
		params.setRequestMethod(HttpConfig.METHOD_GET_HUC_JSON);
		params.setResponce(responce);
		HttpCore core = new HttpCore();
		core.execute(params);

	}
}
