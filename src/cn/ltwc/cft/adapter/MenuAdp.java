package cn.ltwc.cft.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.ltwc.cft.R;
import cn.ltwc.cft.beans.MenuBean;

/**
 * 
 * TODO:右侧菜单的适配器
 * 
 * @author huangshang 2015-11-15 上午10:03:11
 * @Modified_By:
 */

@SuppressLint("InflateParams")
public class MenuAdp extends BaseAdapter {
	private Context c;
	private List<MenuBean> rightMenuList;// 右侧菜单的集合

	public MenuAdp(Context c, List<MenuBean> rightMenuList) {
		super();
		this.c = c;
		this.rightMenuList = rightMenuList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return rightMenuList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return rightMenuList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHodle hodle = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(c).inflate(R.layout.menu_grid, null);
			hodle = new ViewHodle();
			hodle.menuIcon = (ImageView) convertView.findViewById(R.id.rmimg);
			hodle.menuName = (TextView) convertView.findViewById(R.id.rmtxt);
			convertView.setTag(hodle);

		} else {
			hodle = (ViewHodle) convertView.getTag();
		}
		hodle.menuIcon.setImageResource(rightMenuList.get(position).getIcon());
		hodle.menuName.setText(rightMenuList.get(position).getMenuName());
		return convertView;
	}

	class ViewHodle {
		private ImageView menuIcon;// 菜单图标
		private TextView menuName;// 菜单名字

	}
}
