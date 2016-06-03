// package cn.ltwc.cft.activity;
//
// import android.app.Activity;
// import android.os.Bundle;
// import android.view.GestureDetector;
// import android.view.MotionEvent;
// import android.view.View;
// import android.widget.ImageView;
// import android.widget.Toast;
//
//
//
// import java.util.ArrayList;
//
// import cn.ltwc.cft.R;
// import cn.ltwc.cft.datapick.GrallyAdapter;
// import cn.ltwc.cft.view.MyGallery;
// import cn.ltwc.cft.view.MyImageView;
//
/// **
// * 点击图片后可以放大查看
// * Created by LZL on 15/12/18.
// */
// public class ChooseImgForShow extends Activity {
// MyGallery g;
// ArrayList<String> listPath;
// int position;
//
// @Override
// protected void onCreate(Bundle savedInstanceState) {
// super.onCreate(savedInstanceState);
// setContentView(R.layout.activity_choose_img_show);
// initData();
// initView();
// bindView();
//
//
// }
//
// private void bindView() {
// g.setVerticalFadingEdgeEnabled(false);// 取消竖直渐变边框
// g.setHorizontalFadingEdgeEnabled(false);// 取消水平渐变边框
// g.setDetector(new GestureDetector(this,
// new MySimpleGesture()));
// GrallyAdapter adapter = new GrallyAdapter(this, listPath);
// g.setAdapter(adapter);
// g.setSelection(position);
//
// }
//
// private void initView() {
// g = (MyGallery) findViewById(R.id.gallery);
// }
//
// private void initData() {
// listPath = getIntent().getStringArrayListExtra(Const.GOTO_FOR_SHOW_IMG);
// position = getIntent().getIntExtra(Const.GOTO_FOR_SHOW_IMG_POSITION, 0);
// if (listPath.get(0).equals(Const.PHOTO)) {
// listPath.remove(0);
// position -= 1;
// }
// }
//
//
// private class MySimpleGesture extends GestureDetector.SimpleOnGestureListener
// {
// // 按两下的第二下Touch down时触发
// public boolean onDoubleTap(MotionEvent e) {
// View view = g.getSelectedView();
// if (view instanceof MyImageView) {
// MyImageView imageView = (MyImageView) view;
// if (imageView.getScale() > imageView.getMiniZoom()) {
// imageView.zoomTo(imageView.getMiniZoom());
// } else {
// imageView.zoomTo(imageView.getMaxZoom());
// }
// } else {
// }
// return false;
// }
// @Override
// public boolean onSingleTapConfirmed(MotionEvent e) {
// finish();
// return false;
// }
// }
// }
