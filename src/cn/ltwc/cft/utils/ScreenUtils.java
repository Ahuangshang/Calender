package cn.ltwc.cft.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class ScreenUtils {

	public static int getScreenHeight(Context mContext) {
		Resources resources = mContext.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		return dm.heightPixels;
	}

	public static int getScreenWith(Context mContext) {
		Resources resources = mContext.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		return dm.widthPixels;
	}
}
