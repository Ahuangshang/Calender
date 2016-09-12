package cn.ltwc.cft.myinterface;

import cn.ltwc.cft.view.MyScrollView;

public interface ScrollViewListener {
	void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy);
}
