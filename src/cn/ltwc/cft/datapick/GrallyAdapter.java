// package cn.ltwc.cft.datapick;
//
// import java.util.List;
//
// import android.content.Context;
// import android.view.View;
// import android.view.ViewGroup;
// import android.view.ViewGroup.LayoutParams;
// import android.widget.BaseAdapter;
// import android.widget.Gallery;
// import cn.ltwc.cft.view.MyImageView;
//
// import com.bumptech.glide.Glide;
//
//
//
// public class GrallyAdapter extends BaseAdapter {
// private Context c;
// private List<String> pathList;
//
// public GrallyAdapter(Context c, List<String> pathList) {
// super();
// this.c = c;
// this.pathList = pathList;
// }
//
// @Override
// public int getCount() {
// // TODO Auto-generated method stub
// return pathList.size();
// }
//
// @Override
// public Object getItem(int position) {
// // TODO Auto-generated method stub
// return pathList.get(position);
// }
//
// @Override
// public long getItemId(int position) {
// // TODO Auto-generated method stub
// return position;
// }
//
// @SuppressWarnings("deprecation")
// @Override
// public View getView(int position, View convertView, ViewGroup parent) {
// MyImageView view = new MyImageView(c);
// view.setLayoutParams(new
// Gallery.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
// Glide.with(c).load(pathList.get(position)).asBitmap().fitCenter().into(view);
// return view;
// }
// }
