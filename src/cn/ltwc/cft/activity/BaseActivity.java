package cn.ltwc.cft.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;
import cn.ltwc.cft.AppManager;
import cn.ltwc.cft.view.LoadingDialog;

/**
 * 
 * TODO:本应用Activity的基类
 * 
 * @author huangshang 2015-11-10 下午2:20:20
 * @Modified_By:
 */
@SuppressLint("InlinedApi")
public abstract class BaseActivity extends FragmentActivity {
	private int layoutResId = -1;// 布局资源
	public Context c = BaseActivity.this;// 环境变量
	// 定义当前屏幕的宽高
	public int width;
	public int height;
	protected LoadingDialog lDialog;
	public BaseActivity(int layoutResID) {
		this.layoutResId = layoutResID;
	}

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 得到当前屏幕的宽和高
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		height = dm.heightPixels;
		// 沉浸式导航栏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		AppManager.getInstance().addActivity(this);
		if (layoutResId != -1) {
			setContentView(layoutResId);
		}
		initCustomWaitingDialog(c);
		initData();
		initView();
		bindView();
	}

	/** 初始化视图 */
	public abstract void initView();

	/** 初始化数据 */
	public abstract void initData();

	/** 数据与视图的绑定 */
	public abstract void bindView();

	/**
	 * Toast显示的方法
	 * 
	 * @param msg
	 *            要显示的消息
	 */
	public void show(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 隐式意图跳转Activity(不传递数据)
	 */
	public void jumpToActivity(String action) {
		Intent intent = new Intent();
		intent.setAction(action);
		startActivity(intent);
	}

	/**
	 * 隐式意图跳转Activity(传递数据)
	 */
	public void jumpToActivity(String action, Uri data) {
		Intent intent = new Intent();
		intent.setAction(action);
		intent.setData(data);
		startActivity(intent);
	}
	 public void showWaitingDialog(Context context) {
	        if (lDialog == null) {
	            initCustomWaitingDialog(context);
	        }

	        if (!lDialog.isShowing()) {
	            lDialog.show();
	        }
	    }

	    private void initCustomWaitingDialog(Context context) {
	        lDialog = new LoadingDialog(context);
	        lDialog.setCancelable(true);
	        lDialog.setCanceledOnTouchOutside(true);
	    }

	    public void hideWaitingDialog() {
	        if (lDialog != null && lDialog.isShowing()) {
	            lDialog.dismiss();
	        }
	    }
}