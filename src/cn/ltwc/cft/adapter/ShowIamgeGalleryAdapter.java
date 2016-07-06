package cn.ltwc.cft.adapter;

import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import cn.ltwc.cft.R;
import cn.ltwc.cft.activity.ShowImageGallery;
import cn.ltwc.cft.beans.TiangouBean;
import cn.ltwc.cft.gallery.PhotoView;
import cn.ltwc.cft.utils.GlideListener;

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
		if (ShowImageGallery.instance != null) {
			ShowImageGallery.instance.showWaitingDialog(context);
		}
		Glide.with(context).load(SRC + imgList.get(position).getImg()).centerCrop()
				.error(R.drawable.load_failed)
				.into(new GlideDrawableImageViewTarget(photoView) {
					@Override
					public void onResourceReady(GlideDrawable arg0, GlideAnimation<? super GlideDrawable> arg1) {
						// TODO Auto-generated method stub
						super.onResourceReady(arg0, arg1);
						if (ShowImageGallery.instance != null) {
							ShowImageGallery.instance.hideWaitingDialog();
						}
					}
				});
		container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		return photoView;
	}
}
