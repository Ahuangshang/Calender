package cn.ltwc.cft.activity;

import cn.ltwc.cft.AppManager;
import cn.ltwc.cft.view.TitleView;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 
 * TODO:直接加载网页的Activity的基类
 * 
 * @author huangshang 2015-11-23 下午5:27:45
 * @Modified_By:
 */
public abstract class BaseWebActivity extends Activity {

	private int layoutResId = -1;
	private WebView webView;
	private View empty;
	private String webURL;// 网络请求地址
	private TitleView title;

	public void setEmpty(int id) {
		this.empty = findViewById(id);
	}

	public void setTitle(TitleView title) {
		this.title = title;
	}

	public void setWebView(int id) {
		this.webView = (WebView) findViewById(id);

	}

	public BaseWebActivity(int layoutResId, String webURL) {
		this.layoutResId = layoutResId;
		this.webURL = webURL;
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// 沉浸式导航栏
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		AppManager.getInstance().addActivity(this);
		if (layoutResId != -1) {
			setContentView(layoutResId);

		}

		initData();
		initView();
		WebViewConfig(webURL);

	}

	@SuppressLint("SetJavaScriptEnabled")
	public void WebViewConfig(String webURL) {
		webView.loadUrl(webURL);
		WebSettings settings = webView.getSettings();
		settings.setSupportZoom(true); // 支持缩放
		settings.setBuiltInZoomControls(true); // 启用内置缩放装置
		settings.setJavaScriptEnabled(true); // 启用JS脚本
		webView.setWebViewClient(new WebViewClient());
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				if (newProgress > 70) {
					empty.setVisibility(View.GONE);
					// title.setVisibility(View.GONE);
				}
			}
		});
	}

	/** 初始化视图 */
	public abstract void initView();

	/** 初始化数据 */
	public abstract void initData();

	/** 数据与视图的绑定 */
	// public abstract void bindView();

	/**
	 * 按键响应，在WebView中查看网页时，按返回键的时候按浏览历史退回,如果不做此项处理则整个WebView返回退出
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Check if the key event was the Back button and if there's history
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			// 返回键退回
			webView.goBack();
			return true;
		}
		// If it wasn't the Back key or there's no web page history, bubble up
		// to the default
		// system behavior (probably exit the activity)
		return super.onKeyDown(keyCode, event);
	}
}
