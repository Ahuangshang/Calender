package cn.ltwc.cft.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
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
import cn.ltwc.cft.adapter.XiaoMIWeatherAdapter;
import cn.ltwc.cft.beans.MenuBean;
import cn.ltwc.cft.beans.RiqiBean;
import cn.ltwc.cft.beans.XiaomiWeather;
import cn.ltwc.cft.beans.XiaomiZhishuList;
import cn.ltwc.cft.beans.YiJiBean;
import cn.ltwc.cft.data.Constant;
import cn.ltwc.cft.data.LunarCalendar;
import cn.ltwc.cft.data.Variable;
import cn.ltwc.cft.datapick.PickUtils;
import cn.ltwc.cft.datapick.PickUtils.CallBack;
import cn.ltwc.cft.db.HuangLi;
import cn.ltwc.cft.http.HttpFactory;
import cn.ltwc.cft.http.ServiceResponce;
import cn.ltwc.cft.utils.FileUtils;
import cn.ltwc.cft.utils.Utils;
import cn.ltwc.cft.view.ContainerLayout;
import cn.ltwc.cft.view.MyGridView;
import cn.ltwc.cft.view.MyListView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationClientOption.AMapLocationProtocol;
import com.amap.api.location.AMapLocationListener;

/**
 * 
 * TODO:程序的主界面,主要是日历界面
 * 
 * @author huangshang 2015-11-19 下午11:40:20
 * @Modified_By:
 */
@SuppressLint({ "ResourceAsColor", "SimpleDateFormat" })
public class MainActivity extends BaseActivity implements OnClickListener,
		ServiceResponce {
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
	private ContainerLayout myscrollview;
	private RiqiBean rbean;// 要跳转到下一个界面的日期信息
	private View nongLiInfo;// 农历信息栏
	private View lotterMore, ssq;// 更多开奖，双色球开奖
	private TextView ssqi, red1, red2, red3, red4, red5, red6, blue;// 双色球期号、红色球1~6、蓝色球
	private AMapLocationClient locationClient = null;
	private AMapLocationClientOption locationOption = new AMapLocationClientOption();

	private TextView weather;
	private MyListView myListView;

	public MainActivity() {
		super(R.layout.activity_main);
		// TODO Auto-generated constructor stub
		getCurrentDay();
		instance = this;
	}

	private void getCurrentDay() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		currentDate = sdf.format(date); // 当期日期
		year_c = Integer.parseInt(currentDate.split("-")[0]);
		month_c = Integer.parseInt(currentDate.split("-")[1]);
		day_c = Integer.parseInt(currentDate.split("-")[2]);
		chooseday = day_c;
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
		calV = new CalendarAdapter(c, jumpMonth, jumpYear, year_c, month_c,
				day_c);
		addGridView();
		gridView.setAdapter(calV);
		flipper.addView(gridView, 0);
		addTextToTopTextView(currentMonth);
		// show(LunarCalendar.getInstance().getCalendarInfoByChooseDay(Integer.parseInt(calV.getShowYear()),
		// Integer.parseInt(calV.getShowMonth()), day_c));// 显示农历信息
		nongli = (TextView) findViewById(R.id.nongli);
		yi = (TextView) findViewById(R.id.yi);
		ji = (TextView) findViewById(R.id.ji);
		myscrollview = (ContainerLayout) findViewById(R.id.scrollview);
		lotterMore = findViewById(R.id.more_lotter);
		ssq = findViewById(R.id.ssq);
		ssqi = (TextView) findViewById(R.id.ssq_qi);
		red1 = (TextView) findViewById(R.id.red_1);
		red2 = (TextView) findViewById(R.id.red_2);
		red3 = (TextView) findViewById(R.id.red_3);
		red4 = (TextView) findViewById(R.id.red_4);
		red5 = (TextView) findViewById(R.id.red_5);
		red6 = (TextView) findViewById(R.id.red_6);
		blue = (TextView) findViewById(R.id.blue);
		nongLiInfo = findViewById(R.id.nongli_show);
		showNongLi(
				LunarCalendar.getInstance().getCalendarInfoByChooseDay(
						Integer.parseInt(calV.getShowYear()),
						Integer.parseInt(calV.getShowMonth()), day_c),
				calV.getShowYear(), calV.getShowMonth(), day_c + "");
		int position = ((CalendarAdapter) gridView.getAdapter()).currentFlag_;
		myscrollview.setRowNum(position / 7);
		weather = (TextView) findViewById(R.id.weather);
		myListView = (MyListView) findViewById(R.id.my_list_view);
	}

	@Override
	public void initData() {
		// 初始化菜单集合
		MenuList = new ArrayList<MenuBean>();
		for (int i = 0; i < Variable.Icon.length; i++) {
			MenuBean bean = new MenuBean(Variable.Icon[i], Variable.Name[i]);
			MenuList.add(bean);
		}
		HttpFactory.getSSQLotter(this, "ssq", 1);
		initLocation();
		startLocation();
	}

	@SuppressLint("ClickableViewAccessibility")
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
		nongLiInfo.setOnClickListener(this);
		lotterMore.setOnClickListener(this);
		ssq.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	/**
	 * 菜单组的点击事件
	 */
	private void menuClickeListenner() {
		menu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
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
					JumpTodata(true, year_c, month_c, day_c, jumpYear,
							jumpMonth, year_c, month_c, day_c);
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
	private void JumpTodata(boolean istotoday, int year, int month, int day,
			int jumpYear_c, int jumpMonth_c, int chooseYear, int chooseMonth,
			int chooseDay) {
		if (jumpYear_c == 0 && jumpMonth_c == 0) {// 如果当前界面在本年本月
			// 得到当前日期在GridView里的下标
			int pos = ((CalendarAdapter) gridView.getAdapter()).currentFlag;
			if (pos != -1 && istotoday) {
				// myscrollview.setRowNum((pos / 7));
				setChooseBg(pos);
				showNongLi(
						LunarCalendar.getInstance().getCalendarInfoByChooseDay(
								chooseYear, chooseMonth, chooseDay), chooseYear
								+ "", chooseMonth + "", chooseDay + "");
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
		calV = new CalendarAdapter(c, jumpMonth_c, jumpYear_c, year, month, day);
		addGridView();
		gridView.setAdapter(calV);
		flipper.addView(gridView, 0);
		addTextToTopTextView(currentMonth);
		showNongLi(
				LunarCalendar.getInstance().getCalendarInfoByChooseDay(
						chooseYear, chooseMonth, chooseDay), chooseYear + "",
				chooseMonth + "", chooseDay + "");
		int position = ((CalendarAdapter) gridView.getAdapter()).currentFlag_;
		myscrollview.setRowNum((position / 7));
		myscrollview.collapse2();
	}

	/**
	 * 设置选择日期的背景
	 */
	private void setChooseBg(int position) {
		try {
			// 循环遍历GridView里面所有的子项，将背景设为默认状态
			for (int i = 0; i < gridView.getChildCount(); i++) {
				gridView.getChildAt(i).setBackgroundColor(0Xffffff);// 设置背景
				gridView.getChildAt(i).findViewById(R.id.bg)
						.setBackgroundColor(0Xffffff);
			}
			int resid;
			if (position == calV.currentFlag) {
				resid = R.drawable.current_bg;
			} else {
				// 设置选中日期的背景
				resid = R.drawable.select_bg;
			}
			gridView.getChildAt(position).findViewById(R.id.bg)
					.setBackgroundResource(resid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		myscrollview.setRowNum((position / 7));
		myscrollview.collapse2();
	}

	private class MyGestureListener extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			int gvFlag = 0; // 每次添加gridView到viewFlipper中时给的标记
			try {
				if (e1.getX() - e2.getX() > 120) {
					// 像左滑动
					enterNextMonth(gvFlag);
					return true;
				}
				if (e1.getX() - e2.getX() < -120) {
					// 向右滑动
					enterPrevMonth(gvFlag);
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
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
	private void flushView(int gvFlag, Animation inAnimation,
			Animation outAnimation) {
		calV = new CalendarAdapter(c, jumpMonth, jumpYear, year_c, month_c,
				chooseday);
		gridView.setAdapter(calV);
		int position = calV.currentFlag_;
		myscrollview.setRowNum((position / 7));
		if (calV.flag) {
			chooseday = 1;
			myscrollview.setRowNum(0);
		}
		myscrollview.collapse2();
		addTextToTopTextView(currentMonth); // 移动到下一月后，将当月显示在头标题中
		gvFlag++;
		flipper.addView(gridView, gvFlag);
		flipper.setInAnimation(inAnimation);
		flipper.setOutAnimation(outAnimation);
		flipper.showNext();
		flipper.removeViewAt(0);
		showNongLi(
				LunarCalendar.getInstance().getCalendarInfoByChooseDay(
						Integer.parseInt(calV.getShowYear()),
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
		flushView(gvFlag,
				AnimationUtils.loadAnimation(c, R.anim.push_right_in),
				AnimationUtils.loadAnimation(c, R.anim.push_right_out));

	}

	/**
	 * 添加头部的年份 闰哪月等信息
	 * 
	 * @param view
	 */
	public void addTextToTopTextView(TextView view) {
		StringBuffer textDate = new StringBuffer();
		textDate.append(calV.getShowYear()).append("年")
				.append(calV.getShowMonth()).append("月").append("\t");
		view.setText(textDate);
	}

	/**
	 * 添加一个网格视图
	 */
	@SuppressLint("ClickableViewAccessibility")
	private void addGridView() {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		gridView = new MyGridView(c);
		gridView.setNumColumns(7);
		gridView.setGravity(Gravity.CENTER_VERTICAL);
		// 去除gridView边框
		gridView.setVerticalSpacing(0);
		gridView.setHorizontalSpacing(0);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));// 去除点击效果
		gridView.setOnTouchListener(new OnTouchListener() {
			// 将GridView中的触摸事件回传给gestureDetector
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return MainActivity.this.gestureDetector.onTouchEvent(event);
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				setChooseBg(position);// 设置选中的背景
				// =====================================
				// TODO Auto-generated method stub
				// 点击任何一个item，得到这个item的日期(排除点击的是周日到周六(点击不响应))
				// myscrollview.setRowNum((position / 7));
				int startPosition = calV.getStartPositon();
				int endPosition = calV.getEndPosition();

				String scheduleDay = calV.getDateByClickItem(position).split(
						"\\.")[0]; // 这一天的阳历
				chooseday = Integer.parseInt(scheduleDay);// 点击日历的哪一天时为选择日期
				if (startPosition <= position + 7
						&& position <= endPosition - 7) {
					showNongLi(getCalendarInfo(position), calV.getShowYear(),
							calV.getShowMonth(),
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
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				int startPosition = calV.getStartPositon();
				int endPosition = calV.getEndPosition();
				if (startPosition <= position + 7
						&& position <= endPosition - 7) {
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
		return (LunarCalendar.getInstance().getCalendarInfoByChooseDay(
				year_long, month_long, day_long));
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

	public RiqiBean getRbean() {
		return rbean;
	}

	public void setRbean(RiqiBean rbean) {
		this.rbean = rbean;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.nongli_show:// 农历信息栏的点击事件
			Intent intent = new Intent(MainActivity.this,
					DayDetailActivity.class);
			intent.putExtra(Constant.RILIINFO, rbean);
			startActivity(intent);
			break;
		case R.id.more_lotter:// 更多开奖
			// Intent more = new Intent(MainActivity.this,
			// MyWebViewActivity.class);
			Intent more = new Intent(MainActivity.this, MyX5WebView.class);
			more.putExtra(
					Constant.WEBURL,
					"http://cp.mi.com/android_asset/www/newmicai/lotteryinfo/kjgg.html#?page=kjgg_index&tag_from=500");
			more.putExtra(Constant.WEBTITLE, "开奖公告");
			startActivity(more);
			break;
		case R.id.ssq:// 双色球
			// Intent ss = new Intent(MainActivity.this,
			// MyWebViewActivity.class);
			Intent ss = new Intent(MainActivity.this, MyX5WebView.class);
			ss.putExtra(
					Constant.WEBURL,
					"http://cp.mi.com/android_asset/www/newmicai/lotteryinfo/kjgg.html?tag_from=500#?page=kjgg_list&lot_code=50");
			ss.putExtra(Constant.WEBTITLE, "双色球开奖详情");
			startActivity(ss);
			break;
		}
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
				final int cyear = Integer.parseInt(ym.substring(0,
						ym.indexOf("年")));// 得到标题里的年份
				final int cmonth = Integer.parseInt(ym.substring(
						ym.indexOf("年") + 1, ym.indexOf("月")));// 得到标题里的月份
				PickUtils.getInstance().showPopwindow(
						PickUtils.getInstance().getDataPick(cyear, cmonth,
								chooseday));// 弹出日期选择器
				// 设置回调接口，返回数据
				PickUtils.getInstance().setCallback(new CallBack() {

					@Override
					public void SetStr(String str) {
						currentMonth.setText(str.substring(0,
								str.indexOf("月") + 1));
						int year = Integer.parseInt(str.substring(0,
								str.indexOf("年")));// 得到选择的年份
						int month = Integer.parseInt(str.substring(
								str.indexOf("年") + 1, str.indexOf("月")));// 得到选择的月份
						int day = Integer.parseInt(str.substring(
								str.indexOf("月") + 1, str.length()));// 得到选中的日期
						chooseday = day;// 轮子选择器得到的时间为选择时间
						// =========设置当前日历界面到选择的界面===============
						int jumpYear_c = year - cyear;
						int jumpMonth_c = month - cmonth;
						jumpMonth_c = jumpYear_c * 12 + jumpMonth_c;
						jumpYear += jumpYear_c;
						jumpMonth += jumpMonth_c;
						JumpTodata(false, cyear, cmonth, day, jumpYear_c,
								jumpMonth_c, year, month, day);
						// =========================
					}
				});
			}
		});
	}

	/**
	 * 显示选中日期信息
	 * 
	 * @param nongliStr农历信息
	 * @param year阳历的年
	 * @param month阳历的月
	 * @param day阳历的日
	 */
	public void showNongLi(String nongliStr, String year, String month,
			String day) {
		nongli.setText(nongliStr);
		YiJiBean bean = HuangLi.getInstance().quearHuangli(year, month, day);
		yi.setText(TextUtils.isEmpty(bean.getYi()) ? "诸事不宜" : bean.getYi());
		ji.setText(TextUtils.isEmpty(bean.getJi()) ? "黄道吉日，诸事大吉" : bean.getJi());
		rbean = LunarCalendar.getInstance().getRiqiBeanInfo(
				Integer.parseInt(year), Integer.parseInt(month),
				Integer.parseInt(day));
		rbean.setYi(TextUtils.isEmpty(bean.getYi()) ? "诸事不宜" : bean.getYi());
		rbean.setJi(TextUtils.isEmpty(bean.getJi()) ? "黄道吉日，诸事大吉" : bean
				.getJi());
	}

	@Override
	public void httpSuccess(String result, int responseFlag) {
		// TODO Auto-generated method stub
		try {
			JSONObject object = new JSONObject(result);
			if (responseFlag == 1) {
				setLotter(object);
			} else if (responseFlag == 2) {
				setWeather(object);
			} else if (responseFlag == 3) {
				setXiaoMILayout(object);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void httpTimeOut(int responseFlag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void httpError(int responseFlag) {
		// TODO Auto-generated method stub

	}

	/**
	 * 设置开奖信息
	 * 
	 * @param object
	 */
	private void setLotter(JSONObject object) {
		JSONObject obj = object.optJSONObject("retData");
		JSONArray array = obj.optJSONArray("data");
		if (array != null) {
			for (int i = 0; i < array.length(); i++) {
				JSONObject o = array.optJSONObject(i);
				String expect = o.optString("expect");
				String openCode = o.optString("openCode");
				ssqi.setText("-第" + expect + "期");
				String blueq = openCode.split("[+]")[1];// "+"是正则表达式的符号，所以需要完全匹配"[+]"
				String red[] = (openCode.split("[+]")[0]).split("[,]");
				red1.setText(red[0]);
				red2.setText(red[1]);
				red3.setText(red[2]);
				red4.setText(red[3]);
				red5.setText(red[4]);
				red6.setText(red[5]);
				blue.setText(blueq);

			}
		}
	}

	/**
	 * 设置小米的指数布局
	 * 
	 * @param object
	 */
	private void setXiaoMILayout(JSONObject object) {
		// TODO Auto-generated method stub
		JSONArray array = object.optJSONArray("data");
		if (array != null) {
			List<XiaomiWeather> datas = new ArrayList<XiaomiWeather>();
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.optJSONObject(i);
				String title = obj.optString("title");
				List<XiaomiZhishuList> listZhishu = new ArrayList<XiaomiZhishuList>();
				JSONArray arr = obj.optJSONArray("list");
				if (arr != null) {
					for (int j = 0; j < arr.length(); j++) {
						JSONObject o = arr.optJSONObject(j);
						JSONObject data = o.optJSONObject("data");
						String image = data.optString("image");
						String summary = data.optString("summary");
						String t = data.optString("title");
						String headPic = data.optString("headPic");
						String channelId = data.optString("channelId");
						String indexType = data.optString("indexType");
						XiaomiZhishuList bean = new XiaomiZhishuList(image,
								summary, t, headPic, channelId, indexType);
						listZhishu.add(bean);
					}
				}
				XiaomiWeather weather = new XiaomiWeather(title, listZhishu);
				datas.add(weather);
			}
			XiaoMIWeatherAdapter adapter = new XiaoMIWeatherAdapter(c, datas);
			myListView.setAdapter(adapter);
		}
	}

	/**
	 * 设置天气信息
	 * 
	 * @param object
	 */
	private void setWeather(JSONObject object) {
		// TODO Auto-generated method stub
		JSONArray arr = object.optJSONArray("results");
		if (arr != null && arr.length() > 0) {
			JSONObject obj = arr.optJSONObject(0);
			JSONObject location = obj.optJSONObject("location");
			String city = location.optString("name");
			JSONObject wea = obj.optJSONObject("now");
			String tianqi = wea.optString("text");
			String tem = wea.optString("temperature");
			weather.setText(city + "  " + tianqi + "  " + tem + "℃");

		}
	}

	private void initLocation() {
		// TODO Auto-generated method stub
		// 初始化client
		locationClient = new AMapLocationClient(this.getApplicationContext());
		// 设置定位参数
		locationClient.setLocationOption(getDefaultOption());
		// 设置定位监听
		locationClient.setLocationListener(locationListener);
	}

	/**
	 * 开始定位
	 * 
	 * @since 2.8.0
	 * @author hongming.wang
	 * 
	 */
	private void startLocation() {
		// 根据控件的选择，重新设置定位参数
		// resetOption();
		// 设置定位参数
		locationClient.setLocationOption(locationOption);
		// 启动定位
		locationClient.startLocation();
	}

	/**
	 * 定位监听
	 */
	AMapLocationListener locationListener = new AMapLocationListener() {
		@Override
		public void onLocationChanged(AMapLocation loc) {
			if (null != loc) {
				// 解析定位结果
				stopLocation();
				String result = Utils.getLocationStr(loc);
				if (result.equals("定位失败")) {
					result = "杭州";
				}
				String code = FileUtils.getCityCode(result);
				if (!TextUtils.isEmpty(code)) {
					HttpFactory.getLayout(MainActivity.this, code, 3);
				}
				HttpFactory.getWeather(MainActivity.this, result, 2);
			} else {
				weather.setText("定位失败");
			}
		}
	};

	/**
	 * 默认的定位参数
	 * 
	 * @since 2.8.0
	 * @author hongming.wang
	 * 
	 */
	private AMapLocationClientOption getDefaultOption() {
		AMapLocationClientOption mOption = new AMapLocationClientOption();
		mOption.setLocationMode(AMapLocationMode.Hight_Accuracy);// 可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
		mOption.setGpsFirst(false);// 可选，设置是否gps优先，只在高精度模式下有效。默认关闭
		mOption.setHttpTimeOut(30000);// 可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
		mOption.setInterval(2000);// 可选，设置定位间隔。默认为2秒
		mOption.setNeedAddress(true);// 可选，设置是否返回逆地理地址信息。默认是ture
		mOption.setOnceLocation(false);// 可选，设置是否单次定位。默认是false
		mOption.setOnceLocationLatest(false);// 可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
		AMapLocationClientOption.setLocationProtocol(AMapLocationProtocol.HTTP);// 可选，
																				// 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
		return mOption;
	}

	/**
	 * 停止定位
	 * 
	 * @since 2.8.0
	 * @author hongming.wang
	 * 
	 */
	private void stopLocation() {
		// 停止定位
		locationClient.stopLocation();
	}

	/**
	 * 销毁定位
	 * 
	 * @since 2.8.0
	 * @author hongming.wang
	 * 
	 */
	private void destroyLocation() {
		if (null != locationClient) {
			/**
			 * 如果AMapLocationClient是在当前Activity实例化的，
			 * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
			 */
			locationClient.onDestroy();
			locationClient = null;
			locationOption = null;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		destroyLocation();
	}
}
