package com.example.gulimall.common.validator.annotation;

import com.example.gulimall.common.validator.constraint.ListValueConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * User：Hurried1y
 * Date：2022/11/21
 * Description：
 * @author A
 */
@Documented
//该校验注解使用的校验注解器
@Constraint(validatedBy = { ListValueConstraintValidator.class })
//注解可以标注的位置
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
//注解被获取的时机
@Retention(RetentionPolicy.RUNTIME)
public @interface ListValue {
    //最终会去ValidationMessages.properties配置文件中获取message
    String message() default "{com.example.gulimall.common.validator.annotation.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int[] value() default {};
}
