package cn.ltwc.cft.http;

/**
 * 功能:网络请求的配置 作者:mike 时间：2015-11-17 上午9:14:23 修改:
 */
public class HttpConfig {
	public static final int CONNECTION_TIMEOUT = 5000;// 连接超时
	public static final int READ_TIMEOUT = 5000;// 读取超时

	// 请求方式
	// -----------------------------------------
	public static final int METHOD_GET_HUC_JSON = 0x0001;// get请求，使用httpUrlConnection，数据传输格式是json(默认请求方式)
	public static final int METHOD_POST_HUC_JSON = 0x0002;// post请求，使用httpUrlConnection，数据传输格式是json
	public static final int METHOD_GET_HC_JSON = 0x0003;// get请求，使用httpClient，数据传输格式是json
	public static final int METHOD_POST_HC_JSON = 0x0004;// post请求，使用httpClient，数据传输格式是json
	public static final int METHOD_DOWNLOAD = 0x0005;// 下载任意文件
	public static final int METHOD_UPLOAD = 0x0006;// 上传任意文件

}
