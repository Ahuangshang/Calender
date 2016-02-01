package cn.ltwc.cft.beans;

/**
 * 
 * TODO:右侧菜单的JavaBean
 * 
 * @author huangshang 2015-11-15 上午10:09:39
 * @Modified_By:
 */
public class MenuBean {
	private int icon;// 菜单图标
	private String menuName;// 菜单名

	public MenuBean(int icon, String menuName) {
		super();
		this.icon = icon;
		this.menuName = menuName;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String toString() {
		return "RightMenuBean [icon=" + icon + ", menuName=" + menuName + "]";
	}

}
