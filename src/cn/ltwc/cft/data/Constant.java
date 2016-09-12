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
			"0707 乞巧节", "0715 中元节", "0815 中秋节", "0909 重阳节", "1208 腊八", "1224 小年", "1230 除夕" };
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
	// 所有的节日和二十四节气
	public final static String[] holiday = new String[] { "春节", "元宵节", "皇上生辰", "端午", "乞巧节", "中元节", "中秋节", "重阳节", "腊八",
			"小年", "除夕", "元旦", "情人节", "妇女节", "植树节", "愚人节", "劳动节", "青年节", "护士节", "儿童节", "建党节、香港回归纪念日", "建军节", "抗战胜利纪念日",
			"教师节", "九一八事变", "孔子诞辰", "国庆节", "老人", "联合国日", "南京大屠杀国家公祭日", "澳门回归纪念日", "圣诞", "大寒", "雨水", "春分", "谷雨", "小满",
			"夏至", "大暑", "处暑", "秋分", "霜降", "小雪", "冬至", "小寒", "立春", "惊蛰", "清明", "立夏", "芒种", "小暑", "立秋", "白露", "寒露", "立冬",
			"大雪" };
	// 所有放假的节日
	public final static String[] FANGJIAHOLIDAY = new String[] { "春节", "皇上生辰", "端午", "中秋节", "除夕", "元旦", "妇女节", "劳动节",
			"儿童节", "国庆节", };

	public final static String[] YUEEN = new String[] { "Jan.", "Feb.", "Mar.", "Apr.", "May.", "Jun.", "Jul.", "Aug.",
			"Sept.", "Oct.", "Nov.", "Dec." };

	public static final String NOTE_BEAN = "note_bean";
	public static final String FLAG = "flag";
	public static final String RILIINFO = "rili_info";
	public static final String APPPACKAGENAME = "cn.ltwc.cft";
	public static final String WEBURL = "web_url";
	public static final String WEBTITLE = "web_title";
	public static final String IMGURL_LIST = "imgurl_list";
	public static final String POSITION = "position";
	public static final String SHARE_TYPE_TEXT = "text/plain";
	public static final String SHARE_TYPE_IMG = "image/*";
	public static final String SHARE_TYPE_AUDIO = "audio/*";
	public static final String TYPE = "type";
	public static final String SHARE_MESSAGE = "share_msg";
	public static final String SHARE_IMG = "share_img";
}
