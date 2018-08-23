package com.miller.seckill.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by miller on 2018/8/22
 */
@Configuration
public class MQConfig {

    public static final String QUEUE_NAME = "queue";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }
}
