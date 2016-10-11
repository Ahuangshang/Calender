package cn.ltwc.cft.adapter;

import java.util.List;

import com.bumptech.glide.Glide;

import cn.ltwc.cft.R;
import cn.ltwc.cft.beans.XiaomiZhishuList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class XiaoMIZhishuAdapter extends BaseAdapter {
	private Context context;
	private List<XiaomiZhishuList> list;

	public XiaoMIZhishuAdapter(Context context, List<XiaomiZhishuList> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
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
		Holder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_xiao_mi_zhi_shu, null);
			holder = new Holder();
			holder.img = (ImageView) convertView.findViewById(R.id.img);
			holder.erTitle = (TextView) convertView.findViewById(R.id.er_title);
			holder.erContent = (TextView) convertView
					.findViewById(R.id.er_content);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		Glide.with(context).load(list.get(position).getImage())
				.into(holder.img);
		holder.erTitle.setText(list.get(position).getTitle());
		holder.erContent.setText(list.get(position).getSummary());

		return convertView;
	}

	class Holder {
		private ImageView img;
		private TextView erTitle, erContent;

	}
}
