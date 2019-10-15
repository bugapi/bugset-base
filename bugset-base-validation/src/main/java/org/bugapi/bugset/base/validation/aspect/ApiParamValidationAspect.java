package org.bugapi.bugset.base.validation.aspect;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.bugapi.bugset.base.constant.SymbolType;
import org.bugapi.bugset.base.exception.ApiParamException;
import org.bugapi.bugset.base.util.collection.CollectionUtil;
import org.bugapi.bugset.base.util.string.StringUtil;

/**
 * api参数校验切面
 *
 * @author gust
 * @since 0.0.1
 */
public class ApiParamValidationAspect {

  private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
  private static Validator validator = factory.getValidator();

  @Pointcut(value = "@annotation(javax.validation.Valid)")
  public void methodPointCut() {

  }

  @Before(value = "methodPointCut()")
  public void validate(ProceedingJoinPoint point) throws Throwable {
    Object[] args = point.getArgs();
    if (args != null && args.length > 0) {
      Set<ConstraintViolation<Object>> violations;
      StringBuilder result = new StringBuilder();
      for (Object arg : args) {
        violations  = validator.validate(arg);
        if (CollectionUtil.isEmpty(violations)) {
          continue;
        }
        violations.forEach(violation -> {
          result.append(violation.getMessage()).append(SymbolType.SEMICOLON);
        });
      }
      String validateResult = result.toString();
      if (StringUtil.isNotBlank(validateResult)) {
        throw new ApiParamException(validateResult);
      }
    }
    point.proceed();
  }
}
