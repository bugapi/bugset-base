package org.bugapi.bugset.base.exception;

import lombok.Getter;
import org.bugapi.bugset.base.exception.enums.ExceptionTypeEnum;

/**
 * 系统所有运行时异常的基类
 *
 * @author zhangxw
 * @since 0.0.1
 */
@Getter
public class BaseException extends RuntimeException {
	private static final long serialVersionUID = -6021077900819863433L;

	/**
	 * 异常类型
	 */
	private ExceptionTypeEnum exceptionType;

	public BaseException() {
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(ExceptionTypeEnum exceptionType, Throwable cause) {
		super(exceptionType.getMessage(), cause);
		this.exceptionType = exceptionType;
	}

	public BaseException(ExceptionTypeEnum exceptionType, String message) {
		super(message);
		this.exceptionType = exceptionType;
	}

	public BaseException(ExceptionTypeEnum exceptionType, String message, Throwable cause) {
		super(message, cause);
		this.exceptionType = exceptionType;
	}

}