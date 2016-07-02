package cn.ltwc.cft.utils;

import cn.ltwc.cft.data.Constant;

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

}
