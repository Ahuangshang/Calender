package cn.ltwc.cft.adapter;

import java.util.List;

import cn.ltwc.cft.R;
import cn.ltwc.cft.beans.XiaomiWeather;
import cn.ltwc.cft.view.MyListView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
			convertView.setTag(holde);
		} else {
			holde = (Holder) convertView.getTag();
		}
		holde.title.setText(datas.get(position).getTitle());
		XiaoMIZhishuAdapter ada = new XiaoMIZhishuAdapter(context, datas.get(
				position).getListZhishu());
		holde.erList.setAdapter(ada);

		return convertView;
	}

	class Holder {
		private TextView title;
		private MyListView erList;
	}
}
