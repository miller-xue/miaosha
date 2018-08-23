package com.miller.seckill.rabbitmq;

import com.miller.seckill.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by miller on 2018/8/22
 */
@Slf4j
@Service
public class MQSender {

    @Autowired
    AmqpTemplate amqpTemplate;

    public void send(Object message) {
        String msg = JsonUtil.toJSONString(message);
        log.info("send message : " + msg);
        amqpTemplate.convertAndSend(MQConfig.QUEUE_NAME, msg);
    }
}
