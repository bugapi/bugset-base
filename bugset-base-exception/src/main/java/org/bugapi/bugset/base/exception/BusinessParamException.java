package org.bugapi.bugset.base.exception;

import org.bugapi.bugset.base.exception.enums.BusinessExceptionEnum;

/**
 * 业务异常
 * 适用业务校验，校验id是否存在等
 *
 * @author gust
 * @since 0.0.1
 */
public class BusinessParamException extends BugSetRuntimeException {

  public BusinessParamException(String message) {
    super(BusinessExceptionEnum.BUSINESS_ERROR, message);
  }

  public BusinessParamException(BusinessExceptionEnum exceptionType, String message) {
    super(exceptionType, message);
  }

  /**
   * 这里不需要收集异常栈的信息，因此注释
   * @return 异常
   */
  @Override
  public synchronized Throwable fillInStackTrace() {
    return this;
  }
}
