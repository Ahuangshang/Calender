package cn.ltwc.cft.http;

/**
 * 功能:服务器应答消息 作者:mike 时间：2015-11-17 上午10:24:34 修改:
 */
public interface ServiceResponce {
	/**
	 * 服务器返回成功
	 * 
	 * @param result
	 *            服务器的应答消息
	 * @param responseFlag
	 *            返回码
	 */
	public void httpSuccess(String result, int responseFlag);

	/**
	 * 超时
	 * 
	 * @param responseFlag
	 *            返回码
	 */
	public void httpTimeOut(int responseFlag);

	/**
	 * 未知错误
	 * 
	 * @param responseFlag
	 *            返回码
	 */
	public void httpError(int responseFlag);
}
