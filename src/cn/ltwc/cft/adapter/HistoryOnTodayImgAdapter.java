package cn.ltwc.cft.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.ltwc.cft.MyApplication;
import cn.ltwc.cft.R;
import cn.ltwc.cft.beans.HistoryOnTodayImgBean;

public class HistoryOnTodayImgAdapter extends BaseAdapter {
	private Context context;
	private List<HistoryOnTodayImgBean> imgUrl;
	// ImageLoader相关
	ImageLoaderConfiguration imageLoaderConfiguration;
	DisplayImageOptions displayImageOptions;
	ImageLoader imageLoader;// 图像加载器

	public HistoryOnTodayImgAdapter(Context context,
			List<HistoryOnTodayImgBean> imgUrl) {
		this.context = context;
		this.imgUrl = imgUrl;
		// 1.配制ImageLoaderConfiguration(主要是配制图片的缓存【是否缓存到内存卡和内存】)
		imageLoaderConfiguration = MyApplication.getInstance()
				.getImageLoaderConfiguration();
		// 2.配制DisplayImageOptions(主要是配制图片在显示过程中的一些参数【图片加载失败，图片地址不存在所显示的默认图片】)
		initDisplayImageOptions();
		// 3.按照参数配制去显示图片(ImageLoader)
		imageLoader = ImageLoader.getInstance();// 获取ImageLoader的对象
		imageLoader.init(imageLoaderConfiguration);// 配制ImageLoaderConfiguration参数
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
		holder.title.setVisibility(TextUtils.isEmpty(t) ? View.GONE
				: View.VISIBLE);
		// 图像加载器.显示图片(图片地址,imageView,显示参数【DisplayImageOptions】)
		imageLoader.displayImage(imgUrl.get(position).getImgUrl(), holder.img,
				displayImageOptions);
		return convertView;
	}

	class viewHolder {
		TextView title;
		ImageView img;
	}

	/**
	 * 2.配制DisplayImageOptions(主要是配制图片在显示过程中的一些参数【图片加载失败，图片地址不存在所显示的默认图片】)
	 */
	private void initDisplayImageOptions() {
		displayImageOptions = new DisplayImageOptions.Builder()
		// .showImageOnLoading(R.drawable.img_loading_default_big) //
		// 设置图片在下载期间显示的图片
		// .showImageForEmptyUri(R.drawable.img_loading_empty_big)//
		// 设置图片Uri为空或是错误的时候显示的图片
		// .showImageOnFail(R.drawable.img_loading_fail_big) //
		// 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片的缩放类型(目标大小)
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
				.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				.displayer(new RoundedBitmapDisplayer(20))// 是否设置为圆角，弧度为多少
				.displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
				.build();// 构建完成
	}
}
