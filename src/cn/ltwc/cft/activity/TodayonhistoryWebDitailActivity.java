package cn.ltwc.cft.activity;

import cn.ltwc.cft.R;
import cn.ltwc.cft.view.TitleView;

/**
 * 
 * TODO:历史上的今天(网页详细版)
 * 
 * @author huangshang 2015-11-23 下午3:33:18
 * @Modified_By:
 */
public class TodayonhistoryWebDitailActivity extends BaseWebActivity {

	private static String webURL = "http://www.todayonhistory.com/";// 历史上的今天官网
	private TitleView title;

	public TodayonhistoryWebDitailActivity() {
		super(R.layout.activity_todayonhistory_web_ditail);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		// title = (TitleView) findViewById(R.id.tt_title);
		// title.setTitletext("历史上的今天");
		// title.setRightVisibility(View.GONE);
		setWebView(R.id.todayonhistory);
		setEmpty(R.id.tt_emptyview);
		// setTitle(title);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		setWebURL(webURL);
	}

}
