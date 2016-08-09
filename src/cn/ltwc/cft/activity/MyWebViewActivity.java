package cn.ltwc.cft.activity;

import android.view.View;
import android.view.View.OnClickListener;
import cn.ltwc.cft.R;
import cn.ltwc.cft.data.Constant;
import cn.ltwc.cft.view.TitleView;

/**
 * 
 * TODO:军事科技的Activity
 * 
 * @author huangshang 2015-11-15 下午10:47:06
 * @Modified_By:
 */
public class MyWebViewActivity extends BaseWebActivity {
	private TitleView title;
	private String webURL;
	private String webTitle;

	public MyWebViewActivity() {
		super(R.layout.activity_junshi);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		title = (TitleView) findViewById(R.id.junshi_title);
		title.setTitletext(webTitle);
		title.setRightBtnTextVisibility(View.VISIBLE);
		title.setRightText("关闭");
		setWebView(R.id.junshi_webview);
		setBar(R.id.junshi_bar);
		setTitle(title);
		title.getRightText().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		webURL = getIntent().getStringExtra(Constant.WEBURL);
		webTitle = getIntent().getStringExtra(Constant.WEBTITLE);
		setWebURL(webURL);

	}

}
