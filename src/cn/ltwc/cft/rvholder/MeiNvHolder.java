package cn.ltwc.cft.rvholder;

import cn.ltwc.cft.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MeiNvHolder  extends RecyclerView.ViewHolder {
	public TextView title;
	public ImageView Img;
	public MeiNvHolder(View itemView) {
		super(itemView);
		// TODO Auto-generated constructor stub
		title=(TextView) itemView.findViewById(R.id.zainan_title);
		Img=(ImageView) itemView.findViewById(R.id.zainan_img);
	}

}
