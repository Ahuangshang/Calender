package cn.ltwc.cft.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.text.TextUtils;
import cn.ltwc.cft.MyApplication;

public class FileUtils {
	/**
	 * 判断文件是否存在
	 * 
	 * @param path
	 *            文件的路径
	 * @return
	 */
	public static boolean isExit(String path) {
		if (TextUtils.isEmpty(path)) {
			return false;
		} else {
			File file = new File(path);
			if (file.exists() && file.length() > 0) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 将Assets目录中的文件拷贝到sdcard中
	 * 
	 * @param assetsPath
	 *            文件在assets目录中的位置
	 * @param sdcardPath
	 *            文件在sdcard目录中的位置
	 */
	public static void copyAssetsToSdcard(String assetsPath, String sdcardPath) {
		InputStream in = null;
		OutputStream os = null;
		try {
			// 获取city.db在assets中的输入流
			in = MyApplication.getInstance().getAssets().open(assetsPath);

			// 获取city.db需要在SDCARD中存放的输出流
			os = new FileOutputStream(sdcardPath);

			// 定义缓冲器
			byte[] buffer = new byte[1024];

			// 当前读取数据的长度
			int len = 0;

			// 读取输入流到数组中
			while ((len = in.read(buffer)) != -1) {
				os.write(buffer, 0, len);
			}

			// 提交缓冲区文件
			os.flush();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			// 关闭流
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 创建缓存文件夹
	 * 
	 * @param context
	 * @return
	 */
	public static String buildCache(Context context) {
		String cachePath = "";
		if (context.getExternalCacheDir() != null) {
			cachePath = context.getExternalCacheDir().toString();
		} else if (context.getCacheDir() != null) {
			cachePath = context.getCacheDir().toString();
		}
		return cachePath;
	}

}
