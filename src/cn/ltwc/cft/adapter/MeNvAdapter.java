package cn.ltwc.cft.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import cn.ltwc.cft.R;
import cn.ltwc.cft.beans.TiangouBean;
import cn.ltwc.cft.myinterface.MeNvItemImgClickListener;
import cn.ltwc.cft.rvholder.MeiNvHolder;
import cn.ltwc.cft.utils.GlideListener;

import com.bumptech.glide.Glide;

public class MeNvAdapter extends RecyclerView.Adapter<MeiNvHolder> {
	private String SRC = "http://tnfs.tngou.net/image";// 图片地址共有部分
	private Context context;
	private ArrayList<TiangouBean> al;
	private MeNvItemImgClickListener listener;

	public MeNvAdapter(Context context, ArrayList<TiangouBean> al,
			MeNvItemImgClickListener listener) {
		this.al = al;
		this.context = context;
		this.listener = listener;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return al.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onBindViewHolder(MeiNvHolder holder, int position) {
		// TODO Auto-generated method stub
		holder.title.setText(al.get(position).getTitle());// 标题
		Glide.with(context).load(SRC + al.get(position).getImg()).asBitmap()
				.placeholder(R.drawable.mian_color_perload)
				.listener(new GlideListener(holder.Img, GlideListener.SETALL))
				.error(R.drawable.load_failed2).into(holder.Img);
		holder.ImgClick(listener, position);
	}

	@Override
	public MeiNvHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		// TODO Auto-generated method stub
		MeiNvHolder holder = new MeiNvHolder(LayoutInflater.from(context)
				.inflate(R.layout.zhainan_item, parent, false));
		return holder;
	}

}
