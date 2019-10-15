package org.bugapi.bugset.base.exception.enums;

/**
 * 异常类型枚举接口
 *
 * @author gust
 * @since 0.0.1
 */
public interface ExceptionTypeEnum {

  /**
   * 获取异常编码
   * @return 异常编码
   */
  int getCode();

  /**
   * 获取异常信息
   * @return 异常信息
   */
  String getMessage();
}
