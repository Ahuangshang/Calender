package cn.ltwc.cft.adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.ltwc.cft.R;
import cn.ltwc.cft.beans.HistoryOnTodayBean;

public class HistoryOnTodayAdapter extends BaseAdapter {
	private Context context;
	private List<HistoryOnTodayBean> list;

	public HistoryOnTodayAdapter(Context context, List<HistoryOnTodayBean> list) {
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vv = null;
		ViewHodler hodler;
		if (convertView == null) {
			vv = LayoutInflater.from(context).inflate(R.layout.item_history_on_today, null);
			hodler = new ViewHodler();
			hodler.year = (TextView) vv.findViewById(R.id.year);
			hodler.title = (TextView) vv.findViewById(R.id.title);
			hodler.event = (TextView) vv.findViewById(R.id.event);
			vv.setTag(hodler);
		} else {
			vv = convertView;
			hodler = (ViewHodler) vv.getTag();
		}
		hodler.title.setText(list.get(position).getTitle());
		hodler.event.setText(list.get(position).getEvent());
		hodler.year.setText(list.get(position).getYear());
		return vv;
	}

	class ViewHodler {
		TextView year, title, event;
	}
}
