package com.miller.seckill.dto;

import com.miller.seckill.domain.User;
import com.miller.seckill.vo.GoodsVo;
import lombok.*;

/**
 * Created by miller on 2018/8/19
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsDetailDto {
    private int seckillStatus = 0;
    private long remainSeconds = 0;

    private GoodsVo goodsVo;

    private User user;
}
