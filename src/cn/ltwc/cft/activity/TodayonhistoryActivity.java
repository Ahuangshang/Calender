package cn.ltwc.cft.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.ltwc.cft.R;
import cn.ltwc.cft.adapter.HistoryOnTodayAdapter;
import cn.ltwc.cft.adapter.HistoryOnTodayJUHEAdapter;
import cn.ltwc.cft.beans.HistoryOnTodayBean;
import cn.ltwc.cft.beans.HistoryOnTodayBeanJUHE;
import cn.ltwc.cft.data.Constant;
import cn.ltwc.cft.http.HttpFactory;
import cn.ltwc.cft.http.ServiceResponce;
import cn.ltwc.cft.view.TitleView;

/**
 * TODO:历史上的今天
 * 
 * @author LZL
 * 
 */
public class TodayonhistoryActivity extends BaseActivity implements
		ServiceResponce {
	private int month_c = 0;
	private int day_c = 0;
	private String currentDate = "";
	private TitleView title;
	private ListView historyLv;
	private List<HistoryOnTodayBean> list;
	private List<HistoryOnTodayBeanJUHE> juheList;
	private HistoryOnTodayJUHEAdapter juheAdapter;
	private HistoryOnTodayAdapter adapter;
	private View head;
	private TextView headTitle;

	public TodayonhistoryActivity() {
		super(R.layout.activity_todayonhistory);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		title = (TitleView) findViewById(R.id.title);
		historyLv = (ListView) findViewById(R.id.history_lv);
		head = LayoutInflater.from(this).inflate(R.layout.history_head_view,
				null);
		headTitle = (TextView) head.findViewById(R.id.head_title);
		headTitle.setText("历史上的" + month_c + "月" + day_c + "日" + "都发生了什么");
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		list = new ArrayList<HistoryOnTodayBean>();
		juheList = new ArrayList<HistoryOnTodayBeanJUHE>();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		currentDate = sdf.format(date); // 当期日期
		month_c = Integer.parseInt(currentDate.split("-")[1]);
		day_c = Integer.parseInt(currentDate.split("-")[2]);
		getDataFromSerice();
	}

	/**
	 * 获取网络数据
	 */
	private void getDataFromSerice() {
		showWaitingDialog(this);
		// String data = "";
		// if (month_c < 10 && day_c < 10) {
		// data = "0" + month_c + "0" + day_c;
		// } else if (month_c < 10 && day_c >= 10) {
		// data = "0" + month_c + day_c;
		// } else if (month_c >= 10 && day_c < 10) {
		// data = month_c + "0" + day_c;
		// } else if (month_c >= 10 && day_c >= 10) {
		// data = month_c + "" + day_c;
		// }
		// HttpFactory.History(this, data);
		HttpFactory.HistoryJUHE(this, month_c + "/" + day_c);

	}

	@Override
	public void bindView() {
		// TODO Auto-generated method stub
		title.setTitletext("历史上的今天");
		title.setRightText("更多");
		title.setRightBtnTextVisibility(View.VISIBLE);
		// adapter = new HistoryOnTodayAdapter(this, list);
		// historyLv.setAdapter(adapter);
		juheAdapter = new HistoryOnTodayJUHEAdapter(juheList, this);
		historyLv.setAdapter(juheAdapter);
		historyLv.addHeaderView(head);
		title.getRightText().setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TodayonhistoryActivity.this,
						MyXWalkView.class);
				intent.putExtra(Constant.WEBURL,
						"http://www.todayonhistory.com");
				intent.putExtra(Constant.WEBTITLE, "历史上的今天");
				startActivity(intent);
				// startActivity(new Intent(TodayonhistoryActivity.this,
				// TodayonhistoryWebDitailActivity.class));
			}
		});
		historyLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent(TodayonhistoryActivity.this,
				// HistoryDetailActivity.class);
				// intent.putExtra("bean", list.get(position));
				// startActivity(intent);
				if (position == 0) {
					return;
				}
				Intent intent = new Intent(TodayonhistoryActivity.this,
						HistoryDetailJUHEActivity.class);
				intent.putExtra("bean", juheList.get(position - 1));
				startActivity(intent);
			}
		});

	}

	@Override
	public void httpSuccess(String result, int responseFlag) {
		// TODO Auto-generated method stub
		hideWaitingDialog();

		// list.clear();
		// try {
		// JSONObject obj = new JSONObject(result);
		// JSONArray array = obj.optJSONArray("result");
		// if (array != null) {
		// for (int i = array.length() - 1; i > -1; i--) {
		// JSONObject object = array.getJSONObject(i);
		// String title = object.optString("title");
		// String event = object.optString("event");
		// String year = object.optString("date");
		// year = year.substring(0, year.length() - 4);
		// HistoryOnTodayBean bean = new HistoryOnTodayBean();
		// bean.setEvent(event);
		// bean.setTitle(title);
		// bean.setYear(year + "年");
		// list.add(bean);
		// }
		// adapter.notifyDataSetChanged();
		//
		// }
		// } catch (Exception e) {
		// // TODO: handle exception
		// }
		juheList.clear();
		try {
			JSONObject obj = new JSONObject(result);
			JSONArray array = obj.optJSONArray("result");
			if (array != null) {
				for (int i = array.length() - 1; i > -1; i--) {
					JSONObject object = array.getJSONObject(i);
					String title = object.optString("title");
					String data = object.optString("date");
					String e_id = object.optString("e_id");
					HistoryOnTodayBeanJUHE beanJUHE = new HistoryOnTodayBeanJUHE(
							data, title, e_id);
					juheList.add(beanJUHE);
				}
				juheAdapter.notifyDataSetChanged();
			}

		} catch (Exception e) {

		}
	}

	@Override
	public void httpTimeOut(int responseFlag) {
		// TODO Auto-generated method stub
		hideWaitingDialog();
		show("网络连接超时，请稍后再试");
	}

	@Override
	public void httpError(int responseFlag) {
		// TODO Auto-generated method stub
		hideWaitingDialog();
		show("未知异常");
	}

}
