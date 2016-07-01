package cn.ltwc.cft.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import cn.ltwc.cft.AppManager;
import cn.ltwc.cft.R;
import cn.ltwc.cft.adapter.CalendarAdapter;
import cn.ltwc.cft.adapter.MenuAdp;
import cn.ltwc.cft.beans.MenuBean;
import cn.ltwc.cft.beans.YiJiBean;
import cn.ltwc.cft.data.Constant;
import cn.ltwc.cft.data.LunarCalendar;
import cn.ltwc.cft.data.Variable;
import cn.ltwc.cft.datapick.PickUtils;
import cn.ltwc.cft.datapick.PickUtils.CallBack;
import cn.ltwc.cft.db.HuangLi;
import cn.ltwc.cft.myinterface.ScrollViewListener;
import cn.ltwc.cft.view.MyGridView;
import cn.ltwc.cft.view.MyScrollView;

/**
 * 
 * TODO:程序的主界面,主要是日历界面
 * 
 * @author huangshang 2015-11-19 下午11:40:20
 * @Modified_By:
 */
@SuppressLint({ "ResourceAsColor", "SimpleDateFormat" })
public class MainActivity extends BaseActivity implements ScrollViewListener {
	public GridView menu;// 菜单
	private List<MenuBean> MenuList;// 菜单的集合
	// ---------------------------------
	private GestureDetector gestureDetector = null;
	private CalendarAdapter calV = null;
	private ViewFlipper flipper = null;
	private MyGridView gridView = null;
	private static int jumpMonth = 0; // 每次滑动，增加或减去一个月,默认为0（即显示当前月）
	private static int jumpYear = 0; // 滑动跨越一年，则增加或者减去一年,默认为0(即当前年)
	private int year_c = 0;
	private int month_c = 0;
	private int day_c = 0;
	private String currentDate = "";
	/** 当前的年月，现在日历顶端 */
	private TextView currentMonth;
	private long lasttime = 0;// 上次点击时间
	public static MainActivity instance;
	public int chooseday;
	private TextView nongli, yi, ji;// 选中日期的农历信息
	private MyScrollView myscrollview;

	public MainActivity() {
		super(R.layout.activity_main);
		// TODO Auto-generated constructor stub
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		currentDate = sdf.format(date); // 当期日期
		year_c = Integer.parseInt(currentDate.split("-")[0]);
		month_c = Integer.parseInt(currentDate.split("-")[1]);
		day_c = Integer.parseInt(currentDate.split("-")[2]);
		chooseday = day_c;
		instance = this;

	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		menu = (GridView) findViewById(R.id.main_menu);
		// ------------------------------------------------
		currentMonth = (TextView) findViewById(R.id.month);

		gestureDetector = new GestureDetector(c, new MyGestureListener());
		flipper = (ViewFlipper) findViewById(R.id.flipper);
		flipper.removeAllViews();
		calV = new CalendarAdapter(c, getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
		addGridView();
		gridView.setAdapter(calV);
		flipper.addView(gridView, 0);
		addTextToTopTextView(currentMonth);
		// show(LunarCalendar.getInstance().getCalendarInfoByChooseDay(Integer.parseInt(calV.getShowYear()),
		// Integer.parseInt(calV.getShowMonth()), day_c));// 显示农历信息
		nongli = (TextView) findViewById(R.id.nongli);
		yi = (TextView) findViewById(R.id.yi);
		ji = (TextView) findViewById(R.id.ji);
		myscrollview = (MyScrollView) findViewById(R.id.scrollview);
		showNongLi(
				LunarCalendar.getInstance().getCalendarInfoByChooseDay(Integer.parseInt(calV.getShowYear()),
						Integer.parseInt(calV.getShowMonth()), day_c),
				calV.getShowYear(), calV.getShowMonth(), day_c + "");
	}

	public void showNongLi(String nongliStr, String year, String month, String day) {
		nongli.setText(nongliStr);
		YiJiBean bean = HuangLi.getInstance().quearHuangli(year, month, day);
		yi.setText(TextUtils.isEmpty(bean.getYi()) ? "诸事不宜" : bean.getYi());
		ji.setText(TextUtils.isEmpty(bean.getJi()) ? "黄道吉日，诸事大吉" : bean.getJi());
	}

	@Override
	public void initData() {
		// 初始化菜单集合
		MenuList = new ArrayList<MenuBean>();
		for (int i = 0; i < Variable.Icon.length; i++) {
			MenuBean bean = new MenuBean(Variable.Icon[i], Variable.Name[i]);
			MenuList.add(bean);
		}
	}

	@Override
	public void bindView() {
		// TODO Auto-generated method stub
		MenuAdp rma = new MenuAdp(c, MenuList);
		// 设置适配器
		menu.setAdapter(rma);
		// 菜单组的点击事件
		menuClickeListenner();
		// 标题栏里的日期的点击事件(实现轮子选择日期控件)
		currentMonthClickListenner();
		myscrollview.setScrollViewListener(this);
	}

	/**
	 * 标题栏里的日期的点击事件(实现轮子选择日期控件)
	 */
	private void currentMonthClickListenner() {
		currentMonth.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LayoutInflater inflater = LayoutInflater.from(c);// 得到视图转换器
				PickUtils.getInstance().setInflater(inflater);// 设置视图转换器
				String ym = currentMonth.getText().toString();// 得到标题里的年月信息
				final int cyear = Integer.parseInt(ym.substring(0, ym.indexOf("年")));// 得到标题里的年份
				final int cmonth = Integer.parseInt(ym.substring(ym.indexOf("年") + 1, ym.indexOf("月")));// 得到标题里的月份
				PickUtils.getInstance().showPopwindow(PickUtils.getInstance().getDataPick(cyear, cmonth, chooseday));// 弹出日期选择器
				// 设置回调接口，返回数据
				PickUtils.getInstance().setCallback(new CallBack() {

					@Override
					public void SetStr(String str) {
						currentMonth.setText(str.substring(0, str.indexOf("月") + 1));
						int year = Integer.parseInt(str.substring(0, str.indexOf("年")));// 得到选择的年份
						int month = Integer.parseInt(str.substring(str.indexOf("年") + 1, str.indexOf("月")));// 得到选择的月份
						int day = Integer.parseInt(str.substring(str.indexOf("月") + 1, str.length()));// 得到选中的日期
						chooseday = day;// 轮子选择器得到的时间为选择时间
						// Log.i("MainActivity", "year=" + year + "****month="
						// + month);
						// =========设置当前日历界面到选择的界面===============
						int jumpYear_c = year - cyear;
						int jumpMonth_c = month - cmonth;
						jumpMonth_c = jumpYear_c * 12 + jumpMonth_c;
						jumpYear += jumpYear_c;
						jumpMonth += jumpMonth_c;
						JumpTodata(false, cyear, cmonth, day, jumpYear_c, jumpMonth_c, year, month, day);
						// =========================
					}
				});
			}
		});
	}

	/**
	 * 菜单组的点击事件
	 */
	private void menuClickeListenner() {
		menu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:// 记事本的点击事件
					startActivity(new Intent(c, NotepadActivity.class));

					break;
				case 1:// 纪念日的点击事件
					startActivity(new Intent(c, MemorialDayActivity.class));

					break;
				case 2:// 登陆的点击事件

					break;
				case 3:// 更多的点击事件
					startActivity(new Intent(c, MoreActivity.class));
					break;
				case 4:// 今日的点击事件
					JumpTodata(true, year_c, month_c, day_c, jumpYear, jumpMonth, year_c, month_c, day_c);
					chooseday = day_c;// 选中的日期为今天
					break;
				}
			}

		});
	}

	/**
	 * 跳转到指定的月份的某一天
	 * 
	 * @param istotoday
	 *            是否是点击了回到今天(true为点击了回到今天,false为其他的事件响应)
	 * @param year
	 *            标题栏里的年份
	 * @param month
	 *            标题栏里的月份
	 * @param day
	 *            标题栏里的日期
	 * @param jumpYear_c
	 *            需要跳转的年份
	 * @param jumpMonth_c
	 *            需要跳转的月份
	 * @param chooseYear
	 *            当前选择的年份
	 * @param chooseMonth
	 *            当前选择的月份
	 * @param chooseDay
	 *            当前选择的日期
	 */
	private void JumpTodata(boolean istotoday, int year, int month, int day, int jumpYear_c, int jumpMonth_c,
			int chooseYear, int chooseMonth, int chooseDay) {

		if (jumpYear_c == 0 && jumpMonth_c == 0) {// 如果当前界面在本年本月
			// 得到当前日期在GridView里的下标
			int pos = ((CalendarAdapter) gridView.getAdapter()).currentFlag;
			if (pos != -1 && istotoday) {
				setChooseBg(pos);
				return;
			}
		} else {// 如果当前界面不在本年本月
			if (istotoday) {// 如果点击的是回到今天
				jumpMonth = 0;
				jumpYear = 0;
				jumpMonth_c = 0;
				jumpYear_c = 0;
			}
		}
		flipper.removeAllViews();
		calV = new CalendarAdapter(c, getResources(), jumpMonth_c, jumpYear_c, year, month, day);
		addGridView();
		gridView.setAdapter(calV);
		flipper.addView(gridView, 0);
		addTextToTopTextView(currentMonth);
		// show(LunarCalendar.getInstance().getCalendarInfoByChooseDay(chooseYear,
		// chooseMonth, chooseDay));// 得到跳转后的农历信息
		showNongLi(LunarCalendar.getInstance().getCalendarInfoByChooseDay(chooseYear, chooseMonth, chooseDay),
				chooseYear + "", chooseMonth + "", chooseDay + "");
	}

	/**
	 * 设置选择日期的背景
	 */
	@SuppressWarnings("deprecation")
	private void setChooseBg(int position) {
		// 循环遍历GridView里面所有的子项，将背景设为默认状态
		for (int i = 0; i < gridView.getChildCount(); i++) {
			gridView.getChildAt(i).setBackgroundColor(0Xffffff);// 设置背景
		}
		// Drawable drawable;
		int resid;
		if (position == calV.currentFlag) {
			// drawable = new ColorDrawable(Color.rgb(79, 210, 190));
			resid = R.drawable.current_bg;
		} else {
			// 设置选中日期的背景
			// drawable = new ColorDrawable(Color.rgb(198, 226, 255));
			resid = R.drawable.select_bg;
		}
		// gridView.getChildAt(position).setBackgroundDrawable(drawable);
		gridView.getChildAt(position).setBackgroundResource(resid);
	}

	private class MyGestureListener extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			int gvFlag = 0; // 每次添加gridview到viewflipper中时给的标记
			if (e1.getX() - e2.getX() > 120) {
				// 像左滑动
				enterNextMonth(gvFlag);
				return true;
			} else if (e1.getX() - e2.getX() < -120) {
				// 向右滑动
				enterPrevMonth(gvFlag);
				return true;
			}
			return false;
		}
	}

	/**
	 * 移动到下一个月
	 * 
	 * @param gvFlag
	 */
	private void enterNextMonth(int gvFlag) {
		addGridView(); // 添加一个gridView
		jumpMonth++; // 下一个月

		flushView(gvFlag, AnimationUtils.loadAnimation(c, R.anim.push_left_in),
				AnimationUtils.loadAnimation(c, R.anim.push_left_out));
	}

	/**
	 * 移动到上一个月和下一个月刷新视图
	 * 
	 * @param gvFlag
	 * @param inAnimation
	 *            画面进入动画
	 * @param outAnimation
	 *            画面退出动画
	 */
	private void flushView(int gvFlag, Animation inAnimation, Animation outAnimation) {
		calV = new CalendarAdapter(c, this.getResources(), jumpMonth, jumpYear, year_c, month_c, chooseday);
		gridView.setAdapter(calV);
		if (calV.flag) {
			chooseday = 1;
		}
		addTextToTopTextView(currentMonth); // 移动到下一月后，将当月显示在头标题中
		gvFlag++;
		flipper.addView(gridView, gvFlag);
		flipper.setInAnimation(inAnimation);
		flipper.setOutAnimation(outAnimation);
		flipper.showNext();
		flipper.removeViewAt(0);
		// show(LunarCalendar.getInstance().getCalendarInfoByChooseDay(Integer.parseInt(calV.getShowYear()),
		// Integer.parseInt(calV.getShowMonth()), chooseday));
		showNongLi(
				LunarCalendar.getInstance().getCalendarInfoByChooseDay(Integer.parseInt(calV.getShowYear()),
						Integer.parseInt(calV.getShowMonth()), chooseday),
				calV.getShowYear(), calV.getShowMonth(), chooseday + "");
	}

	/**
	 * 移动到上一个月
	 * 
	 * @param gvFlag
	 */
	private void enterPrevMonth(int gvFlag) {
		addGridView(); // 添加一个gridView
		jumpMonth--; // 上一个月
		flushView(gvFlag, AnimationUtils.loadAnimation(c, R.anim.push_right_in),
				AnimationUtils.loadAnimation(c, R.anim.push_right_out));

	}

	/**
	 * 添加头部的年份 闰哪月等信息
	 * 
	 * @param view
	 */
	public void addTextToTopTextView(TextView view) {
		StringBuffer textDate = new StringBuffer();
		// draw = getResources().getDrawable(R.drawable.top_day);
		// view.setBackgroundDrawable(draw);
		textDate.append(calV.getShowYear()).append("年").append(calV.getShowMonth()).append("月").append("\t");
		view.setText(textDate);
	}

	/**
	 * 添加一个网格视图
	 */
	@SuppressLint("ClickableViewAccessibility")
	private void addGridView() {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);

		gridView = new MyGridView(c);
		gridView.setNumColumns(7);
		gridView.setColumnWidth(40);
		// gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		if (width == 720 && height == 1280) {
			gridView.setColumnWidth(40);
		}
		gridView.setGravity(Gravity.CENTER_VERTICAL);
		// 去除gridView边框
		gridView.setVerticalSpacing(0);
		gridView.setHorizontalSpacing(0);
		gridView.setOnTouchListener(new OnTouchListener() {
			// 将GridView中的触摸事件回传给gestureDetector
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return MainActivity.this.gestureDetector.onTouchEvent(event);
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				setChooseBg(position);// 设置选中的背景
				// =====================================
				// TODO Auto-generated method stub
				// 点击任何一个item，得到这个item的日期(排除点击的是周日到周六(点击不响应))
				int startPosition = calV.getStartPositon();
				int endPosition = calV.getEndPosition();

				String scheduleDay = calV.getDateByClickItem(position).split("\\.")[0]; // 这一天的阳历
				chooseday = Integer.parseInt(scheduleDay);// 点击日历的哪一天时为选择日期
				if (startPosition <= position + 7 && position <= endPosition - 7) {
					showNongLi(getCalendarInfo(position), calV.getShowYear(), calV.getShowMonth(),
							calV.getDateByClickItem(position).split("\\.")[0]);

				}
				if (position < startPosition - 7) {
					// 如果点击的下标比当月开始的下标小，则跳转到上一个月，并显示那一天
					enterPrevMonth(0);
				}
				if (position > endPosition - 7) {
					enterNextMonth(0);
				}
			}
		});
		gridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				int startPosition = calV.getStartPositon();
				int endPosition = calV.getEndPosition();
				if (startPosition <= position + 7 && position <= endPosition - 7) {
					// 只有在当前月份时才可以有长按的响应事件
					getCalendarInfo(position);
				}
				return true;
			}

		});
		gridView.setLayoutParams(params);

	}

	/**
	 * 通过日历视图的下标得到日期农历信息
	 * 
	 * @param position
	 *            日历视图的下标
	 * @return 农历信息(包含农历年份月份日期以及周几)
	 */
	private String getCalendarInfo(int position) {
		String scheduleYear = calV.getShowYear();// 得到当前的阳历的年份
		String scheduleMonth = calV.getShowMonth();// 得到当前的阳历的月份
		String scheduleDay = calV.getDateByClickItem(position).split("\\.")[0]; // 得到当前的阳历的日期
		int year_long = Integer.parseInt(scheduleYear);// 当前的阳历的年份
		int month_long = Integer.parseInt(scheduleMonth);// 当前的阳历的月份
		int day_long = Integer.parseInt(scheduleDay);// 当前的阳历的日期
		return (LunarCalendar.getInstance().getCalendarInfoByChooseDay(year_long, month_long, day_long));
	}

	/**
	 * 监听返回键
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		// 如果按下的是返回键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 如果当前点击时间减去上次点击时间小于退出时间
			if (System.currentTimeMillis() - lasttime > Constant.exitTime) {
				lasttime = System.currentTimeMillis();
				show("再次点击退出程序");
			} else {
				AppManager.getInstance().appExit(c);// 程序退出
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
		// TODO Auto-generated method stub
		if (y - oldy > 200 || y - oldy < -200) {
			myscrollview.scrollTo(x, y);
		}

	}
}
