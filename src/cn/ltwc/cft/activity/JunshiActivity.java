package cn.ltwc.cft.activity;

import android.view.View;
import cn.ltwc.cft.R;
import cn.ltwc.cft.view.TitleView;

/**
 * 
 * TODO:军事科技的Activity
 * 
 * @author huangshang 2015-11-15 下午10:47:06
 * @Modified_By:
 */
public class JunshiActivity extends BaseWebActivity {
	private TitleView title;
	private static String webURL = "http://military.china.com/";

	public JunshiActivity() {
		super(R.layout.activity_junshi, webURL);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		title = (TitleView) findViewById(R.id.junshi_title);
		title.setTitletext("中华军事");
		title.setRightVisibility(View.GONE);
		setWebView(R.id.junshi_webview);
		setEmpty(R.id.junshi_emptyview);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

}
