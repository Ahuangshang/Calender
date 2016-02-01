package cn.ltwc.cft.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.Environment;
import android.text.TextUtils;

/**
 * 功能:用于发送get和post请求(httpUrlConnection/httpClient) 作者:mike 时间：2015-11-16
 * 上午9:12:43 修改:
 */
public class HttpUtils {
	@SuppressWarnings("unused")
	private String TAG = "HttpUtils";

	private HttpClient client;// 客户端必须只能够创建一个对象，才能够实现访问受保护资源的功能，它会自己维持一个session

	private HttpUtils() {
		// TODO Auto-generated constructor stub
		client = new DefaultHttpClient();
	}

	private static HttpUtils instance = new HttpUtils();

	public static HttpUtils getInstance() {
		return instance;
	}

	// ========================HttpURLConnection/URLConnection==========================

	/**
	 * 发送get请求(HttpUrlConnection)
	 * 
	 * @param httpUrl
	 *            请求的地址
	 * @param httpParam
	 *            请求的参数
	 * @return
	 */
	public String sendHUCGet(String httpUrl, String httpParam, String apikey) {
		StringBuffer sb = new StringBuffer();// 保存服务器返回的数据

		// 1.拼接地址和参数
		String address = httpUrl + "?" + httpParam;

		try {
			// 2.创建HttpURLConnection对象
			URL url = new URL(address);
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();
			// 3.设置请求方式
			huc.setRequestMethod("GET");
			huc.setConnectTimeout(HttpConfig.CONNECTION_TIMEOUT);// 设置连接超时
			huc.setReadTimeout(HttpConfig.READ_TIMEOUT);

			// 设置apikey
			if (!TextUtils.isEmpty(apikey)) {
				huc.setRequestProperty("apikey", apikey);
			}

			// 4.简介连接
			huc.connect();
			// 5.获取服务器返回的数据
			InputStream is = huc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			String line = "";// 保存每一行的内容

			// 循环读取
			while ((line = br.readLine()) != null) {
				sb.append(line + "\r\n");
			}

			// 6.关闭流
			br.close();
		} catch (ConnectTimeoutException timeout) {// 联网超时
			sb.append("TIME_OUT");
		} catch (Exception e) {// 未知错误
			sb.append("UNKNOW_ERROR");
		}

		// 7.返回服务器返回的结果
		return sb.toString();
	}

	/**
	 * 发送post请求
	 * 
	 * @param httpUrl
	 * @param httpParam
	 * @return
	 */
	public String sendHUCPost(String httpUrl, String httpParam) {
		StringBuffer sb = new StringBuffer();// 保存服务器返回的数据

		try {
			// 2.创建HttpUrlConnection对象
			URL url = new URL(httpUrl);
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();

			// 3.设置请求方式
			huc.setRequestMethod("POST");
			huc.setDoInput(true);// 允许输入
			huc.setDoOutput(true);// 允许输出
			huc.setUseCaches(false);// 禁止缓存
			huc.setConnectTimeout(HttpConfig.CONNECTION_TIMEOUT);// 设置连接超时
			huc.setReadTimeout(HttpConfig.READ_TIMEOUT);// 设置读取超时

			// 4.建立连接
			huc.connect();

			// 5.向服务器传递参数（传递数据量大，且安全）
			OutputStream os = huc.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			dos.writeBytes(httpParam);

			dos.flush();
			dos.close();

			// 6.读取服务器数据
			// 5.获取服务器返回的数据
			InputStream is = huc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			String line = "";// 保存每一行的内容

			// 循环读取
			while ((line = br.readLine()) != null) {
				sb.append(line + "\r\n");
			}

			// 6.关闭流
			br.close();

		} catch (ConnectTimeoutException timeout) {// 联网超时
			sb.append("TIME_OUT");
		} catch (Exception e) {// 未知错误
			sb.append("UNKNOW_ERROR");
		}

		return sb.toString();
	}

	// ========================HttpClient==========================
	/**
	 * 使用HttpClient发送get请求
	 * 
	 * @param httpUrl
	 *            请求的地址
	 * @param httpParam
	 *            请求的参数
	 * @return
	 */
	public String sendHCGet(String httpURL, String httpParam) {
		StringBuffer sb = new StringBuffer();
		// 1.创建HttpClient对象
		// 2.创建HttpClient对象
		HttpGet get = new HttpGet(httpURL + "?" + httpParam);
		try {
			// 3.执行get请求
			HttpResponse response = client.execute(get);
			// 4.获取服务器返回的数据
			HttpEntity entity = response.getEntity();
			// 获取输入流
			InputStream is = entity.getContent();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader bf = new BufferedReader(isr);

			String line = "";
			while ((line = bf.readLine()) != null) {
				sb.append(line);
				sb.append("\r\n");
			}
		} catch (ConnectTimeoutException timeout) {// 联网超时
			sb.append("TIME_OUT");
		} catch (Exception e) {// 未知错误
			sb.append("UNKNOW_ERROR");
		}

		return sb.toString();
	}

	/**
	 * 使用HttpClent发送post请求
	 * 
	 * @param httpURL
	 *            请求地址
	 * @param params
	 *            集合
	 * @param httpParam
	 *            参数拼接
	 * @return
	 */
	public String sendHCPost(String httpURL, List<NameValuePair> params,
			String httpParam) {
		StringBuffer sb = new StringBuffer();

		// 1.创建HttpClent对象
		// 2.创建HttpPost对象
		// 地址
		HttpPost post = new HttpPost(httpURL);
		// 3.设置请求参数
		// 3.1创建HttpEntity对象(封装请求参数)
		HttpEntity entity = null;

		try {
			if (params != null) {
				// 设置请求方式一：将参数封装到集合中
				// //创建请求参数
				// //用户名，密码
				// 设置请求参数
				entity = new UrlEncodedFormEntity(params);
			} else {
				// 设置请求方式二：将参数拼接成字符串
				entity = new StringEntity(httpParam, "utf-8");
				// ((StringEntity)entity).setContentEncoding("UTF-8");
				// ((StringEntity)entity).setContentType("application/json");

			}

			// httpEntity
			post.setEntity(entity);

			// 4.发送post请求
			HttpResponse response = client.execute(post);
			// 判断服务器返回是否成功(200代表成功)
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 5.获取服务器返回的结果
				HttpEntity httpEntity = response.getEntity();
				sb.append(EntityUtils.toString(httpEntity));
			}
		} catch (ConnectTimeoutException timeout) {// 联网超时
			sb.append("TIME_OUT");
		} catch (Exception e) {// 未知错误
			sb.append("UNKNOW_ERROR");
		}

		return sb.toString();
	}

	/**
	 * 下载文件到sdcard(默认存储到外置内存卡的根目录,本地文件名就是网路文件名)
	 * 
	 * @param webPath
	 *            文件的网络地址
	 * @param fileNameStr
	 *            文件名
	 */
	public String downloadsFile(String webPath, String fileNameStr) {
		StringBuffer sb = new StringBuffer();
		// 1.文件在服务器的地址
		// ------------------------------
		String urlPath = webPath + fileNameStr;

		try {
			// 2.创建UrlConnection对象
			URL url = new URL(urlPath);
			// Url连接
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();

			// 3.获取输入流
			InputStream is = urlConnection.getInputStream();

			// 4.保存到sd卡中
			File file = new File(Environment.getExternalStorageDirectory()
					+ File.separator + fileNameStr);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);

			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}

			fos.flush();

			fos.close();
			is.close();

			sb.append("SUCCESS");// 下载成功
		} catch (Exception e) {// 未知错误

			sb.append("UNKNOW_ERROR");// 下载失败
		}

		return sb.toString();
	}

	/**
	 * 上传任意文件到服务器
	 * 
	 * @param webPath
	 *            服务器地址
	 * @param fileName
	 *            文件名
	 * @return 服务器的应答消息
	 */
	public String uploadAnyFile(String webPath, String fileName) {

		StringBuffer sb = new StringBuffer();// 服务器应答消息

		try {
			/***
			 * 上传文件
			 */
			// 获得统一资源定位符
			URL url = new URL(webPath);
			// 获得HttpURLConnection对象 uc
			HttpURLConnection uc = (HttpURLConnection) url.openConnection();

			// =======================
			// 设置网络连接的校验
			// 定义字符串(回车/换行)
			String end = "\r\n";
			// 分隔符
			String Dps = "--";
			// 分界字符串
			String Boundary = "******";

			// ===========================
			// 设置请求方式 post请求
			uc.setRequestMethod("POST");
			// 设置输入输出流是否开启
			uc.setDoInput(true);
			uc.setDoOutput(true);
			uc.setUseCaches(false);
			// ===============================
			// 设置请求的属性(连续性)
			uc.setRequestProperty("Connection", "Keep-Alive");
			// 设置请求属性是否支持中文(字符集)
			uc.setRequestProperty("Charset", "UTF-8");
			// 设置请求的属性(内容-类型,相关内容数据,分界字符串等)
			uc.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + Boundary);
			// ====================================

			// 获得网络输出流
			OutputStream outStream = uc.getOutputStream();
			// 数据输出流
			DataOutputStream dos = new DataOutputStream(outStream);
			// 寻找到要上传文件的路径
			String pps = "/data/" + fileName;

			// =============================
			// 写入头文件，验证头("--******\r\n")
			dos.writeBytes(Dps + Boundary + end);
			// 内容解析
			dos.writeBytes("Content-Disposition:form-data;name=\"file\";filename=\""
					+ fileName + "\"" + end);
			// 写入(回车/换行)
			dos.writeBytes(end);
			// =============================================
			// 创建文件对象(来自sdcard里面的文件)
			File file = new File(pps);
			// 文件输入流
			FileInputStream fis = new FileInputStream(file);

			// 创建缓存数组
			byte buff[] = new byte[1024];
			// 标识
			int temp = 0;

			// 写入数据
			while ((temp = fis.read(buff)) != -1) {
				dos.write(buff, 0, temp);
			}
			// 关闭文件输入流
			fis.close();
			// 写入(回车/换行)
			dos.writeBytes(end);
			// 写入校验尾文件("--******--\r\n")
			dos.writeBytes(Dps + Boundary + Dps + end);

			// ==============================================
			/***
			 * 验证HTTP返回信息
			 */
			// 获得网络上面的输入流
			InputStream inStream = uc.getInputStream();
			// 转换流
			InputStreamReader isr = new InputStreamReader(inStream);
			// 加强流
			BufferedReader br = new BufferedReader(isr);
			// 结果
			sb.append(br.readLine());

			// 显示结果
			// Message msg = new Message();
			// msg.obj = line;
			// handler.sendMessage(msg);
			// // 关闭流对象
			// inStream.close();
			// dos.close();
			// isr.close();
			// br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sb.append("UNKNOW_ERROR");
		}

		return sb.toString();
	}

}
