package cn.ltwc.cft.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.ltwc.cft.MyApplication;
import cn.ltwc.cft.R;
import cn.ltwc.cft.beans.HistoryOnTodayBeanJUHE;
import cn.ltwc.cft.beans.HistoryOnTodayImgBean;
import cn.ltwc.cft.myinterface.ItemClickListener;

public class HistoryOnToadyJUHEDeatilAdapter extends RecyclerView.Adapter<ViewHolder> {
	private final int LAYOUT_ONE = 1;
	private final int LAYOUT_TWO = 2;
	private final int LAYOUT_THREE = 3;
	private Context c;
	private HistoryOnTodayBeanJUHE bean;
	private List<HistoryOnTodayImgBean> imgUrl;// 图片地址的集合
	private String con = "";// 内容
	// ImageLoader相关
	private ImageLoaderConfiguration imageLoaderConfiguration;
	private DisplayImageOptions displayImageOptions;
	private ImageLoader imageLoader;// 图像加载器
	private ItemClickListener listener;

	public HistoryOnToadyJUHEDeatilAdapter(Context c, HistoryOnTodayBeanJUHE bean, List<HistoryOnTodayImgBean> imgUrl,
			String con) {
		super();
		this.c = c;
		this.bean = bean;
		this.imgUrl = imgUrl;
		this.con = con;
		// 1.配制ImageLoaderConfiguration(主要是配制图片的缓存【是否缓存到内存卡和内存】)
		imageLoaderConfiguration = MyApplication.getInstance().getImageLoaderConfiguration();
		// 2.配制DisplayImageOptions(主要是配制图片在显示过程中的一些参数【图片加载失败，图片地址不存在所显示的默认图片】)
		initDisplayImageOptions();
		// 3.按照参数配制去显示图片(ImageLoader)
		imageLoader = ImageLoader.getInstance();// 获取ImageLoader的对象
		imageLoader.init(imageLoaderConfiguration);// 配制ImageLoaderConfiguration参数
	}

	public void setCon(String con) {
		this.con = con;
	}

	public void setListener(ItemClickListener listener) {
		this.listener = listener;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return imgUrl.size() + 2;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		// TODO Auto-generated method stub
		int viewType = getItemViewType(position);
		switch (viewType) {
		case LAYOUT_ONE:
			TitleHolder tHolder = (TitleHolder) holder;
			tHolder.setUI();
			break;
		case LAYOUT_TWO:
			ImageHolder imgHolder = (ImageHolder) holder;
			imgHolder.setUI(position - 1);
			break;
		case LAYOUT_THREE:
			ContentHolder cHolder = (ContentHolder) holder;
			cHolder.setUI();
			break;
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// TODO Auto-generated method stub
		switch (viewType) {
		case LAYOUT_ONE:
			TitleHolder tHolder = new TitleHolder(
					LayoutInflater.from(c).inflate(R.layout.item_history_on_today_title, parent, false));
			return tHolder;
		case LAYOUT_TWO:
			ImageHolder imgHolder = new ImageHolder(
					LayoutInflater.from(c).inflate(R.layout.item_history_on_today_img, parent, false));
			return imgHolder;
		case LAYOUT_THREE:
			ContentHolder cHolder = new ContentHolder(
					LayoutInflater.from(c).inflate(R.layout.item_history_on_today_content, parent, false));
			return cHolder;
		}
		return null;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return LAYOUT_ONE;
		} else if (position == getItemCount() - 1) {
			return LAYOUT_THREE;
		} else {
			return LAYOUT_TWO;
		}
	}

	public class ImageHolder extends RecyclerView.ViewHolder {
		ImageView img;
		TextView imgName;
		View item;

		public ImageHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
			img = (ImageView) itemView.findViewById(R.id.img_show);
			imgName = (TextView) itemView.findViewById(R.id.img_title);
			item = itemView.findViewById(R.id.item);
		}

		public void setUI(int i) {
			// TODO Auto-generated method stub
			HistoryOnTodayImgBean imgBean = imgUrl.get(i);
			imgName.setText(imgBean.getImgTitle());
			// 图像加载器.显示图片(图片地址,imageView,显示参数【DisplayImageOptions】)
			imageLoader.displayImage(imgBean.getImgUrl(), img, displayImageOptions);
			imgName.setVisibility(TextUtils.isEmpty(imgBean.getImgTitle()) ? View.GONE : View.VISIBLE);
			item.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					if (listener != null) {
						listener.ItemClick(v);
					}
					return false;
				}
			});
		}

	}

	public class TitleHolder extends RecyclerView.ViewHolder {
		TextView year, title;

		public TitleHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
			year = (TextView) itemView.findViewById(R.id.juhe_history_year);
			title = (TextView) itemView.findViewById(R.id.juhe_history_title);
		}

		public void setUI() {
			// TODO Auto-generated method stub
			year.setText(bean.getYear());
			title.setText(bean.getTitle());
		}

	}

	public class ContentHolder extends RecyclerView.ViewHolder {
		TextView content;

		public ContentHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
			content = (TextView) itemView.findViewById(R.id.juhe_history_event);
		}

		public void setUI() {
			// TODO Auto-generated method stub
			content.setText(con);
		}

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
