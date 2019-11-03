package org.bugapi.bugset.base.exception;

/**
 * 系统所有受检查异常的基类
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class BugSetException extends Exception {

  public BugSetException(String message, Throwable cause) {
    super(message, cause);
  }
}
