package org.bugapi.bugset.base.exception;

/**
 * 系统所有运行时异常的基类
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class BaseException extends RuntimeException {
	private static final long serialVersionUID = -6021077900819863433L;
	private String title;

	public BaseException() {
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(String message, String title) {
		super(message);
		this.title = title;
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(String message, String title, Throwable cause) {
		super(message, cause);
		this.title = title;
	}


	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}