package org.bugapi.bugset.base.validation.aspect;

import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.bugapi.bugset.base.exception.InvokeException;
import org.bugapi.bugset.base.exception.InvokeTimeoutException;

/**
 * 数据库执行切面
 *
 * @author gust
 * @since 0.0.1
 */
public class DatabaseExecutionAspect {

  @Pointcut(value = "execution(* org.bugapi.bugset.*.mapper.*Mapper.*(..))")
  public void methodPointCut() {

  }

  @Around(value = "methodPointCut()")
  public void catchTimeoutException(ProceedingJoinPoint point) throws Throwable {
    try {
      point.proceed();
    } catch (SQLTimeoutException e) {
      throw new InvokeTimeoutException(point.getArgs(), e);
    } catch (SQLException e) {
      throw new InvokeException(point.getArgs(), e);
    }
  }
}
