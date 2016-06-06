package cn.ltwc.cft.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import cn.ltwc.cft.R;
import cn.ltwc.cft.beans.NoteBean;
import cn.ltwc.cft.db.NoteDB;
import cn.ltwc.cft.utils.BitMapUtil;
import cn.ltwc.cft.view.TitleView;

public class AddNoteActivity extends BaseActivity {
	private TitleView title;
	private EditText addTitle, addContent;
	private LinearLayout root;
	private int topHeight;
	private View top;

	public AddNoteActivity() {
		super(R.layout.activity_addnote);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		title = (TitleView) findViewById(R.id.addnote_title);
		title.setTitletext("新建记事");
		title.setRightBtnTextVisibility(View.VISIBLE);
		addTitle = (EditText) findViewById(R.id.addnote_addtitle);
		addContent = (EditText) findViewById(R.id.addnote_content);
		root = (LinearLayout) findViewById(R.id.root);
		top = findViewById(R.id.top);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void bindView() {
		// TODO Auto-generated method stub
		SureClick();
		// addContent.setMovementMethod(ScrollingMovementMethod.getInstance());
		addContent.setSelection(addContent.getText().length(), addContent.getText().length());
		ViewTreeObserver vto = root.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {

				// TODO Auto-generated method stub
				// root.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				Rect r = new Rect();
				root.getWindowVisibleDisplayFrame(r);
				int screenHeight = root.getRootView().getHeight();
				int heightDifference = screenHeight - (r.bottom - r.top);
				Log.d("AA", "Size:" + heightDifference);
				if (heightDifference > screenHeight / 3) {
					// 软键盘弹起
					Log.d("AA", "新高度:" + (screenHeight - heightDifference - topHeight
							- BitMapUtil.dip2px(AddNoteActivity.this, 30)));
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
							ViewGroup.LayoutParams.MATCH_PARENT,
							screenHeight - heightDifference - topHeight - BitMapUtil.dip2px(AddNoteActivity.this, 30));
					addContent.setLayoutParams(params);

				} else {
					// 软键盘关闭
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
							ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
					addContent.setLayoutParams(params);
				}

			}
		});

		top.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				top.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				topHeight = top.getHeight();
				Log.d("AA", "初始高度：" + topHeight);

			}
		});
	}

	/**
	 * 右侧完成按钮的点击事件
	 */
	private void SureClick() {
		// TODO Auto-generated method stub
		title.getRightText().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String noteTitle = addTitle.getText().toString();
				String noteContent = addContent.getText().toString();

				if (TextUtils.isEmpty(noteTitle) || TextUtils.isEmpty(noteContent)) {
					// 如果输入框中有一个没有内容
					show("请确认标题或内容不为空");
				} else {
					String time = getCurrentTime();
					NoteBean notebean = new NoteBean(noteTitle, noteContent, time, System.currentTimeMillis() + "");
					NoteDB.getInstance().addNote(notebean);
					finish();
				}
			}
		});
	}

	@SuppressLint("SimpleDateFormat")
	private String getCurrentTime() {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return (format.format(date));
	}

}
