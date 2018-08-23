package com.miller.seckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Created by miller on 2018/8/22
 */
@Slf4j
@Service
public class MQReceiver {

    /**
     * Direct模式 交换机Exchange
     *
     * 
     * @param message
     */
    @RabbitListener(queues = MQConfig.QUEUE_NAME)
    public void receive(String message) {
        log.info("receive message :" + message);

    }
}
