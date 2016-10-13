package cn.ltwc.cft.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import cn.ltwc.cft.activity.ShareActivity;
import cn.ltwc.cft.beans.RiqiBean;
import cn.ltwc.cft.beans.YiJiBean;
import cn.ltwc.cft.data.Constant;
import cn.ltwc.cft.data.LunarCalendar;
import cn.ltwc.cft.data.SpecialCalendar;
import cn.ltwc.cft.db.HuangLi;

/**
 * 
 * TODO: 本APP的一些方法的
 * 
 * @author huangshang 2016-7-2 下午11:16:52
 * @Modified_By:
 */
public class HLUtil {

	/**
	 * 通过农历日期查询是否是节假日，是就返回该节日
	 * 
	 * @param nHolidayDay
	 * @return
	 */
	public static String getHoliday(String nHolidayDay) {
		String holiday = "";
		for (int i = 0; i < Constant.holiday.length; i++) {
			if (nHolidayDay.indexOf(Constant.holiday[i]) != -1
					|| Constant.holiday[i].indexOf(nHolidayDay) != -1) {
				holiday = Constant.holiday[i];
				return holiday;
			}

		}

		return holiday;
	}

	/**
	 * 判断农历该日期是否是放假的节假日
	 * 
	 * @param nHolidayDay
	 * @return true为放假，false不放假
	 */
	public static boolean isFangJia(String nHolidayDay) {
		boolean is = false;

		for (int i = 0; i < Constant.FANGJIAHOLIDAY.length; i++) {
			if (nHolidayDay.indexOf(Constant.FANGJIAHOLIDAY[i]) != -1
					|| Constant.FANGJIAHOLIDAY[i].indexOf(nHolidayDay) != -1) {
				is = true;
				return is;
			}
		}
		return is;
	}

	/**
	 * 返回星期几
	 * 
	 * @param temp
	 * @return
	 */
	public static String getWeek(int temp) {
		String week = "";
		switch (temp) {
		case 0:
			week = "星期日";
			break;
		case 1:
			week = "星期一";
			break;
		case 2:
			week = "星期二";
			break;
		case 3:
			week = "星期三";
			break;
		case 4:
			week = "星期四";
			break;
		case 5:
			week = "星期五";
			break;
		case 6:
			week = "星期六";
			break;
		}
		return week;
	}

	public static String setENMonth(int month) {
		String m = "";
		for (int i = 0; i < Constant.YUEEN.length; i++) {
			if (i == month - 1) {
				m = Constant.YUEEN[i];
				return m;
			}
		}
		return m;
	}

	/**
	 * 滑动到下一天
	 * 
	 * @param bean
	 * @return
	 */
	public static RiqiBean nextDay(RiqiBean bean) {
		int year = bean.getYear();
		int month = bean.getyMonth();
		int day = bean.getyDay();
		int totalDay = getDayOfMonth(year, month);
		if (day < totalDay) {
			day++;
		} else {
			day = 1;
			if (month < 12) {
				month++;
				totalDay = getDayOfMonth(year, month);
			} else {
				month = 1;
				year++;
				totalDay = getDayOfMonth(year, month);
			}

		}

		RiqiBean newBean = setRiQIBean(year, month, day);
		return newBean;

	}

	/**
	 * 滑动到上一天
	 * 
	 * @param bean
	 * @return
	 */
	public static RiqiBean preDay(RiqiBean bean) {
		int year = bean.getYear();
		int month = bean.getyMonth();
		int day = bean.getyDay();
		int totalDay = getDayOfMonth(year, month);
		if (day > 1) {
			day--;
		} else {
			if (month > 1) {
				month--;
				totalDay = getDayOfMonth(year, month);
				day = totalDay;
			} else {
				year--;
				month = 12;
				totalDay = getDayOfMonth(year, month);
				day = totalDay;
			}
		}

		RiqiBean newBean = setRiQIBean(year, month, day);

		return newBean;

	}

	/**
	 * 获取当前月份的天数
	 * 
	 * @param bean
	 * @return
	 */
	private static int getDayOfMonth(int year, int month) {
		boolean isLeapyear;
		int totalDay;
		isLeapyear = SpecialCalendar.getInstance().isLeapYear(year);
		totalDay = SpecialCalendar.getInstance().getDaysOfMonth(isLeapyear,
				month);
		return totalDay;
	}

	private static RiqiBean setRiQIBean(int year, int month, int day) {
		RiqiBean newBean = new RiqiBean();
		newBean = LunarCalendar.getInstance().getRiqiBeanInfo(year, month, day);
		YiJiBean yibean = HuangLi.getInstance().quearHuangli(year + "",
				month + "", day + "");
		newBean.setYi(TextUtils.isEmpty(yibean.getYi()) ? "诸事不宜" : yibean
				.getYi());
		newBean.setJi(TextUtils.isEmpty(yibean.getJi()) ? "黄道吉日，诸事大吉" : yibean
				.getJi());
		return newBean;
	}

	/**
	 * 调用安卓系统自带的分享到第三方APP
	 * 
	 * @param context
	 * @param activityTitle
	 * @param msgTitle
	 * @param msgText
	 * @param imgPath
	 */
	public static void shareMsg(Context context, String activityTitle,
			String msgTitle, String msgText, String imgPath) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		if (imgPath == null || imgPath.equals("")) {
			intent.setType("text/plain"); // 纯文本
		} else {
			File f = new File(imgPath);
			if (f != null && f.exists() && f.isFile()) {
				intent.setType("image/png");
				Uri u = Uri.fromFile(f);
				intent.putExtra(Intent.EXTRA_STREAM, u);
			}
		}
		// 标题需要第三方APP支持才行（QQ、微信都不支持）
		intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
		intent.putExtra(Intent.EXTRA_TEXT, msgText);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(Intent.createChooser(intent, activityTitle));
	}

	/**
	 * 跳转到分享界面
	 * 
	 * @param context
	 * @param type
	 *            分享的类型
	 * @param msgText
	 *            分享文字内容
	 * @param imgPath
	 *            分享图片的地址
	 */
	public static void toMyShare(Context context, String type, String msgText,
			String imgPath) {
		Intent intent = new Intent(context, ShareActivity.class);
		intent.putExtra(Constant.TYPE, type);
		intent.putExtra(Constant.SHARE_TYPE_TEXT, msgText);
		intent.putExtra(Constant.SHARE_TYPE_IMG, imgPath);
		context.startActivity(intent);

	}

	/**
	 * 获取到可分享的应用列表
	 * 
	 * @param context
	 * @return
	 */
	public static List<ResolveInfo> getShareApps(Context context, String type) {
		List<ResolveInfo> mApps = new ArrayList<ResolveInfo>();
		Intent intent = new Intent(Intent.ACTION_SEND, null);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setType(type);
		PackageManager pManager = context.getPackageManager();
		mApps = pManager.queryIntentActivities(intent,
				PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
		return mApps;
	}

}
