package org.bugapi.bugset.base.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * api参数异常类型枚举 1开头
 * 适用基础的参数校验，不涉及任何的业务
 *
 * @author gust
 * @since 0.0.1
 */
@Getter
@AllArgsConstructor
public enum ApiParamExceptionEnum implements ExceptionTypeEnum {

  /**
   * 参数错误（通用）
   */
  PARAM_ERROR(1000, "参数错误"),

  /**
   * 参数为空（适用：String、各种包装类为空等）
   */
  EMPTY_PARAM_ERROR(1001, "参数为空"),

  /**
   * 参数范围非法（适用：参数超过指定的范围，大小区间错误等）
   */
  ILLEGAL_RANGE_ERROR(1002, "参数范围非法"),

  /**
   * 参数不满足正则表达式
   */
  ILLEGAL_REGEX_ERROR(1003, "参数格式非法");


  /**
   * 异常码
   */
  private int code;

  /**
   * 通用异常信息
   */
  private String message;

}
