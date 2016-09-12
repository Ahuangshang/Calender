package cn.ltwc.cft.rvholder;

import android.content.pm.ResolveInfo;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import cn.ltwc.cft.R;
import cn.ltwc.cft.myinterface.PagerRecyclerItemClickListener;

public class ShareHolder extends RecyclerView.ViewHolder {
	public ImageView icon;
	public TextView appName;
	public View item;

	public ShareHolder(View itemView) {
		super(itemView);
		// TODO Auto-generated constructor stub
		icon = (ImageView) itemView.findViewById(R.id.icon);
		appName = (TextView) itemView.findViewById(R.id.app_name);
		item = itemView.findViewById(R.id.item);
	}

	public void itemClick(final ResolveInfo info, final PagerRecyclerItemClickListener listener) {
		item.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.itemClicked(info);
				}

			}
		});
	}
}
