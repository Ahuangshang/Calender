package cn.ltwc.cft.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.widget.TextView;
import cn.ltwc.cft.R;
import cn.ltwc.cft.beans.HistoryOnTodayBean;
import cn.ltwc.cft.view.TitleView;

public class HistoryDetailActivity extends BaseActivity {
	private TitleView title;
	private TextView year, titleName, content;
	private HistoryOnTodayBean bean;
	private int month_c = 0;
	private int day_c = 0;
	private String currentDate = "";

	public HistoryDetailActivity() {
		super(R.layout.activity_history_detail);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		title = (TitleView) findViewById(R.id.title);
		title.setTitletext("历史上的今天");
		year = (TextView) findViewById(R.id.history_year);
		titleName = (TextView) findViewById(R.id.history_title);
		content = (TextView) findViewById(R.id.history_event);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		bean = (HistoryOnTodayBean) getIntent().getSerializableExtra("bean");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		currentDate = sdf.format(date); // 当期日期
		month_c = Integer.parseInt(currentDate.split("-")[1]);
		day_c = Integer.parseInt(currentDate.split("-")[2]);
	}

	@Override
	public void bindView() {
		// TODO Auto-generated method stub
		year.setText(bean.getYear() + month_c + "月" + day_c + "日");
		titleName.setText(bean.getTitle());
		content.setText(bean.getEvent());
	}

}
