package com.miller.seckill.rabbitmq;

import com.miller.seckill.domain.User;
import lombok.*;

/**
 * Created by miller on 2018/8/25
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeckillMessage {
    private User user;
    private long goodsId;
}
