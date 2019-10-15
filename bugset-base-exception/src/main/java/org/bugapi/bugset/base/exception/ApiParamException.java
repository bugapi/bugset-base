package org.bugapi.bugset.base.exception;

import org.bugapi.bugset.base.exception.enums.ApiParamExceptionEnum;

/**
 * api参数异常
 * 适用基础的参数校验，不涉及任务的业务
 *
 * @author gust
 * @since 0.0.1
 */
public class ApiParamException extends BaseException {

  public ApiParamException(String message) {
    super(ApiParamExceptionEnum.PARAM_ERROR, message);
  }

  public ApiParamException(ApiParamExceptionEnum exceptionType, String message) {
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
