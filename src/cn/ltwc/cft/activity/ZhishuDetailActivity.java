package cn.ltwc.cft.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import cn.ltwc.cft.R;
import cn.ltwc.cft.adapter.ZhishuDetailAdapter;
import cn.ltwc.cft.beans.ZhishuDetailBean;
import cn.ltwc.cft.data.Constant;
import cn.ltwc.cft.http.HttpFactory;
import cn.ltwc.cft.http.ServiceResponce;
import cn.ltwc.cft.utils.BitMapUtil;
import cn.ltwc.cft.view.TitleView;

import com.bumptech.glide.Glide;

public class ZhishuDetailActivity extends BaseActivity implements
		ServiceResponce {
	private TitleView titleView;
	private ListView list;
	private String channelId;
	private String headPic;
	private String title;
	private View headView;
	private ImageView headImg;

	public ZhishuDetailActivity() {
		super(R.layout.activity_zhishu_detail);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		titleView = (TitleView) findViewById(R.id.zhishu_title);
		list = (ListView) findViewById(R.id.list);
		headView = LayoutInflater.from(this).inflate(
				R.layout.zhishu_detail_head, list, false);
		headImg = (ImageView) headView.findViewById(R.id.head_pic);
		titleView.setTitleAlpha(0);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		channelId = getIntent().getStringExtra("channelId");
		headPic = getIntent().getStringExtra("headPic");
		title = getIntent().getStringExtra("indexType");
	}

	@Override
	public void bindView() {
		// TODO Auto-generated method stub
		titleView.setTitletext(title);
		Glide.with(this).load(headPic).into(headImg);
		list.addHeaderView(headView);
		showWaitingDialog(this);
		HttpFactory.loadZhishuDetail(this, channelId);

		list.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				int location[] = new int[2];
				headView.getLocationOnScreen(location);
				float h = location[1];
				float top = 0;
				if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
					top = BitMapUtil.dip2px(c, 70);
				} else {
					top = BitMapUtil.dip2px(c, 50);
				}
				float y = BitMapUtil.dip2px(c, 180);
				if (y + h >= 2 * top) {
					titleView.setTitleAlpha(0);
				} else {
					titleView.setTitleAlpha(1 - ((y + h - top) / top));
				}
			}
		});
	}

	@Override
	public void httpSuccess(String result, int responseFlag) {
		hideWaitingDialog();
		// TODO Auto-generated method stub
		try {
			JSONObject object = new JSONObject(result);
			setView(object);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void httpTimeOut(int responseFlag) {
		// TODO Auto-generated method stub
		hideWaitingDialog();
		
	}

	@Override
	public void httpError(int responseFlag) {
		// TODO Auto-generated method stub
		hideWaitingDialog();
	}

	private void setView(JSONObject object) {
		// TODO Auto-generated method stub
		JSONArray array = object.optJSONArray("data");
		final List<ZhishuDetailBean> datas = new ArrayList<ZhishuDetailBean>();
		if (array != null) {
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.optJSONObject(i);
				JSONArray list = obj.optJSONArray("list");
				if (list != null) {
					for (int j = 0; j < list.length(); j++) {
						JSONObject o = list.optJSONObject(j);
						JSONObject data = o.optJSONObject("data");
						String title = data.optString("title");
						String url = data.optString("url");
						String summary = data.optString("summary");
						String source = data.optString("source");
						String image = data.optString("images")
								.replace("[", "").replace("]", "");
						String[] temp = image.split(",");
						if (temp != null) {
							String[] images = new String[temp.length];
							for (int k = 0; k < temp.length; k++) {
								images[k] = temp[k].replaceAll("\"", "")
										.replaceAll("\\\\", "");
							}
							String imageNum = data.optString("imageNum");
							ZhishuDetailBean bean = new ZhishuDetailBean(title,
									url, summary, source, images, imageNum);
							datas.add(bean);
						}

					}
				}
			}
		}
		ZhishuDetailAdapter adapter = new ZhishuDetailAdapter(this, datas);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position > 0) {
					Intent intent = new Intent(ZhishuDetailActivity.this,
							MyX5WebView.class);
					intent.putExtra(Constant.WEBURL, datas.get(position - 1)
							.getUrl());
					startActivity(intent);
				}

			}
		});

	}
}
