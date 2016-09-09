package cn.ltwc.cft.activity;

import java.io.File;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;
import cn.ltwc.cft.AppManager;
import cn.ltwc.cft.R;
import cn.ltwc.cft.adapter.ShareAdapter;
import cn.ltwc.cft.data.Constant;
import cn.ltwc.cft.myinterface.MeNvItemImgClickListener;
import cn.ltwc.cft.utils.FileUtils;
import cn.ltwc.cft.utils.HLUtil;

@SuppressLint("InlinedApi")
public class ShareActivity extends Activity implements OnClickListener, MeNvItemImgClickListener {
	// 解决退出动画无效
	protected int activityCloseEnterAnimation;
	protected int activityCloseExitAnimation;
	private TextView cancel;
	private RecyclerView rv;
	private ShareAdapter adapter;
	private List<ResolveInfo> list;
	private String type;// 分享类型
	private String msg;// 分享的文字
	private String imgPath;// 分享的图片地址

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// // 沉浸式导航栏
		// getWindow()
		// .addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		// getWindow().addFlags(
		// WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		AppManager.getInstance().addActivity(this);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_share);
		dealExitAniom();
		initView();
		initData();
		bindView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		cancel = (TextView) findViewById(R.id.cancel);
		rv = (RecyclerView) findViewById(R.id.rv);
		rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));

	}

	private void initData() {
		// TODO Auto-generated method stub
		type = getIntent().getStringExtra(Constant.TYPE);
		msg = getIntent().getStringExtra(Constant.SHARE_TYPE_TEXT);
		imgPath = getIntent().getStringExtra(Constant.SHARE_TYPE_IMG);
		list = HLUtil.getShareApps(this, type);
		for (int i = 0; i < list.size(); i++) {
			// ResolveInfo info=list.get(i);
			// Log.d("AA", info.resolvePackageName);
			// Log.d("AA", info.activityInfo.packageName);

		}
		adapter = new ShareAdapter(this, list, this);
		rv.setAdapter(adapter);
	}

	private void bindView() {
		// TODO Auto-generated method stub
		cancel.setOnClickListener(this);
	}

	/**
	 * 解决退出动画无效的方法
	 */
	private void dealExitAniom() {
		TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[] { android.R.attr.windowAnimationStyle });

		int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);

		activityStyle.recycle();

		activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId,
				new int[] { android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation });

		activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);

		activityCloseExitAnimation = activityStyle.getResourceId(1, 0);

		activityStyle.recycle();
	}

	@Override
	public void finish() {
		super.finish();
		// 要在结束时调用此方法，才能使退出动画起作用
		overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);

	}

	/**
	 * Activity设置为对话框属性时（Theme.Dialog）时，改变其在屏幕中的位置
	 */
	@Override
	public void onAttachedToWindow() {
		// 设置本Activity在父窗口的位置
		super.onAttachedToWindow();
		View view = getWindow().getDecorView();
		WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
		// lp.gravity = Gravity.RIGHT | Gravity.BOTTOM;
		lp.gravity = Gravity.BOTTOM;
		lp.x = getResources().getDimensionPixelSize(R.dimen.playqueue_dialog_marginright);
		lp.y = getResources().getDimensionPixelSize(R.dimen.playqueue_dialog_marginbottom);
		lp.width = getResources().getDimensionPixelSize(R.dimen.playqueue_dialog_width);
		// lp.height = getResources().getDimensionPixelSize(
		// R.dimen.playqueue_dialog_height);
		getWindowManager().updateViewLayout(view, lp);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.cancel:
			this.finish();
			break;
		}
	}

	@Override
	public void onClick(int position) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		ResolveInfo info = list.get(position);
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		
		
		Log.d("AA",info.activityInfo.packageName);
		Log.d("AA",info.activityInfo.name);
		shareIntent.setComponent(new ComponentName(info.activityInfo.packageName, info.activityInfo.name));
		if (type.equals(Constant.SHARE_TYPE_TEXT)) {
			shareIntent.putExtra(Intent.EXTRA_TEXT, msg);
		} else if (type.equals(Constant.SHARE_TYPE_IMG)) {
			if (FileUtils.isExit(imgPath)) {
				File f = new File(imgPath);
				Uri u = Uri.fromFile(f);
				shareIntent.putExtra(Intent.EXTRA_STREAM, u);
				shareIntent.putExtra("Kdescription", msg);
				// shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_GRANT_READ_URI_PERMISSION);
			}
		} else if (type.equals(Constant.SHARE_TYPE_AUDIO)) {

		}
		shareIntent.setType(type);
		startActivity(shareIntent);
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ShareActivity.this.finish();
			}
		}, 200);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}
}
