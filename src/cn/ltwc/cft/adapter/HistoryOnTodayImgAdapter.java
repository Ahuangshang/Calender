package cn.ltwc.cft.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.ltwc.cft.R;
import cn.ltwc.cft.activity.HistoryDetailJUHEActivity;
import cn.ltwc.cft.beans.HistoryOnTodayImgBean;
import cn.ltwc.cft.myinterface.ImgLoadListener;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class HistoryOnTodayImgAdapter extends BaseAdapter {
	private Context context;
	private List<HistoryOnTodayImgBean> imgUrl;
	private ImgLoadListener listener;

	public HistoryOnTodayImgAdapter(Context context,
			List<HistoryOnTodayImgBean> imgUrl, ImgLoadListener listener) {
		this.context = context;
		this.imgUrl = imgUrl;
		this.listener = listener;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int size = imgUrl.size();

		return size;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return imgUrl.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		viewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_history_on_today_img, null);
			holder = new viewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.img_title);
			holder.img = (ImageView) convertView.findViewById(R.id.img_show);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}
		String t = imgUrl.get(position).getImgTitle();
		holder.title.setText(t);
		Glide.with(context).load(imgUrl.get(position).getImgUrl())
				.listener(new RequestListener() {

					@Override
					public boolean onException(Exception arg0, Object arg1,
							Target arg2, boolean arg3) {
						// TODO Auto-generated method stub
						HistoryDetailJUHEActivity.instance.count.add("0");
						if (listener != null) {
							listener.ImgLoad();
						}
						return false;
					}

					@Override
					public boolean onResourceReady(Object arg0, Object arg1,
							Target arg2, boolean arg3, boolean arg4) {
						// TODO Auto-generated method stub
						HistoryDetailJUHEActivity.instance.count.add("0");
						if (listener != null) {
							listener.ImgLoad();
						}
						return false;
					}
				}).into(holder.img);
		holder.title.setVisibility(TextUtils.isEmpty(t) ? View.GONE
				: View.VISIBLE);
		return convertView;
	}

	class viewHolder {
		TextView title;
		ImageView img;
	}
}
