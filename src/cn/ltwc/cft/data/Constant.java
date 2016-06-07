package cn.ltwc.cft.data;

/**
 * 
 * TODO:本应用的常量类
 * 
 * @author huangshang 2015-11-10 下午2:22:42
 * @Modified_By:
 */
public class Constant {
	public static long exitTime = 2000;// 程序退出时间
	// 农历部分假日
	public final static String[] lunarHoliday = new String[] { "0101 春节", "0115 元宵节", "0504 皇上生辰", "0505 端午",
			"0707 情人节", "0715 中元节", "0815 中秋节", "0909 重阳节", "1208 腊八", "1224 小年", "1230 除夕" };
	// 公历部分节假日
	public final static String[] solarHoliday = new String[] { "0101 元旦", "0214 情人节", "0308 妇女节", "0312 植树节",
			"0401 愚人节", "0501 劳动节", "0504 青年节", "0512 护士节", "0601 儿童节", "0701 建党节", "0801 建军节", "0903 抗战胜利", "0910 教师节",
			"0918 九一八", "0928 孔子诞辰", "1001 国庆节", "1006 老人", "1024 联合国日", "1213 国家公祭", "1220 澳门回归", "1225 圣诞", };
	// 公历放假的日子
	public final static String[] solarFajia = new String[] { "0101 元旦", "0501 劳动节", "0601 儿童节", "1001 国庆节" };

	// 农历放假的日子
	public final static String[] lunarFajia = new String[] { "1230 除夕", "0101 春节", "0115 元宵节", "0504 皇上生辰", "0505 端午",
			"0815 中秋节", "1208 腊八", "1224 小年",

	};
	public static final String NOTE_BEAN = "note_bean";
	public static final String FLAG = "flag";
}
