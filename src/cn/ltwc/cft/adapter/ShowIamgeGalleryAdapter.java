package cn.ltwc.cft.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import cn.ltwc.cft.R;
import cn.ltwc.cft.beans.TiangouBean;
import cn.ltwc.cft.gallery.PhotoView;

import com.bumptech.glide.Glide;

public class ShowIamgeGalleryAdapter extends PagerAdapter {
	private RelativeLayout root;
	private Context context;
	private List<TiangouBean> imgList;
	private String SRC = "http://tnfs.tngou.net/image";// 图片地址共有部分

	public ShowIamgeGalleryAdapter(RelativeLayout root, Context context, List<TiangouBean> imgList) {
		super();
		this.root = root;
		this.context = context;
		this.imgList = imgList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imgList.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		return view == object;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		PhotoView photoView = new PhotoView(container.getContext(), root);

		Glide.with(context).load(SRC + imgList.get(position).getImg()).asBitmap().placeholder(R.drawable.pre_load)
				.error(R.drawable.loading_failed).into(photoView);
		container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		return photoView;
	}
}
