package cn.ltwc.cft.http;

import java.util.List;

import org.apache.http.NameValuePair;

/**
 * 功能:封装了网络请求的参数(javaBean) 作者:mike 时间：2015-11-17 上午9:11:17 修改:
 */
public class RequestParams {
	private String httpUrl;// 请求地址
	private String httpParam;// 请求参数的拼接
	private List<NameValuePair> params;// 请求参数的集合

	private ServiceResponce responce;// 服务器应答接口

	private int requestFlag;// 请求码

	private String apiKey;// 接口key

	private int requestMethod = HttpConfig.METHOD_GET_HUC_JSON;// 请求方式

	public RequestParams() {
		super();
	}

	public String getHttpUrl() {
		return httpUrl;
	}

	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}

	public String getHttpParam() {
		return httpParam;
	}

	public void setHttpParam(String httpParam) {
		this.httpParam = httpParam;
	}

	public int getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(int requestMethod) {
		this.requestMethod = requestMethod;
	}

	public List<NameValuePair> getParams() {
		return params;
	}

	public void setParams(List<NameValuePair> params) {
		this.params = params;
	}

	public ServiceResponce getResponce() {
		return responce;
	}

	public void setResponce(ServiceResponce responce) {
		this.responce = responce;
	}

	public int getRequestFlag() {
		return requestFlag;
	}

	public void setRequestFlag(int requestFlag) {
		this.requestFlag = requestFlag;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	@Override
	public String toString() {
		return "RequestParams [httpUrl=" + httpUrl + ", httpParam=" + httpParam
				+ ", params=" + params + ", responce=" + responce
				+ ", requestFlag=" + requestFlag + ", apiKey=" + apiKey
				+ ", requestMethod=" + requestMethod + "]";
	}
}
