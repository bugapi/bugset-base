package org.bugapi.bugset.base.util.exception;

/**
 * 获取 当前执行位置/异常报错 处代码的文件名/类名/方法名/行号
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class StackTraceUtil {

	/**
	 * 获取异常的信息和发生的位置
	 *
	 * @param e 异常类
	 * @return String 异常信息
	 */
	public static String getExceptionMsg(Exception e) {
		if (null != e && null != e.getStackTrace() && e.getStackTrace().length >= 1) {
			StackTraceElement stackTraceElement = e.getStackTrace()[0];
			return String.format("--error messag detail：%s, file=%s, method=%s, line=%s", e.toString(),
					getFileName(stackTraceElement), getMethodName(stackTraceElement), getLineNumber(stackTraceElement));
		}
		return "堆栈信息为空";
	}

	/**
	 * 获取当前进程的执行位置信息
	 *
	 * @return String
	 */
	public static String getCurrentLineMsg() {
		/*
		调用本类中方法的位置 --> StackTraceUtil.getLineNumber() --> Thread.getStackStrace()
		因为栈是 先入后出 (队列是 先入先出), 所以最初调用位置的函数栈下表是 2
		 */
		int originStackIndex = 2;
		StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[originStackIndex];
		return String.format("--current line detail：file=%s, method=%s, line=%s",
				getFileName(stackTraceElement), getMethodName(stackTraceElement), getLineNumber(stackTraceElement));
	}

	/**
	 * 获取文件名
	 *
	 * @param stackTraceElement Java线程中的方法栈信息
	 * @return String 文件名
	 */
	private static String getFileName(StackTraceElement stackTraceElement) {
		if (null != stackTraceElement) {
			return stackTraceElement.getFileName();
		}
		return "StackTraceUtil.java";
	}

	/**
	 * 获取类名
	 *
	 * @param stackTraceElement Java线程中的方法栈信息
	 * @return String 获取类名
	 */
	private static String getClassName(StackTraceElement stackTraceElement) {
		if (null != stackTraceElement) {
			return stackTraceElement.getClassName();
		}
		return "org.bugapi.bugset.base.util.exception.StackTraceUtil";
	}

	/**
	 * 获取方法名
	 *
	 * @param stackTraceElement Java线程中的方法栈信息
	 * @return String 方法名
	 */
	private static String getMethodName(StackTraceElement stackTraceElement) {
		if (null != stackTraceElement) {
			return stackTraceElement.getMethodName();
		}
		return "getMethodName";
	}

	/**
	 * 获取行号
	 *
	 * @param stackTraceElement Java线程中的方法栈信息
	 * @return int 行号
	 */
	private static int getLineNumber(StackTraceElement stackTraceElement) {
		if (null != stackTraceElement) {
			return stackTraceElement.getLineNumber();
		}
		return 0;
	}

	public static void main(String[] args) {
		System.out.println(getCurrentLineMsg());
		System.out.println(getExceptionMsg(new Exception()));
	}
}
