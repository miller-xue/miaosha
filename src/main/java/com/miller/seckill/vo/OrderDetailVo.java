package com.miller.seckill.vo;

import com.miller.seckill.domain.Order;
import lombok.*;

/**
 * Created by miller on 2018/8/21
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailVo {
    private GoodsVo goods;
    private Order order;
}
