package cn.ltwc.cft.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.ltwc.cft.R;

/**
 * 
 * TODO:标题栏
 * 
 * @author huangshang 2015-11-15 下午10:44:13
 * @Modified_By:
 */
public class TitleView extends LinearLayout {

	private ImageButton leftIcon, rightIcon;// 左右侧图片
	private TextView titletext;// 中间标题
	private TextView rightText;// 右侧完成按钮

	private Context c;
	private MyOnClickListener myOnClickListener;

	public void setMyOnClickListener(MyOnClickListener myOnClickListener) {
		this.myOnClickListener = myOnClickListener;
	}

	@SuppressLint("NewApi")
	public TitleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public TitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public TitleView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context context) {
		this.c = context;
		LayoutInflater.from(getContext()).inflate(R.layout.title, this);
		leftIcon = (ImageButton) findViewById(R.id.title_left);
		titletext = (TextView) findViewById(R.id.title_center);
		rightIcon = (ImageButton) findViewById(R.id.title_right_icon);
		rightText = (TextView) findViewById(R.id.title_right_tex);
		leftIcon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (myOnClickListener == null) {
					((Activity) c).finish();
				} else {
					myOnClickListener.monClick(v);
				}
			}
		});

	}

	public interface MyOnClickListener {
		void monClick(View v);
	}

	public TextView getRightText() {
		return rightText;
	}

	public void setRightText(String btnName) {
		rightText.setText(btnName);
	}

	public ImageButton getLeftIcon() {
		return leftIcon;
	}

	public void setLeftIcon(int resId) {
		this.leftIcon.setImageResource(resId);
	}

	public ImageButton getRightIcon() {

		return rightIcon;

	}

	public void setRightIcon(int resId) {
		this.rightIcon.setImageResource(resId);

	}

	public TextView getTitletext() {
		return titletext;
	}

	public void setTitletext(String text) {
		this.titletext.setText(text);
	}

	/**
	 * 设置左边图片是否显示(默认显示)
	 * 
	 * @param visibility
	 */
	public void setLeftVisibility(int visibility) {
		leftIcon.setVisibility(visibility);
	}

	/**
	 * 设置右边图片是否显示(默认不显示)
	 * 
	 * @param visibility
	 */
	public void setRightVisibility(int visibility) {
		rightIcon.setVisibility(visibility);
	}

	/**
	 * 设置右边文字按钮是是否显示(默认不显示)
	 * 
	 * @param visibility
	 */
	public void setRightBtnTextVisibility(int visibility) {
		rightText.setVisibility(visibility);
	}
}
