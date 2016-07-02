package cn.ltwc.cft.activity;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import cn.ltwc.cft.R;
import cn.ltwc.cft.beans.RiqiBean;
import cn.ltwc.cft.data.Constant;
import cn.ltwc.cft.utils.HLUtil;
import cn.ltwc.cft.view.TitleView;

public class DayDetailActivity extends BaseActivity {
	private RiqiBean bean;
	private boolean is;// 是否放假
	private TextView year, month, monthEN, day;// 阳历年月日
	private TextView nMonth, nDay, week, nHoliday;
	private View nianDivider, nongliDivider;// 两个分割线
	private View nongli;// 农历显示栏
	private TextView yi, ji;
	private TitleView title;

	public DayDetailActivity() {
		super(R.layout.activity_day_detail);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		yi = (TextView) findViewById(R.id.yi);
		ji = (TextView) findViewById(R.id.ji);
		year = (TextView) findViewById(R.id.day_detail_year);
		month = (TextView) findViewById(R.id.day_detail_month);
		monthEN = (TextView) findViewById(R.id.day_detail_month_en);
		day = (TextView) findViewById(R.id.day_detail_day);
		nMonth = (TextView) findViewById(R.id.day_detail_nongli_yue);
		nDay = (TextView) findViewById(R.id.day_detail_nongli_day);
		week = (TextView) findViewById(R.id.day_detail_week);
		nHoliday = (TextView) findViewById(R.id.day_detail_holiday);
		nianDivider = findViewById(R.id.day_detail_nian_fenge);
		nongliDivider = findViewById(R.id.day_detail_nongli_fenge);
		nongli = findViewById(R.id.day_detail_nongli);
		title = (TitleView) findViewById(R.id.day_detail_title);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		bean = getIntent().getParcelableExtra(Constant.RILIINFO);
		is = HLUtil.isFangJia(bean.getnHolidayDay());
	}

	@Override
	public void bindView() {
		// TODO Auto-generated method stub
		title.setTitletext(bean.getYear() + "年" + bean.getyMonth() + "月"
				+ bean.getyDay() + "日" + HLUtil.getWeek(bean.getWeek()));
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
			monthEN.setTextColor(this.getResources().getColor(R.color.main_color));
			nMonth.setTextColor(this.getResources().getColor(R.color.main_color));
			nDay.setTextColor(this.getResources().getColor(R.color.main_color));
			week.setTextColor(this.getResources().getColor(R.color.main_color));
			nHoliday.setTextColor(this.getResources().getColor(R.color.main_color));
			nianDivider.setBackgroundColor(this.getResources().getColor(
					R.color.main_color));
			nongliDivider.setBackgroundColor(this.getResources().getColor(
					R.color.main_color));
			nongli.setBackgroundResource(R.drawable.day_detail_nongli_balck_bg);
		}

	}

}
