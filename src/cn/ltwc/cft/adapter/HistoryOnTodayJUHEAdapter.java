package cn.ltwc.cft.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.ltwc.cft.R;
import cn.ltwc.cft.beans.HistoryOnTodayBeanJUHE;

public class HistoryOnTodayJUHEAdapter extends BaseAdapter {
	private List<HistoryOnTodayBeanJUHE> juheList;
	private Context context;

	public HistoryOnTodayJUHEAdapter(List<HistoryOnTodayBeanJUHE> juheList,
			Context context) {
		super();
		this.juheList = juheList;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return juheList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return juheList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vv = null;
		ViewHodler hodler;
		if (convertView == null) {
			vv = LayoutInflater.from(context).inflate(
					R.layout.item_history_on_today, null);
			hodler = new ViewHodler();
			hodler.year = (TextView) vv.findViewById(R.id.year);
			hodler.title = (TextView) vv.findViewById(R.id.title);
			hodler.event = (TextView) vv.findViewById(R.id.event);
			vv.setTag(hodler);
		} else {
			vv = convertView;
			hodler = (ViewHodler) vv.getTag();
		}
		hodler.event.setVisibility(View.GONE);
		hodler.title.setText(juheList.get(position).getTitle());
		String year = juheList.get(position).getYear();
		year = year.substring(0, year.indexOf("年"));
		hodler.year.setText(year + "年");
		return vv;
	}

	class ViewHodler {
		TextView year, title, event;
	}
}
