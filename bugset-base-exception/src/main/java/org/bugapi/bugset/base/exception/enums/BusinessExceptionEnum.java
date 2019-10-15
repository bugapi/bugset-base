package org.bugapi.bugset.base.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务异常类型枚举 2开头
 *
 * @author gust
 * @since 0.0.1
 */
@Getter
@AllArgsConstructor
public enum BusinessExceptionEnum implements ExceptionTypeEnum {

  /**
   * 业务异常（通用）
   */
  BUSINESS_ERROR(2000, "业务异常"),

  /**
   * 业务状态异常（适用id不存在，查询时数据已删除等访问时数据状态异常）
   */
  ILLEGAL_STATE_ERROR(2001, "业务状态异常");

  /**
   * 异常码
   */
  private int code;

  /**
   * 通用异常信息
   */
  private String message;
}
