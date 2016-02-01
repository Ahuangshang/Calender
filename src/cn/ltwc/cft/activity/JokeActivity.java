package cn.ltwc.cft.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import cn.ltwc.cft.R;
import cn.ltwc.cft.adapter.JokeAdapter;
import cn.ltwc.cft.beans.JokeListBean;
import cn.ltwc.cft.beans.ResBean;
import cn.ltwc.cft.http.HttpFactory;
import cn.ltwc.cft.http.ServiceResponce;
import cn.ltwc.cft.view.TitleView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * TODO:内涵段子的Activity
 * 
 * @author huangshang 2015-11-15 下午10:46:42
 * @Modified_By:
 */
public class JokeActivity extends BaseActivity implements ServiceResponce {
	private TitleView title;
	private ListView listJoke;
	private String TAG = "JokeActivity";
	private List<JokeListBean> jokeList;//笑话的集合
	private JokeAdapter jokeADApter;//适配器
	public JokeActivity() {
		super(R.layout.activity_joke);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		title = (TitleView) findViewById(R.id.joke_title);
		title.setTitletext("内涵段子");
		title.setRightVisibility(View.GONE);
		listJoke = (ListView) findViewById(R.id.joke_list);
		
	}

	@Override
	public void initData() {
		jokeList=new ArrayList<JokeListBean>();
		Random random=new Random();
		int d=random.nextInt(300);
		// TODO Auto-generated method stub
		HttpFactory.Joke(this, d);
		Log.i(TAG, jokeList.toString());
	}

	@Override
	public void bindView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void httpSuccess(String result, int responseFlag) {
		// TODO Auto-generated method stub
		//Log.i(TAG, result);
		try {
			JSONObject jsonObject=new JSONObject(result);
			JSONObject jsonObject2=jsonObject.getJSONObject("showapi_res_body");
			JSONArray array=jsonObject2.getJSONArray("contentlist");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject3=array.getJSONObject(i);
				String text=jsonObject3.getString("text");
				String title=jsonObject3.getString("title");
				String ct=jsonObject3.getString("ct");
				String type=jsonObject3.getString("type");
				JokeListBean bean=new JokeListBean(ct, title, text, type);
				jokeList.add(bean);
			}
			//	Log.e(TAG, jokeList.toString()+1232144);
			
			//jokeADApter=new JokeAdapter(c, jokeList);
			
			jokeADApter=new JokeAdapter(c, jokeList);
			listJoke.setAdapter(jokeADApter);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Gson gosn = new Gson();
//
//		ResBean resbean = gosn.fromJson(result, new TypeToken<ResBean>() {
//		}.getType());
//
//		jokeList.addAll(resbean.getRes_body().getJokeList());
//		jokeADApter.notifyDataSetChanged();
//		Log.i(TAG, jokeList.get(0).getJokeTitle());
//		Log.i(TAG, jokeList.get(0).getJokeContent());
	}

	@Override
	public void httpTimeOut(int responseFlag) {
		// TODO Auto-generated method stub
		Log.i(TAG, "连接超时");
	}

	@Override
	public void httpError(int responseFlag) {
		// TODO Auto-generated method stub
		Log.i(TAG, "错误异常");
	}

}
