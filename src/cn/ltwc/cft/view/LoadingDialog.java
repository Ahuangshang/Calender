package cn.ltwc.cft.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import cn.ltwc.cft.R;

/**
 * 加载中的对话框 Created by LZL on 16/3/28.
 */
public class LoadingDialog extends AlertDialog {

	@SuppressWarnings("unused")
	private Context context;
	private boolean cancelable = true;

	public LoadingDialog(Context context) {
		super(context, R.style.transparent_dialog_them);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
	}

	public void setCancelable(boolean cancelable) {
		this.cancelable = cancelable;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (cancelable) {
				this.dismiss();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
