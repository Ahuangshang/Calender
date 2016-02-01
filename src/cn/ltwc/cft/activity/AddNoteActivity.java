package cn.ltwc.cft.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import cn.ltwc.cft.R;
import cn.ltwc.cft.beans.NoteBean;
import cn.ltwc.cft.db.NoteDB;
import cn.ltwc.cft.view.TitleView;

public class AddNoteActivity extends BaseActivity {
	private TitleView title;
	private EditText addTitle, addContent;

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
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void bindView() {
		// TODO Auto-generated method stub
		show(getCurrentTime());
		SureClick();
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

				if (TextUtils.isEmpty(noteTitle)
						|| TextUtils.isEmpty(noteContent)) {
					// 如果输入框中有一个没有内容
					show("请确认标题或内容不为空");
				} else {
					String time=getCurrentTime();
					NoteBean notebean=new NoteBean(noteTitle, noteContent, time);
					NoteDB.getInstance().addNote(notebean);
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
