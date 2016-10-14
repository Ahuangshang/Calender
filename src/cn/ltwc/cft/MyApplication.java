package cn.ltwc.cft;

import java.io.File;

import android.app.Application;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

/**
 * 
 * TODO:本应用的全局变量管理类
 * 
 * @author huangshang 2015-11-10 下午2:22:09
 * @Modified_By:
 */
public class MyApplication extends Application {
	private static MyApplication instance;
	// ImageLoader相关
	private ImageLoaderConfiguration imageLoaderConfiguration;

	/**
	 * 得到ImageLoaderConfiguration的对象
	 * 
	 * @return
	 */
	public ImageLoaderConfiguration getImageLoaderConfiguration() {

		return imageLoaderConfiguration;
	}

	/**
	 * 返回本类的操作对象
	 * 
	 * @return
	 */
	public static MyApplication getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance = this;
		// 搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
		//TbsDownloader.needDownload(getApplicationContext(), false);
		QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

			@Override
			public void onViewInitFinished(boolean arg0) {
				// TODO Auto-generated method stub
				Log.e("AA", " onViewInitFinished is " + arg0);
			}

			@Override
			public void onCoreInitFinished() {
				// TODO Auto-generated method stub

			}
		};
		QbSdk.setTbsListener(new TbsListener() {
			@Override
			public void onDownloadFinish(int i) {
				Log.d("AA", "onDownloadFinish");
			}

			@Override
			public void onInstallFinish(int i) {
				Log.d("AA", "onInstallFinish");
			}

			@Override
			public void onDownloadProgress(int i) {
				Log.d("AA", "onDownloadProgress:" + i);
			}
		});

		QbSdk.initX5Environment(getApplicationContext(), cb);
		initImageLoaderConfiguration();
	}

	/**
	 * 配制ImageLoaderConfiguration(主要是配制图片的缓存【是否缓存到内存卡和内存】)
	 */
	private void initImageLoaderConfiguration() {
		// 配制文件被缓存的路径
		File cacheDir = StorageUtils.getOwnCacheDirectory(
				instance.getApplicationContext(), "/CalenderForTang/"
						+ "imgCache");

		imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(
				instance)
				.threadPoolSize(3)
				// 线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				// 设置优先级
				.memoryCacheSize(80 * 1024 * 1024)
				// 缓存到内存中的大小
				.discCacheSize(150 * 1024 * 1024)
				// 缓存到内存卡中的大小
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// 将保存的时候的URI名称用MD5
				.discCacheFileCount(500)
				// 缓存的文件数量
				.discCache(new UnlimitedDiscCache(cacheDir))
				// 自定义缓存路径
				.imageDownloader(
						new BaseImageDownloader(instance, 5 * 1000, 30 * 1000)) // connectTimeout
				// (5s)超时时间
				// .writeDebugLogs() // Remove for release app
				.build();// 开始构建

		// Log.d(TAG, "图片缓存在sd卡中的路径：" + cacheDir.getPath());
	}
}
