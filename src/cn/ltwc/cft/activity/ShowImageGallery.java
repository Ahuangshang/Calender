package cn.ltwc.cft.activity;

import cn.ltwc.cft.gallery.HackyViewPager;

import java.util.List;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.RelativeLayout;
import cn.ltwc.cft.R;
import cn.ltwc.cft.adapter.ShowIamgeGalleryAdapter;
import cn.ltwc.cft.beans.TiangouBean;
import cn.ltwc.cft.data.Constant;

/**
 * 显示大图片（cn.ltwc.cft.gallery包下面就是显示该效果的文件）
 * 
 * @author LZL
 *
 */
public class ShowImageGallery extends BaseActivity implements OnPageChangeListener {
	private RelativeLayout root;
	private HackyViewPager pager;
	private List<TiangouBean> imgList;// 图片信息的集合
	private int position;// 当前点击进来的是第几张
	private ShowIamgeGalleryAdapter adapter;
	public static ShowImageGallery instance;

	public ShowImageGallery() {
		super(R.layout.activity_show_image_gallery);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		root = (RelativeLayout) findViewById(R.id.img_root);
		pager = (HackyViewPager) findViewById(R.id.img_pager);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		imgList = getIntent().getParcelableArrayListExtra(Constant.IMGURL_LIST);
		position = getIntent().getIntExtra(Constant.POSITION, 0);
		instance = this;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void bindView() {
		// TODO Auto-generated method stub
		pager.setPageMargin(12);
		adapter = new ShowIamgeGalleryAdapter(root, this, imgList);
		pager.setAdapter(adapter);
		pager.setOnPageChangeListener(this);
		pager.setCurrentItem(position);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		position = pager.getCurrentItem();
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		instance = null;
	}
}
