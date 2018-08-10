package com.miller.seckill.validator;

import com.miller.seckill.util.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by miller on 2018/8/10
 * @author Miller
 * 手机校验器
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {

    private boolean required;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 是否必填
        if (required) {
            return ValidatorUtil.isMobile(value);
        }else {// 空直接true
            if (StringUtils.isBlank(value)) {
                return true;
            }else {
                return ValidatorUtil.isMobile(value);
            }
        }
    }
}
