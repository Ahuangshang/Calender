package cn.ltwc.cft.activity;

import java.net.URLDecoder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.ltwc.cft.R;
import cn.ltwc.cft.http.HttpFactory;
import cn.ltwc.cft.http.ServiceResponce;
import cn.ltwc.cft.view.TitleView;

/**
 * 
 * TODO:手机号码归属地查询的Activity
 * 
 * @author huangshang 2015-11-15 下午10:48:25
 * @Modified_By:
 */
public class PhoneActivity extends BaseActivity implements ServiceResponce {
	@SuppressWarnings("unused")
	private TextView phonetext, citytext, provincetext, suppliertext, suittext, retMsgtext;
	private EditText getphone;
	private LinearLayout linearLayout;
	private RelativeLayout relativeLayout;
	private Button chaxunbutton;

	private String phonenumber;
	private TitleView titleView;
	private String TAG = "PhoneNumberActivity";

	public PhoneActivity() {
		super(R.layout.activity_phonenumber);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initView() {
		phonetext = (TextView) findViewById(R.id.activity_phonenumber_phone);
		citytext = (TextView) findViewById(R.id.activity_phonenumber_city);
		provincetext = (TextView) findViewById(R.id.activity_phonenumber_province);
		suppliertext = (TextView) findViewById(R.id.activity_phonenumber_supplier);
		suittext = (TextView) findViewById(R.id.activity_phonenumber_suit);
		retMsgtext = (TextView) findViewById(R.id.activity_phonenumber_error);
		linearLayout = (LinearLayout) findViewById(R.id.activity_phonenumber_lineaer);
		relativeLayout = (RelativeLayout) findViewById(R.id.activity_phonenumber_Relative);
		getphone = (EditText) findViewById(R.id.activity_phonenumber_getphone);
		chaxunbutton = (Button) findViewById(R.id.activity_phonenumber_chaxun);
		titleView = (TitleView) findViewById(R.id.phonenumber_title);
		titleView.setLeftIcon(R.drawable.title_back);
		titleView.setRightVisibility(View.GONE);
		titleView.setTitletext("归属地查询");

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void bindView() {
		titleView.getLeftIcon().setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();

			}
		});

	}

	public void chaxun(View v) {
		// 1.禁用此按钮
		chaxunbutton.setEnabled(false);
		phonenumber = getphone.getText().toString().trim();
		if (TextUtils.isEmpty(phonenumber)) {
			setEmpty("手机号码不能为空");
			chaxunbutton.setEnabled(true);
			return;
		} else {
			HttpFactory.PhoneNumber(this, phonenumber, 1);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.ltwc.cft.http.ServiceResponce#httpSuccess(java.lang.String, int)
	 */
	@SuppressWarnings("deprecation")
	public void httpSuccess(String result, int responseFlag) {
		Log.i(TAG, "result" + result);

		try {
			// JSONObject jsonObject01 = new JSONObject(result);
			// String retMsg = jsonObject01.getString("retMsg");
			// int errNum = jsonObject01.getInt("errNum");
			// Log.e(TAG, "errNum" + errNum);
			// if (errNum == 0) {
			// JSONObject jsonObject02 = jsonObject01.getJSONObject("retData");
			// String phone = jsonObject02.getString("phone");
			// String province = jsonObject02.getString("province");
			// String city = jsonObject02.getString("city");
			// String supplier = jsonObject02.getString("supplier");
			// String suit = jsonObject02.getString("suit");
			// linearLayout.setVisibility(View.VISIBLE);
			// relativeLayout.setVisibility(View.GONE);
			// phonetext.setText(phone);
			// provincetext.setText(province);
			// citytext.setText(city);
			// suppliertext.setText(supplier);
			// suittext.setText(suit);
			// } else if (errNum == -1) {
			// setEmpty(retMsg);
			// }

			Gson gson = new Gson();

			ReturnBack back = gson.fromJson(result, new TypeToken<ReturnBack>() {
			}.getType());
			if (back.getErrNum() == 0) {

				BackDate retData = back.getRetData();

				linearLayout.setVisibility(View.VISIBLE);
				relativeLayout.setVisibility(View.GONE);
				phonetext.setText(retData.getTelString());
				provincetext.setText(URLDecoder.decode(retData.getProvince()));
				suppliertext.setText(URLDecoder.decode(retData.getCarrier()));

			} else {
				setEmpty(back.getErrMsg());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// Log.e(TAG, "Exception" + e.getMessage());
		}
		chaxunbutton.setEnabled(true);
	}

	private void setEmpty(String retMsg) {
		linearLayout.setVisibility(View.GONE);
		relativeLayout.setVisibility(View.VISIBLE);
		retMsgtext.setText(retMsg);
	}

	@Override
	public void httpTimeOut(int responseFlag) {
		// TODO Auto-generated method stub
		chaxunbutton.setEnabled(true);
		setEmpty("没有网络，请检查网络");

	}

	@Override
	public void httpError(int responseFlag) {
		chaxunbutton.setEnabled(true);
		setEmpty("没有网络，请检查网络");

	}

	/**
	 * 
	 * TODO:内部类，接口返回的数据
	 * 
	 * @author huangshang 2015-11-30 下午5:24:49
	 * @Modified_By:
	 */
	class ReturnBack {
		private int errNum;//
		private String errMsg;// success",
		private BackDate retData;

		public int getErrNum() {
			return errNum;
		}

		public void setErrNum(int errNum) {
			this.errNum = errNum;
		}

		public String getErrMsg() {
			return errMsg;
		}

		public void setErrMsg(String errMsg) {
			this.errMsg = errMsg;
		}

		public BackDate getRetData() {
			return retData;
		}

		public void setRetData(BackDate retData) {
			this.retData = retData;
		}

	}

	/**
	 * 
	 * TODO:内部类，接口返回的数据
	 * 
	 * @author huangshang 2015-11-30 下午5:25:12
	 * @Modified_By:
	 */
	class BackDate {
		private String telString;// 18656638418
		private String province;// 安徽
		private String carrier;// 安徽联通

		public String getTelString() {
			return telString;
		}

		public void setTelString(String telString) {
			this.telString = telString;
		}

		public String getProvince() {
			return province;
		}

		public void setProvince(String province) {
			this.province = province;
		}

		public String getCarrier() {
			return carrier;
		}

		public void setCarrier(String carrier) {
			this.carrier = carrier;
		}

	}
}
