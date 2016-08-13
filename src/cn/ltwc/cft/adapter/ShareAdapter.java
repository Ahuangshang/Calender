package cn.ltwc.cft.adapter;

import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import cn.ltwc.cft.R;
import cn.ltwc.cft.myinterface.MeNvItemImgClickListener;
import cn.ltwc.cft.rvholder.ShareHolder;

public class ShareAdapter extends RecyclerView.Adapter<ShareHolder> {
	private Context c;
	private List<ResolveInfo> list;
	private  MeNvItemImgClickListener listener;
	public ShareAdapter(Context c, List<ResolveInfo> list,MeNvItemImgClickListener listener) {
		super();
		this.c = c;
		this.list = list;
		this.listener=listener;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public void onBindViewHolder(ShareHolder holder, int position) {
		// TODO Auto-generated method stub
		PackageManager packageManager = c.getPackageManager();  
		ResolveInfo info = list.get(position);
		
		holder.icon.setImageDrawable(info.loadIcon(packageManager));
		holder.appName.setText(info.loadLabel(packageManager).toString());
		holder.itemClick(position,listener);

	}

	@Override
	public ShareHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// TODO Auto-generated method stub
		ShareHolder holder = new ShareHolder(LayoutInflater.from(c).inflate(
				R.layout.item_share_app, parent, false));
		return holder;
	}

}
