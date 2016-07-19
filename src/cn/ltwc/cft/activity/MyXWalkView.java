package cn.ltwc.cft.activity;

import org.xwalk.core.XWalkNavigationHistory.Direction;
import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkSettings;
import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;
import cn.ltwc.cft.AppManager;
import cn.ltwc.cft.R;
import cn.ltwc.cft.data.Constant;
import cn.ltwc.cft.view.TitleView;

@SuppressLint("InlinedApi")
public class MyXWalkView extends Activity {

	private XWalkView webView;
	private ProgressBar bar;
	private TitleView title;
	private String webURL;// 加载的网址
	private String webTitle;// 加载网页的标题

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
		setContentView(R.layout.activity_my_xwalk);
		initView();
		initData();
		bindView();
		webConfig();

	}

	private void webConfig() {
		// TODO Auto-generated method stub
		// 添加对javascript支持
		XWalkPreferences.setValue("enable-javascript", true);
		// 开启调式,支持谷歌浏览器调式
		XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);
		// 置是否允许通过file url加载的Javascript可以访问其他的源,包括其他的文件和http,https等其他的源
		XWalkPreferences.setValue(
				XWalkPreferences.ALLOW_UNIVERSAL_ACCESS_FROM_FILE, true);
		// JAVASCRIPT_CAN_OPEN_WINDOW
		XWalkPreferences.setValue(XWalkPreferences.JAVASCRIPT_CAN_OPEN_WINDOW,
				true);
		// enable multiple windows.
		XWalkPreferences.setValue(XWalkPreferences.SUPPORT_MULTIPLE_WINDOWS,
				true);
		// 设置滑动样式。。。
		webView.setHorizontalScrollBarEnabled(false);
		webView.setVerticalScrollBarEnabled(false);
		webView.setScrollBarStyle(XWalkView.SCROLLBARS_OUTSIDE_INSET);
		webView.setScrollbarFadingEnabled(true);
		// 获取setting
		XWalkSettings mMSettings = webView.getSettings();
		// 支持空间导航
		mMSettings.setSupportSpatialNavigation(true);
		mMSettings.setBuiltInZoomControls(true);
		mMSettings.setSupportZoom(true);
		webView.load(webURL, null);
	}

	private void initView() {
		// TODO Auto-generated method stub
		webView = (XWalkView) findViewById(R.id.xweb);
		bar = (ProgressBar) findViewById(R.id.bar);
		title = (TitleView) findViewById(R.id.xweb_title);

	}

	private void initData() {
		// TODO Auto-generated method stub
		webURL = getIntent().getStringExtra(Constant.WEBURL);
		webTitle = getIntent().getStringExtra(Constant.WEBTITLE);
		title.setTitletext(webTitle);
		title.setRightBtnTextVisibility(View.VISIBLE);
		title.setRightText("关闭");
	}

	private void bindView() {
		// TODO Auto-generated method stub
		title.getRightText().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		title.getLeftIcon().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (webView.getNavigationHistory().canGoBack()) {
					webView.getNavigationHistory().navigate(Direction.BACKWARD,
							1);// 返回上一页面
				} else {
					finish();
				}
			}
		});
		webView.setResourceClient(new XWalkResourceClient(webView) {
			@Override
			public void onDocumentLoadedInFrame(XWalkView arg0, long arg1) {
				// TODO Auto-generated method stub
				super.onDocumentLoadedInFrame(arg0, arg1);
			}

			@Override
			public void onProgressChanged(XWalkView view, int process) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, process);
				bar.setProgress(process * 100);
				if (process >= 100) {
					bar.postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							bar.setVisibility(View.GONE);
						}
					}, 200);
				} else {
					bar.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public boolean shouldOverrideUrlLoading(XWalkView view, String url) {
				// TODO Auto-generated method stub

				if (url.startsWith("http") || url.startsWith("https")) {
					return super.shouldOverrideUrlLoading(view, url);
				} else {
					return true;
				}
			}

		});
		webView.setUIClient(new XWalkUIClient(webView) {
			@Override
			public void onFullscreenToggled(XWalkView view, boolean toggle) {
				// TODO Auto-generated method stub
				super.onFullscreenToggled(view, toggle);
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (webView != null) {
			webView.onDestroy();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (webView != null) {
			webView.pauseTimers();
			webView.onHide();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (webView != null) {
			webView.resumeTimers();
			webView.onShow();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (webView != null) {
			webView.onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		if (webView != null) {
			webView.onNewIntent(intent);
		}
	}

}
