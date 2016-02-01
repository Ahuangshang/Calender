package cn.ltwc.cft.adapter;

import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.ltwc.cft.R;
import cn.ltwc.cft.beans.JokeListBean;

public class JokeAdapter extends BaseAdapter {
	private Context c;
	List<JokeListBean> jokeList;

	public JokeAdapter(Context c, List<JokeListBean> jokeList) {
		super();
		this.c = c;
		this.jokeList = jokeList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		return jokeList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return jokeList.get(position);
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
		ViewHodle viewHodle = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(c).inflate(R.layout.joke_item,
					null);
			viewHodle = new ViewHodle();
			viewHodle.title = (TextView) convertView
					.findViewById(R.id.joke_item_title);
			viewHodle.content = (TextView) convertView
					.findViewById(R.id.joke_item_content);
			convertView.setTag(viewHodle);
		} else {
			viewHodle = (ViewHodle) convertView.getTag();
		}
		JokeListBean bean = (JokeListBean) getItem(position);
		viewHodle.title.setText(bean.getJokeTitle());
		viewHodle.content.setText(bean.getJokeContent());

		return convertView;
	}

	class ViewHodle {
		TextView title, content;

		public ViewHodle() {
			super();
		}

	}
}
