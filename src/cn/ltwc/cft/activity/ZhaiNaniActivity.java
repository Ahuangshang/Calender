package cn.ltwc.cft.activity;

import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import cn.ltwc.cft.R;
import cn.ltwc.cft.adapter.MeiNvAdapter;
import cn.ltwc.cft.beans.MeiNvIconBean;
import cn.ltwc.cft.beans.TiangouBean;
import cn.ltwc.cft.http.HttpFactory;
import cn.ltwc.cft.http.ServiceResponce;
import cn.ltwc.cft.view.TitleView;

/**
 * ] TODO:宅男天堂的Activity
 * 
 * @author huangshang
 * @Modified_By:
 */
public class ZhaiNaniActivity extends BaseActivity implements ServiceResponce {

	private String TAG = "ZhaiNaniActivity";
	private ListView listView;//
	private ArrayList<TiangouBean> al = null;// 存放数据的集合
	private TitleView titleView;
	private View emptyView;

	public ZhaiNaniActivity() {
		super(R.layout.activity_zhainan);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		listView = (ListView) findViewById(R.id.zaina_listview);
		titleView = (TitleView) findViewById(R.id.zhainan_title);
		titleView.setLeftIcon(R.drawable.title_back);
		titleView.setRightVisibility(View.GONE);

		titleView.setTitletext("宅男天堂");

		emptyView = findViewById(R.id.note_emptyview);
		listView.setEmptyView(emptyView);
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

		HttpFactory.meinvTPatiangou(this, s, 50, d, 1);
		titleView.getLeftIcon().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

	}

	/**
	 * 成功时
	 */
	public void httpSuccess(String result, int responseFlag) {
		al = new ArrayList<TiangouBean>();
		Log.e(TAG, "1231546");
		try {
			Log.e(TAG, "********" + result);
			JSONObject jsonObject = new JSONObject(result);
			JSONArray JSONArray = jsonObject.getJSONArray("tngou");
			for (int i = 0; i < JSONArray.length(); i++) {
				JSONObject jsonObject2 = JSONArray.getJSONObject(i);
				String title = jsonObject2.getString("title");

				String img = jsonObject2.getString("img");
				TiangouBean bean = new TiangouBean(img, title);
				al.add(bean);

			}

			// int len = jsonObject.length();
			// JSONObject jsonObject2 = null;
			// for (int i = 0; i < len; i++) {
			// try {
			//
			// jsonObject2 = jsonObject.getJSONObject(i + "");
			// } catch (Exception e) {
			// Log.i(TAG, "json键值对解析异常");
			// continue;
			// }
			// if (jsonObject2 == null) {
			// continue;
			//
			// }
			// String title = jsonObject2.getString("title");
			// String img = jsonObject2.getString("picUrl");
			// TiangouBean bean = new TiangouBean(title, img);
			// al.add(bean);
			// }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(TAG, "新闻推荐json格式error");

			// getAttachAct().showMessage(getString(R.string.server_error));
		}

		MeiNvAdapter Adapter = new MeiNvAdapter(c, al);
		listView.setAdapter(Adapter);
	}

	/**
	 * 连接超时
	 */
	@Override
	public void httpTimeOut(int responseFlag) {
		// TODO Auto-generated method stub

	}

	/**
	 * 服务器应答
	 */
	public void httpError(int responseFlag) {
		// TODO Auto-generated method stub

	}

}
