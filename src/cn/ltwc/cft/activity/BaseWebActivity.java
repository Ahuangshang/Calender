package cn.ltwc.cft.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import cn.ltwc.cft.AppManager;
import cn.ltwc.cft.utils.BrowserJsInject;
import cn.ltwc.cft.view.TitleView;

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

	private String webURL;// 网络请求地址
	private TitleView title;
	private boolean islandport;
	private ProgressBar bar;

	public void setBar(int id) {
		this.bar = (ProgressBar) findViewById(id);
	}

	public void setTitle(TitleView title) {
		this.title = title;
	}

	public void setWebView(int id) {
		this.webView = (WebView) findViewById(id);

	}

	public String getWebURL() {
		return webURL;
	}

	public void setWebURL(String webURL) {
		this.webURL = webURL;
	}

	public BaseWebActivity(int layoutResId) {
		this.layoutResId = layoutResId;
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// 沉浸式导航栏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		AppManager.getInstance().addActivity(this);
		if (layoutResId != -1) {
			setContentView(layoutResId);

		}

		initData();
		initView();
		WebViewConfig(webURL);
		if (title != null) {

			title.getLeftIcon().setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (webView.canGoBack()) {
						webView.goBack();
					} else {
						finish();
					}
				}
			});
		}

	}

	@SuppressLint("SetJavaScriptEnabled")
	public void WebViewConfig(String webURL) {
		webView.loadUrl(webURL);
		WebSettings settings = webView.getSettings();
		settings.setSupportZoom(true); // 支持缩放
		settings.setBuiltInZoomControls(true); // 启用内置缩放装置
		settings.setJavaScriptEnabled(true); // 启用JS脚本
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(BrowserJsInject.fullScreenByJs(url));
			}
		});
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				bar.setProgress(newProgress * 100);
				if (newProgress >= 100) {
					bar.postDelayed(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							bar.setVisibility(View.GONE);
						}
					}, 200);
				} else {
					if (!islandport) {
						bar.setVisibility(View.VISIBLE);
					}
				}
			}

		});
		webView.addJavascriptInterface(new Object() {
			@JavascriptInterface
			public void playing() {
				Log.i("video", "=======================");
				if (islandport) {
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
					title.setVisibility(View.GONE);
					bar.setVisibility(View.GONE);
				} else {
					title.setVisibility(View.VISIBLE);
					bar.setVisibility(View.VISIBLE);
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				}

			}

		}, "local_obj");
	}

	/**
	 * 当横竖屏切换时会调用该方法
	 * 
	 * @author
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			islandport = true;
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			islandport = false;
		}
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
