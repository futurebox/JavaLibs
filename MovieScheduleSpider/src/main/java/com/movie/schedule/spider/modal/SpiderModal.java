package com.movie.schedule.spider.modal;

import java.io.Serializable;

/**
 * 
 * 结果回传
 * 
 * @author futurebox
 *
 */
public class SpiderModal implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 0为成功，其他为失败
	 */
	private int code = 0;
	
	/**
	 * 异常消息
	 */
	private String msg = "success";
	
	public SpiderModal() {
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
	@Override
	public String toString() {
		return new StringBuffer()
				.append("[ code =")
				.append(code)
				.append(", msg=")
				.append(msg)
				.append(" ]")
				.toString();
	}
}
