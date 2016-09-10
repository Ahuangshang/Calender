package cn.ltwc.cft.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ResolveInfo;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.ltwc.cft.R;
import cn.ltwc.cft.adapter.ShareAdapter;
import cn.ltwc.cft.myinterface.PagerRecyclerItemClickListener;

/**
 * 
 * TODO: 数据适配器
 * 
 * @author huangshang 2016-9-11 上午5:11:23
 * @Modified_By:
 */
@SuppressLint("InflateParams")
public class PagerRecyclerAdapter extends PagerAdapter {
	private List<ResolveInfo> list;
	private int spanRow, spanColumn;// 每页的行数和列数
	private int num;// 每页显示的item的数量
	private Context c;
	private PagerRecyclerItemClickListener listener;
	private View itemView;

	public PagerRecyclerAdapter(Context c, List<ResolveInfo> list, int spanRow,
			int spanColumn, PagerRecyclerItemClickListener listener) {
		super();
		this.c = c;
		this.list = list;
		this.spanRow = spanRow;
		this.spanColumn = spanColumn;
		this.num = spanRow * spanColumn;
		this.listener = listener;
	}

	@Override
	public int getCount() {
		if (list.size() % num == 0) {
			return list.size() / num;
		} else {
			return list.size() / num + 1;
		}
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {

		((ViewPager) container).removeView(itemView);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		List<ResolveInfo> temp = new ArrayList<ResolveInfo>();
		for (int i = position * num; i < num * (position + 1); i++) {
			if (i < list.size()) {
				temp.add(list.get(i));
			}
		}
		itemView = LayoutInflater.from(c).inflate(R.layout.item_pager_recycler,
				null);
		RecyclerView rv = (RecyclerView) itemView.findViewById(R.id.rv);

		rv.setLayoutManager(new StaggeredGridLayoutManager(spanRow,
				StaggeredGridLayoutManager.HORIZONTAL));
		List<ResolveInfo> temp2 = new ArrayList<ResolveInfo>();
		for (int i = 0; i < num; i++) {
			temp2.add(null);
		}
		if (temp.size() < num) {
			for (int j = 0; j < spanColumn; j++) {
				int count = 0;
				for (int i = j; i < temp2.size(); i += spanRow) {
					if (i + j * (spanColumn) - count < temp.size()) {
						temp2.set(i, temp.get(i + j * (spanColumn) - count));
						count++;
					} else {
						break;
					}

				}
			}
			temp.clear();
			temp.addAll(temp2);
		}
		ShareAdapter adapter = new ShareAdapter(c, temp, listener);
		rv.setAdapter(adapter);
		((ViewPager) container).addView(itemView);
		return itemView;
	}

}
