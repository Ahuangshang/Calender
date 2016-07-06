package cn.ltwc.cft.adapter;

import java.util.ArrayList;

import com.bumptech.glide.Glide;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import cn.ltwc.cft.R;
import cn.ltwc.cft.beans.TiangouBean;
import cn.ltwc.cft.myinterface.MeNvItemImgClickListener;
import cn.ltwc.cft.rvholder.MeiNvHolder;

public class MeNvAdapter extends RecyclerView.Adapter<MeiNvHolder> {
	private String SRC = "http://tnfs.tngou.net/image";// 图片地址共有部分
	private Context context;
	private ArrayList<TiangouBean> al;
	private MeNvItemImgClickListener listener;

	public MeNvAdapter(Context context, ArrayList<TiangouBean> al, MeNvItemImgClickListener listener) {
		this.al = al;
		this.context = context;
		this.listener = listener;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return al.size();
	}

	@Override
	public void onBindViewHolder(MeiNvHolder holder, int position) {
		// TODO Auto-generated method stub
		holder.title.setText(al.get(position).getTitle());// 标题
		Glide.with(context).load(SRC + al.get(position).getImg()).placeholder(R.drawable.img_loading_default_big)
				.error(R.drawable.img_loading_fail_big).into(holder.Img);
		holder.Img.setTag(position);
		holder.Img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int index = (Integer) v.getTag();
				if (listener != null) {
					listener.onClick(index);
				}
			}
		});
	}

	@Override
	public MeiNvHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		// TODO Auto-generated method stub
		MeiNvHolder holder = new MeiNvHolder(
				LayoutInflater.from(context).inflate(R.layout.zhainan_item, parent, false));
		return holder;
	}

}
