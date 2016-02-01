package cn.ltwc.cft.beans;

public class TiangouBean {
	
	private  String img;
	private String title;
	public TiangouBean(String img, String title) {
		super();
		this.img = img;
		this.title = title;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "TiangouBean [img=" + img + ", title=" + title + "]";
	}
	
	

}
