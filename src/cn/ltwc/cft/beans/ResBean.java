package cn.ltwc.cft.beans;

/**
 * 
 * TODO:内涵段子返回数据的Bean
 * 
 * @author huangshang 2015-11-24 下午2:59:56
 * @Modified_By:
 */
public class ResBean {
	private String res_code;// 返回码
	private String res_error;// 返回错误
	private ResBodyBean res_body;// 返回数据体

	public ResBean() {
		super();
	}

	public ResBean(String res_code, String res_error, ResBodyBean res_body) {
		super();
		this.res_code = res_code;
		this.res_error = res_error;
		this.res_body = res_body;
	}

	public String getRes_code() {
		return res_code;
	}

	public void setRes_code(String res_code) {
		this.res_code = res_code;
	}

	public String getRes_error() {
		return res_error;
	}

	public void setRes_error(String res_error) {
		this.res_error = res_error;
	}

	public ResBodyBean getRes_body() {
		return res_body;
	}

	public void setRes_body(ResBodyBean res_body) {
		this.res_body = res_body;
	}

	@Override
	public String toString() {
		return "ResBean [res_code=" + res_code + ", res_error=" + res_error
				+ ", res_body=" + res_body + "]";
	}

}
