package cn.ltwc.cft.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import cn.ltwc.cft.R;
import cn.ltwc.cft.adapter.HistoryOnToadyJUHEDeatilAdapter;
import cn.ltwc.cft.beans.HistoryOnTodayBeanJUHE;
import cn.ltwc.cft.beans.HistoryOnTodayImgBean;
import cn.ltwc.cft.data.Constant;
import cn.ltwc.cft.http.HttpFactory;
import cn.ltwc.cft.http.ServiceResponce;
import cn.ltwc.cft.myinterface.ItemClickListener;
import cn.ltwc.cft.myinterface.SaveImgListener;
import cn.ltwc.cft.utils.BitMapUtil;
import cn.ltwc.cft.utils.FileUtils;
import cn.ltwc.cft.utils.HLUtil;
import cn.ltwc.cft.view.TitleView;

public class HistoryDetailJUHEActivity extends BaseActivity
		implements ServiceResponce, SaveImgListener, ItemClickListener {
	private TitleView title;
	private HistoryOnTodayBeanJUHE bean;
	private List<HistoryOnTodayImgBean> imgUrl;
	public static HistoryDetailJUHEActivity instance;
	private String c = "";// 内容
	private String cachPath;
	private int num = 0;
	private RecyclerView rv;
	private HistoryOnToadyJUHEDeatilAdapter a;

	public HistoryDetailJUHEActivity() {
		super(R.layout.activity_juhe_history_detail);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		instance = this;
		title = (TitleView) findViewById(R.id.title);
		title.setTitletext("历史上的今天");
		title.setRightText("分享");
		title.setRightBtnTextVisibility(View.VISIBLE);
		rv = (RecyclerView) findViewById(R.id.rv);
		LinearLayoutManager manager = new LinearLayoutManager(instance);
		manager.setOrientation(LinearLayoutManager.VERTICAL);
		rv.setLayoutManager(manager);

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		bean = (HistoryOnTodayBeanJUHE) getIntent().getSerializableExtra("bean");
		imgUrl = new ArrayList<HistoryOnTodayImgBean>();
		a = new HistoryOnToadyJUHEDeatilAdapter(this, bean, imgUrl, c);
		showWaitingDialog(this);
		HttpFactory.HistoryJUHEDetail(this, bean.getE_id());
	}

	@Override
	public void bindView() {
		// TODO Auto-generated method stub
		rv.setAdapter(a);
		title.getRightText().setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// createMenu(v);
				HLUtil.toMyShare(HistoryDetailJUHEActivity.this, Constant.SHARE_TYPE_TEXT,
						"\t\t\t\t\t\t\t王朝黄历\n\t\t\t\t\t历史上的今天\n" + c, null);
			}
		});
		a.setListener(this);
	}

	@SuppressLint("InflateParams")
	@SuppressWarnings("deprecation")
	protected void createMenu(View v) {
		// TODO Auto-generated method stub
		View popview = LayoutInflater.from(this).inflate(R.layout.pop_share_menu, null);
		final PopupWindow pop = new PopupWindow(popview, ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, false);
		// 需要设置一下此参数，点击外边可消失
		pop.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击窗口外边窗口消失
		pop.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		pop.setFocusable(true);
		pop.showAsDropDown(v, 0, 0);
		popview.findViewById(R.id.linear_m_one).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HLUtil.toMyShare(HistoryDetailJUHEActivity.this, Constant.SHARE_TYPE_TEXT,
						"\t\t\t\t\t\t\t王朝黄历\n\t\t\t\t\t历史上的今天\n" + c, null);
				pop.dismiss();
			}
		});

		popview.findViewById(R.id.linear_m_two).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (num != 0) {
					if (FileUtils.isExit(cachPath)) {
						num++;
						HLUtil.toMyShare(HistoryDetailJUHEActivity.this, Constant.SHARE_TYPE_IMG,
								"\t\t\t\t\t\t\t王朝黄历\n\t\t\t\t\t历史上的今天\n" + c, cachPath);
					} else {
						num = 0;
					}

				}
				if (num == 0) {
					cachPath = FileUtils.buildCache(instance) + "王朝黄历.jpg";
					showWaitingDialog(instance);
					final Bitmap bit = BitMapUtil.view2Bitmap(rv);
					new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								BitMapUtil.saveImg(bit, cachPath, instance);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								HistoryDetailJUHEActivity.instance.show("获取资源失败");
								return;
							}
						}
					}).start();

				}
				pop.dismiss();

			}
		});

	}

	@Override
	public void httpSuccess(String result, int responseFlag) {
		// TODO Auto-generated method stub
		try {
			JSONObject object = new JSONObject(result);
			JSONArray array = object.optJSONArray("result");
			hideWaitingDialog();
			imgUrl.clear();
			if (array != null) {
				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = array.optJSONObject(i);
					c = obj.optString("content");
					JSONArray a = obj.optJSONArray("picUrl");
					for (int j = 0; j < a.length(); j++) {
						JSONObject o = a.optJSONObject(j);
						String pic_title = o.optString("pic_title");
						String url = o.optString("url");
						HistoryOnTodayImgBean bean = new HistoryOnTodayImgBean(pic_title, url);
						imgUrl.add(bean);
					}
				}
				a.setCon(c);
				a.notifyDataSetChanged();
			} else {
				hideWaitingDialog();
				// 提示用户
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void httpTimeOut(int responseFlag) {
		// TODO Auto-generated method stub
		hideWaitingDialog();
		// 提示用户
	}

	@Override
	public void httpError(int responseFlag) {
		// TODO Auto-generated method stub
		hideWaitingDialog();
		// 提示用户
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// adpter.notifyDataSetChanged();
	}

	@Override
	public void saveSuccess() {
		hideWaitingDialog();
		num++;
		HLUtil.toMyShare(HistoryDetailJUHEActivity.this, Constant.SHARE_TYPE_IMG,
				"\t\t\t\t\t\t\t王朝黄历\n\t\t\t\t\t历史上的今天\n" + c, cachPath);
	}

	@Override
	public void ItemClick(View v) {
		// TODO Auto-generated method stub
		cachPath = FileUtils.buildCache(instance) + "王朝黄历.jpg";
		showWaitingDialog(instance);
		final Bitmap bit = BitMapUtil.view2Bitmap(v);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					BitMapUtil.saveImg(bit, cachPath, instance);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					HistoryDetailJUHEActivity.instance.show("获取资源失败");
					return;
				}
			}
		}).start();
	}

}
