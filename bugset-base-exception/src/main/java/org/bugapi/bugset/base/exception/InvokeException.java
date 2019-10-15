package org.bugapi.bugset.base.exception;

import org.bugapi.bugset.base.exception.enums.BusinessExceptionEnum;
import org.bugapi.bugset.base.exception.enums.ExceptionTypeEnum;

/**
 * 服务调用异常
 * 适用数据库，缓存，rpc等调用
 *
 * @author gust
 * @since 0.0.1
 */
public class InvokeException extends BaseException {

  public InvokeException(Throwable cause) {
    super(BusinessExceptionEnum.BUSINESS_ERROR, cause);
  }

  public InvokeException(String message, Throwable cause) {
    super(BusinessExceptionEnum.BUSINESS_ERROR, message, cause);
  }

  public InvokeException(
      ExceptionTypeEnum exceptionType, String message, Throwable cause) {
    super(exceptionType, message, cause);
  }
}
