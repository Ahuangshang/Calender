package cn.ltwc.cft.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.ltwc.cft.R;
import cn.ltwc.cft.data.Constant;
import cn.ltwc.cft.view.TitleView;

/**
 * 
 * TODO:更多的Activity
 * 
 * @author huangshang 2015-11-15 下午10:47:56
 * @Modified_By:
 */
public class MoreActivity extends BaseActivity {
	private TitleView title;
	private ListView list;
	private String al[] = { "手机号码归属地查询", "中华军事", "内涵段子", "宅男天堂", "历史上的今天", "QQ空間遊戲", "wifi密码查看" };// 数据集合
	private int al2[] = { R.drawable.phone, R.drawable.zhonghuajunshi, R.drawable.joke, R.drawable.zhainan,
			R.drawable.todayonhistory, R.drawable.qq, R.drawable.qq };

	public MoreActivity() {
		super(R.layout.activity_more);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		title = (TitleView) findViewById(R.id.more_title);
		title.setLeftIcon(R.drawable.title_back);
		title.setRightVisibility(View.GONE);// 设置右边图片不显示
		title.setTitletext("更多");

		list = (ListView) findViewById(R.id.more_list);// 列表视图
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
	}

	@Override
	public void bindView() {
		// 返回按钮的点击事件
		title.getLeftIcon().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		ListAdapter adapter = new ListAdapter();
		list.setAdapter(adapter);
		// 列表的点击事件
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:
					startActivity(new Intent(c, PhoneActivity.class));
					break;
				case 1:
					// Intent intent = new Intent(c, MyWebViewActivity.class);
					Intent intent = new Intent(c, MyX5WebView.class);
					intent.putExtra(Constant.WEBURL, "http://military.china.com/");
					intent.putExtra(Constant.WEBTITLE, "中华军事");
					startActivity(intent);
					break;
				case 2:
					startActivity(new Intent(c, JokeActivity.class));
					break;
				case 3:
					startActivity(new Intent(c, ZhaiNaniActivity.class));
					break;
				case 4:
					startActivity(new Intent(c, TodayonhistoryActivity.class));
					break;
				case 5:
					Intent qq = new Intent(c, MyX5WebView.class);
					qq.putExtra(Constant.WEBURL, "http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_mainpage");
					qq.putExtra(Constant.WEBTITLE, "QQ");
					startActivity(qq);
					break;
				case 6:
					Intent wifi = new Intent(c, ShowWifiPakActivity.class);
					startActivity(wifi);
					break;

				}
			}
		});
	}

	/**
	 * 
	 * TODO:内部类，ListView的适配器
	 * 
	 * @author huangshang 2015-11-15 下午4:37:19
	 * @Modified_By:
	 */
	@SuppressLint("InflateParams")
	class ListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return al.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return al[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			if (convertView == null) {
				convertView = LayoutInflater.from(c).inflate(R.layout.more_adapter, null);
			}
			TextView text = (TextView) convertView.findViewById(R.id.more_adapter_txt);
			ImageView icon = (ImageView) convertView.findViewById(R.id.more_adapter_img);
			text.setText(al[position]);
			icon.setImageResource(al2[position]);
			return convertView;
		}
	}
}
