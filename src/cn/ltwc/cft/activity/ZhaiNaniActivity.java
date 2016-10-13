package cn.ltwc.cft.activity;

import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import cn.ltwc.cft.R;
import cn.ltwc.cft.adapter.MeNvAdapter;
import cn.ltwc.cft.beans.TiangouBean;
import cn.ltwc.cft.data.Constant;
import cn.ltwc.cft.http.HttpFactory;
import cn.ltwc.cft.http.ServiceResponce;
import cn.ltwc.cft.myinterface.MeNvItemImgClickListener;
import cn.ltwc.cft.view.SpacesItemDecoration;
import cn.ltwc.cft.view.TitleView;

/**
 * ] TODO:宅男天堂的Activity
 * 
 * @author huangshang
 * @Modified_By:
 */
public class ZhaiNaniActivity extends BaseActivity implements ServiceResponce,
		MeNvItemImgClickListener {

	private String TAG = "ZhaiNaniActivity";
	private ArrayList<TiangouBean> al = null;// 存放数据的集合
	private TitleView titleView;

	private RecyclerView rv;

	public ZhaiNaniActivity() {
		super(R.layout.activity_zhainan);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		// listView = (ListView) findViewById(R.id.zaina_listview);
		titleView = (TitleView) findViewById(R.id.zhainan_title);
		titleView.setLeftIcon(R.drawable.title_back);
		titleView.setRightVisibility(View.GONE);

		titleView.setTitletext("宅男天堂");

		// emptyView = findViewById(R.id.note_emptyview);
		// listView.setEmptyView(emptyView);
		rv = (RecyclerView) findViewById(R.id.rv);
		// LinearLayoutManager manager = new LinearLayoutManager(this);
		// manager.setOrientation(LinearLayoutManager.VERTICAL);
		// rv.setLayoutManager(manager);
		// 设置为瀑布流
		rv.setLayoutManager(new StaggeredGridLayoutManager(2,
				StaggeredGridLayoutManager.VERTICAL));
		// 设置item之间的间隔
		SpacesItemDecoration decoration = new SpacesItemDecoration(12);
		rv.addItemDecoration(decoration);
	}

	@Override
	public void initData() {

		// TODO Auto-generated method stub

	}

	@Override
	public void bindView() {
		Random random = new Random();
		int s = random.nextInt(10);
		int d = random.nextInt(5);
		showWaitingDialog(this);
		HttpFactory.meinvTPatiangou(this, s, 50, d, 1);

	}

	/**
	 * 成功时
	 */
	public void httpSuccess(String result, int responseFlag) {
		hideWaitingDialog();
		al = new ArrayList<TiangouBean>();
		Log.e(TAG, result);
		try {
			JSONObject jsonObject = new JSONObject(result);
			JSONArray JSONArray = jsonObject.getJSONArray("tngou");
			for (int i = 0; i < JSONArray.length(); i++) {
				JSONObject jsonObject2 = JSONArray.getJSONObject(i);
				String title = jsonObject2.getString("title");
				String img = jsonObject2.getString("img");
				TiangouBean bean = new TiangouBean(img, title);
				al.add(bean);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MeNvAdapter adapter = new MeNvAdapter(c, al, this);

		rv.setAdapter(adapter);
	}

	/**
	 * 连接超时
	 */
	@Override
	public void httpTimeOut(int responseFlag) {
		// TODO Auto-generated method stub
		hideWaitingDialog();
	}

	/**
	 * 服务器应答
	 */
	public void httpError(int responseFlag) {
		// TODO Auto-generated method stub
		hideWaitingDialog();
	}

	@Override
	public void onClick(int position) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(ZhaiNaniActivity.this,
				ShowImageGallery.class);
		intent.putParcelableArrayListExtra(Constant.IMGURL_LIST, al);
		intent.putExtra(Constant.POSITION, position);
		startActivity(intent);
	}

}
