package cn.ltwc.cft.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.ltwc.cft.R;
import cn.ltwc.cft.activity.ZhishuDetailActivity;
import cn.ltwc.cft.beans.XiaomiWeather;
import cn.ltwc.cft.view.MyListView;

public class XiaoMIWeatherAdapter extends BaseAdapter {
	private Context context;
	private List<XiaomiWeather> datas;

	public XiaoMIWeatherAdapter(Context context, List<XiaomiWeather> datas) {
		super();
		this.context = context;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holde;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_xiao_mi_weather, null);
			holde = new Holder();
			holde.title = (TextView) convertView.findViewById(R.id.title);
			holde.erList = (MyListView) convertView.findViewById(R.id.er_list);
			holde.item = convertView.findViewById(R.id.item);
			convertView.setTag(holde);
		} else {
			holde = (Holder) convertView.getTag();
		}
		final XiaomiWeather model = datas.get(position);
		for (int i = 0; i < model.getListZhishu().size(); i++) {
			if (TextUtils.isEmpty(model.getListZhishu().get(i).getChannelId())) {
				holde.item.setVisibility(View.GONE);
			} else {
				holde.item.setVisibility(View.VISIBLE);
			}
		}
		holde.title.setText(model.getTitle());
		XiaoMIZhishuAdapter ada = new XiaoMIZhishuAdapter(context,
				model.getListZhishu());
		holde.erList.setAdapter(ada);
		holde.erList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int i,
					long id) {
				Intent intent = new Intent(context, ZhishuDetailActivity.class);
				intent.putExtra("channelId", model.getListZhishu().get(i)
						.getChannelId());
				intent.putExtra("headPic", model.getListZhishu().get(i)
						.getHeadPic());
				intent.putExtra("title", model.getListZhishu().get(i)
						.getTitle());
				intent.putExtra("summary", model.getListZhishu().get(i)
						.getSummary());
				intent.putExtra("indexType", model.getListZhishu().get(i)
						.getIndexType());
				context.startActivity(intent);
			}
		});
		return convertView;
	}

	class Holder {
		private TextView title;
		private MyListView erList;
		private View item;
	}
}
