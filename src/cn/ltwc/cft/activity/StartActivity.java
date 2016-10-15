package cn.ltwc.cft.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.ltwc.cft.AppManager;
import cn.ltwc.cft.R;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

@SuppressLint("HandlerLeak")
public class StartActivity extends Activity {
	private ProgressBar bar;
	private TextView progress;
	private View bg;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				bg.setVisibility(View.GONE);
				int i = (Integer) msg.obj;
				bar.setVisibility(View.VISIBLE);
				progress.setVisibility(View.VISIBLE);
				bar.setProgress(i);
				progress.setText("正在更新数据（" + i / 2 + "%)");
				break;
			case 2:
				startActivity(new Intent(StartActivity.this, MainActivity.class));
				break;
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppManager.getInstance().addActivity(this);
		setContentView(R.layout.activity_start);
		bar = (ProgressBar) findViewById(R.id.paly_seekbar);
		progress = (TextView) findViewById(R.id.progress);
		bg = findViewById(R.id.bg);
		// 搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
		// TbsDownloader.needDownload(getApplicationContext(), false);
		QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

			@Override
			public void onViewInitFinished(boolean arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onCoreInitFinished() {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.what = 2;
				handler.sendMessage(msg);

			}
		};
		QbSdk.setTbsListener(new TbsListener() {
			boolean onDownloadFinish, onInstallFinish;

			@Override
			public void onDownloadFinish(int i) {
				if (!onDownloadFinish) {
					Message msg = new Message();
					msg.obj = i;
					msg.what = 1;
					handler.sendMessage(msg);
				}
				onDownloadFinish = true;
			}

			@Override
			public void onInstallFinish(int i) {
				if (!onInstallFinish) {
					Message msg = new Message();
					msg.obj = i - 2;
					msg.what = 1;
					handler.sendMessage(msg);
				}
				onInstallFinish = true;
			}

			@Override
			public void onDownloadProgress(int i) {
				Message msg = new Message();
				msg.obj = i;
				msg.what = 1;
				handler.sendMessage(msg);
			}
		});
		QbSdk.initX5Environment(getApplicationContext(), cb);
	}
}
