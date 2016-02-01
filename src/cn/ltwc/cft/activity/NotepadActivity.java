package cn.ltwc.cft.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import cn.ltwc.cft.R;
import cn.ltwc.cft.view.TitleView;

/**
 * 
 * TODO:记事本的Activity
 * 
 * @author huangshang 2015-11-15 下午1:27:04
 * @Modified_By:
 */
public class NotepadActivity extends BaseActivity {
	private TitleView title;// 标题栏
	private ListView list;// 列表视图
	private View emptyView;// 列表为空时的视图

	public NotepadActivity() {
		super(R.layout.activity_notepad);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		title = (TitleView) findViewById(R.id.note_title);
		title.setTitletext("全部记事");
		title.setRightVisibility(View.VISIBLE);
		list = (ListView) findViewById(R.id.note_list);
		// 设置list为空时候的视图
		emptyView = findViewById(R.id.note_emptyview);
		list.setEmptyView(emptyView);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void bindView() {
		// TODO Auto-generated method stub
		// 右侧添加按钮的点击事件
		addNote();
		
	}
	/**
	 * 右侧添加按钮的点击事件
	 */
	private void addNote() {
		title.getRightIcon().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(c, AddNoteActivity.class));
			}
		});
	}

}
