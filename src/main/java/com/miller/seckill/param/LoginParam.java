package com.miller.seckill.param;

import com.miller.seckill.validator.IsMobile;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;


/**
 * Created by miller on 2018/8/9
 * @author Miller
 */
@Getter
@Setter
public class LoginParam {

    @IsMobile
    private String mobile;

    @NotBlank
    @Length(min = 32)
    private String password;
}
