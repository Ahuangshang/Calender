package cn.ltwc.cft.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;
import cn.ltwc.cft.R;
import cn.ltwc.cft.beans.RiqiBean;
import cn.ltwc.cft.data.Constant;
import cn.ltwc.cft.myinterface.ScrollViewListener;
import cn.ltwc.cft.utils.HLUtil;
import cn.ltwc.cft.view.MyScrollView;
import cn.ltwc.cft.view.TitleView;

public class DayDetailActivity extends BaseActivity implements OnClickListener,
		ScrollViewListener {
	private RiqiBean bean;
	private boolean is;// 是否放假
	private TextView year, month, monthEN, day;// 阳历年月日
	private TextView nMonth, nDay, week, nHoliday;
	private View nianDivider, nongliDivider;// 两个分割线
	private View nongli;// 农历显示栏
	private TextView yi, ji;
	private TitleView title;
	private ViewFlipper vf;
	private GestureDetector gestureDetector = null;
	private View showView;// 显示的界面
	private MyScrollView myScrollView;
	private View yun, meng, yuan, bazi;

	public DayDetailActivity() {
		super(R.layout.activity_day_detail);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		vf = (ViewFlipper) findViewById(R.id.day_detail_vf);
		title = (TitleView) findViewById(R.id.day_detail_title);
		vf.removeAllViews();
		addView();
		vf.addView(showView, 0);
		myScrollView = (MyScrollView) findViewById(R.id.day_detail_scrollView);

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		bean = getIntent().getParcelableExtra(Constant.RILIINFO);
		gestureDetector = new GestureDetector(c, new MyGestureListener());
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public void bindView() {
		// TODO Auto-generated method stub
		setTitle();
		myScrollView.setScrollViewListener(this);
		myScrollView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return DayDetailActivity.this.gestureDetector
						.onTouchEvent(event);
			}
		});

	}

	private void setTitle() {
		title.setTitletext(bean.getYear() + "年" + bean.getyMonth() + "月"
				+ bean.getyDay() + "日" + HLUtil.getWeek(bean.getWeek()));
	}

	@SuppressLint({ "ClickableViewAccessibility", "InflateParams" })
	private void addView() {
		showView = LayoutInflater.from(c).inflate(R.layout.day_detail_layout,
				null);
		viewInitView();
		viewBindView();
		showView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return DayDetailActivity.this.gestureDetector
						.onTouchEvent(event);

			}
		});
		showView.setOnClickListener(this);
	}

	private void viewInitView() {
		// TODO Auto-generated method stub
		yi = (TextView) showView.findViewById(R.id.yi);
		ji = (TextView) showView.findViewById(R.id.ji);
		year = (TextView) showView.findViewById(R.id.day_detail_year);
		month = (TextView) showView.findViewById(R.id.day_detail_month);
		monthEN = (TextView) showView.findViewById(R.id.day_detail_month_en);
		day = (TextView) showView.findViewById(R.id.day_detail_day);
		nMonth = (TextView) showView.findViewById(R.id.day_detail_nongli_yue);
		nDay = (TextView) showView.findViewById(R.id.day_detail_nongli_day);
		week = (TextView) showView.findViewById(R.id.day_detail_week);
		nHoliday = (TextView) showView.findViewById(R.id.day_detail_holiday);
		nianDivider = showView.findViewById(R.id.day_detail_nian_fenge);
		nongliDivider = showView.findViewById(R.id.day_detail_nongli_fenge);
		nongli = showView.findViewById(R.id.day_detail_nongli);
		yun = showView.findViewById(R.id.yun);
		meng = showView.findViewById(R.id.meng);
		yuan = showView.findViewById(R.id.yuan);
		bazi = showView.findViewById(R.id.bazi);
	}

	private void viewBindView() {
		// TODO Auto-generated method stub
		yun.setOnClickListener(this);
		meng.setOnClickListener(this);
		yuan.setOnClickListener(this);
		bazi.setOnClickListener(this);
		setBg();
		yi.setText(bean.getYi());
		ji.setText(bean.getJi());
		year.setText(bean.getYear() + "");
		month.setText(bean.getyMonth() + "月");
		day.setText(bean.getyDay() + "");
		monthEN.setText(HLUtil.setENMonth(bean.getyMonth()));
		nMonth.setText(bean.getnYear() + bean.getnAnimal() + "年"
				+ bean.getnMonth());
		nDay.setText(bean.getnDay());
		week.setText(HLUtil.getWeek(bean.getWeek()));
		nHoliday.setText(HLUtil.getHoliday(bean.getnHolidayDay()));
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("ResourceAsColor")
	private void setBg() {
		if (TextUtils.isEmpty(HLUtil.getHoliday(bean.getnHolidayDay()))) {
			nHoliday.setVisibility(View.GONE);
		} else {
			nHoliday.setVisibility(View.VISIBLE);
		}
		is = HLUtil.isFangJia(bean.getnHolidayDay());
		if (bean.getWeek() == 0 || bean.getWeek() == 6) {
			is = true;
		}
		if (is) {
			year.setTextColor(this.getResources().getColor(R.color.red));
			month.setTextColor(this.getResources().getColor(R.color.red));
			day.setTextColor(this.getResources().getColor(R.color.red));
			monthEN.setTextColor(this.getResources().getColor(R.color.red));
			nMonth.setTextColor(this.getResources().getColor(R.color.red));
			nDay.setTextColor(this.getResources().getColor(R.color.red));
			week.setTextColor(this.getResources().getColor(R.color.red));
			nHoliday.setTextColor(this.getResources().getColor(R.color.red));
			nianDivider.setBackgroundColor(this.getResources().getColor(
					R.color.red));
			nongliDivider.setBackgroundColor(this.getResources().getColor(
					R.color.red));
			nongli.setBackgroundResource(R.drawable.day_detail_nongli_red_bg);
		} else {
			year.setTextColor(this.getResources().getColor(R.color.main_color));
			month.setTextColor(this.getResources().getColor(R.color.main_color));
			day.setTextColor(this.getResources().getColor(R.color.main_color));
			monthEN.setTextColor(this.getResources().getColor(
					R.color.main_color));
			nMonth.setTextColor(this.getResources()
					.getColor(R.color.main_color));
			nDay.setTextColor(this.getResources().getColor(R.color.main_color));
			week.setTextColor(this.getResources().getColor(R.color.main_color));
			nHoliday.setTextColor(this.getResources().getColor(
					R.color.main_color));
			nianDivider.setBackgroundColor(this.getResources().getColor(
					R.color.main_color));
			nongliDivider.setBackgroundColor(this.getResources().getColor(
					R.color.main_color));
			nongli.setBackgroundResource(R.drawable.day_detail_nongli_balck_bg);
		}

	}

	private class MyGestureListener extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			int gvFlag = 0; // 每次添加gridView到viewFlipper中时给的标记
			if (e1.getX() - e2.getX() > 120
					&& (Math.abs(e1.getY() - e2.getY()) < 200)) {
				// 像右滑动
				enterNextDay(gvFlag);
				return true;
			} else if (e1.getX() - e2.getX() < -120
					&& (Math.abs(e1.getY() - e2.getY()) < 200)) {
				// 向左滑动
				enterPrevDay(gvFlag);
				return true;
			}
			return false;
		}

	}

	/**
	 * 进入下一天
	 * 
	 * @param gvFlag
	 */
	private void enterNextDay(int gvFlag) {
		// TODO Auto-generated method stub
		bean = HLUtil.nextDay(bean);
		setTitle();
		addView();
		flushView(gvFlag, AnimationUtils.loadAnimation(c, R.anim.push_left_in),
				AnimationUtils.loadAnimation(c, R.anim.push_left_out));
	}

	/**
	 * 进入上一天
	 * 
	 * @param gvFlag
	 */
	private void enterPrevDay(int gvFlag) {
		// TODO Auto-generated method stub
		bean = HLUtil.preDay(bean);
		setTitle();
		addView();
		flushView(gvFlag,
				AnimationUtils.loadAnimation(c, R.anim.push_right_in),
				AnimationUtils.loadAnimation(c, R.anim.push_right_out));
	}

	private void flushView(int gvFlag, Animation inAnimation,
			Animation outAnimation) {
		gvFlag++;
		vf.addView(showView, gvFlag);
		vf.setInAnimation(inAnimation);
		vf.setOutAnimation(outAnimation);
		vf.showNext();
		vf.removeViewAt(0);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.yun:
			jumpToWeb("http://m.linghit.com/Tools/measurecal/id/26", "黄历");
			break;
		case R.id.meng:
			jumpToWeb("http://m.linghit.com/Tools/jiemeng/", "黄历");
			break;
		case R.id.yuan:
			jumpToWeb("http://m.linghit.com/Tools/measurecal/id/40", "黄历");
			break;
		case R.id.bazi:
			jumpToWeb("http://zxcs.linghit.com/Divination", "黄历");
			// jumpToWeb("http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_mainpage",
			// "黄历");
			break;
		}

	}

	private void jumpToWeb(String url, String title) {
		// Intent intent = new Intent(DayDetailActivity.this,
		// MyWebViewActivity.class);
		Intent intent = new Intent(DayDetailActivity.this, MyX5WebView.class);
		intent.putExtra(Constant.WEBURL, url);
		intent.putExtra(Constant.WEBTITLE, title);
		startActivity(intent);
	}

	@Override
	public void onScrollChanged(MyScrollView scrollView, int x, int y,
			int oldx, int oldy) {
		// TODO Auto-generated method stub
		if (y - oldy > 200 || y - oldy < -200) {
			myScrollView.scrollTo(x, y);
		}
	}

}
