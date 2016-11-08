package cn.ltwc.cft.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import cn.ltwc.cft.MyApplication;
import cn.ltwc.cft.beans.CityCodeBean;

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

	/**
	 * 读取目录下的城市信息
	 * 
	 * @param fileName
	 * @return
	 */
	private static List<CityCodeBean> readCity() {
		String fileName = "city.txt";
		List<CityCodeBean> cities = new ArrayList<CityCodeBean>();
		InputStreamReader inputReader = null;
		try {
			inputReader = new InputStreamReader(MyApplication.getInstance()
					.getAssets().open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			while ((line = bufReader.readLine()) != null) {
				String city = line.substring(0, line.indexOf(":"));
				String code = line.substring(line.indexOf(":") + 1,
						line.length());
				CityCodeBean bean = new CityCodeBean(city, code);
				cities.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputReader != null) {
				try {
					inputReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return cities;
	}

	/**
	 * 获取城市的code
	 * 
	 * @param city
	 * @return
	 */
	public static String getCityCode(String city) {
		if (city.endsWith("市")) {
			city = city.substring(0, city.length() - 1);
		}
		String code = "";
		List<CityCodeBean> cities = readCity();
		for (int i = 0; i < cities.size(); i++) {
			if (city.equals(cities.get(i).getCity())) {
				code = cities.get(i).getCode();
				return code;
			}
		}
		if (TextUtils.isEmpty(code)) {
			for (int i = 0; i < cities.size(); i++) {
				if (city.contains(cities.get(i).getCity())) {
					code = cities.get(i).getCode();
					return code;
				}
			}
		}

		return code;
	}
    public static Intent getFileIntent(File file){  
//      Uri uri = Uri.parse("http://m.ql18.com.cn/hpf10/1.pdf");  
       Uri uri = Uri.fromFile(file);  
       String type = getMIMEType(file);  
       Log.i("AA", "type="+type);  
       Intent intent = new Intent("android.intent.action.VIEW");  
       intent.addCategory("android.intent.category.DEFAULT");  
       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
       intent.setDataAndType(uri, type);  
       return intent;  
     }  
      
   public static void writeToSDCard(String fileName,InputStream input){  
         
       if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){  
           File directory=Environment.getExternalStorageDirectory();  
           File file=new File(directory,fileName);  
//         if(file.exists()){  
//             Log.i("tag", "The file has already exists.");  
//             return;  
//         }  
           try {  
               FileOutputStream fos = new FileOutputStream(file);  
               byte[] b = new byte[2048];  
               int j = 0;  
               while ((j = input.read(b)) != -1) {  
                   fos.write(b, 0, j);  
               }  
               fos.flush();  
               fos.close();  
           } catch (FileNotFoundException e) {  
               // TODO Auto-generated catch block  
               e.printStackTrace();  
           } catch (IOException e) {  
               // TODO Auto-generated catch block  
               e.printStackTrace();  
           }  
       }else{  
           Log.i("AA", "NO SDCard.");  
       }  
   }  
     
   private static String getMIMEType(File f){     
     String type="";    
     String fName=f.getName();    
     /* 取得扩展名 */    
     String end=fName.substring(fName.lastIndexOf(".")+1,fName.length()).toLowerCase();  
       
     /* 依扩展名的类型决定MimeType */  
     if(end.equals("pdf")){  
         type = "application/pdf";//  
     }  
     else if(end.equals("m4a")||end.equals("mp3")||end.equals("mid")||    
     end.equals("xmf")||end.equals("ogg")||end.equals("wav")){    
       type = "audio/*";     
     }    
     else if(end.equals("3gp")||end.equals("mp4")){    
       type = "video/*";    
     }    
     else if(end.equals("jpg")||end.equals("gif")||end.equals("png")||    
     end.equals("jpeg")||end.equals("bmp")){    
       type = "image/*";    
     }    
     else if(end.equals("apk")){     
       /* android.permission.INSTALL_PACKAGES */     
       type = "application/vnd.android.package-archive";   
     }  
//     else if(end.equals("pptx")||end.equals("ppt")){  
//       type = "application/vnd.ms-powerpoint";   
//     }else if(end.equals("docx")||end.equals("doc")){  
//       type = "application/vnd.ms-word";  
//     }else if(end.equals("xlsx")||end.equals("xls")){  
//       type = "application/vnd.ms-excel";  
//     }  
     else{  
//       /*如果无法直接打开，就跳出软件列表给用户选择 */    
       type="*/*";  
     }  
     return type;  
   }     
}
