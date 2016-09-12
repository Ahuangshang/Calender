package cn.ltwc.cft;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

/**
 * 
 * TODO:Activity的管理类
 * 
 * @author huangshang 2015-11-15 下午1:37:23
 * @Modified_By:
 */
public class AppManager {
	private static AppManager instance = new AppManager();
	private Stack<Activity> allActivity;// 用于保存打开的Activity

	/**
	 * 私有的构造方法
	 */
	private AppManager() {
		allActivity = new Stack<Activity>();
	}

	/**
	 * 单例
	 * 
	 * @return instance本类的唯一操作对象
	 */
	public static AppManager getInstance() {
		return instance;
	}

	/**
	 * 保存打开的Activity
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		allActivity.add(activity);
	}

	/**
	 * 程序退出
	 */

	@SuppressWarnings("deprecation")
	public void appExit(Context context) {
		// 1.关闭所有Activity
		for (Activity activity : allActivity) {
			if (activity != null) {
				activity.finish();
			}
		}
		// 清空集合
		allActivity.clear();
		// 2.重启包
		ActivityManager manager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
		manager.restartPackage(context.getPackageName());
		// 2.系统退出
		System.exit(0);
	}
}
