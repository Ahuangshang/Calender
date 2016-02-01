package cn.ltwc.cft.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import cn.ltwc.cft.R;
import cn.ltwc.cft.adapter.ShowNotesAdapter;
import cn.ltwc.cft.beans.NoteBean;
import cn.ltwc.cft.db.NoteDB;
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
	private List<NoteBean> notes;
	private ShowNotesAdapter adapter;

	public NotepadActivity() {
		super(R.layout.activity_notepad);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		reflushData();
	}

	private void reflushData() {
		List<NoteBean> temp = NoteDB.getInstance().getAll();
		notes.clear();
		notes.addAll(temp);
		adapter.notifyDataSetChanged();
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
		notes = new ArrayList<NoteBean>();

	}

	@Override
	public void bindView() {
		// TODO Auto-generated method stub
		// 右侧添加按钮的点击事件
		addNote();
		adapter = new ShowNotesAdapter(c, notes);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

			}
		});
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				dialog(position);
				return false;
			}
		});
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

	protected void dialog(final int position) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		String dialogTitle = "提示";
		builder.setMessage(dialogTitle);
		builder.setTitle(dialogTitle);
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				NoteDB.getInstance().deleteItem(
						notes.get(position).getCurrentTime());
				reflushData();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

}
