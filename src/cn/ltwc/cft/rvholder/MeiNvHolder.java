package cn.ltwc.cft.rvholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import cn.ltwc.cft.R;
import cn.ltwc.cft.myinterface.MeNvItemImgClickListener;

public class MeiNvHolder extends RecyclerView.ViewHolder {
	public TextView title;
	public ImageView Img;
	private MeNvItemImgClickListener listener;

	public MeiNvHolder(View itemView) {
		super(itemView);
		// TODO Auto-generated constructor stub
		title = (TextView) itemView.findViewById(R.id.zainan_title);
		Img = (ImageView) itemView.findViewById(R.id.zainan_img);
	}

	public void ImgClick(MeNvItemImgClickListener listene, final int position) {
		this.listener = listene;
		Img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (listener != null) {
					listener.onClick(position);
				}
			}
		});
	}
}
