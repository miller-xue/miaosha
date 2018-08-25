package com.miller.seckill.rabbitmq;

import com.miller.seckill.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
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

    /*public void send(Object message) {
        String msg = JsonUtil.toJSONString(message);
        log.info("send message : " + msg);
        amqpTemplate.convertAndSend(MQConfig.QUEUE_NAME, msg);
    }


    public void sendTopic(Object message) {
        String msg = JsonUtil.toJSONString(message);
        log.info("send message : " + msg);
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE_NAME, "topic.key1", message + "1");
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE_NAME, "topic.key2", message + "2");
    }


    public void sendFanout(Object message) {
        String msg = JsonUtil.toJSONString(message);
        log.info("Fanout send message : " + msg);
        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE_NAME,"" , message);
    }

    public void sendHeaders(Object message) {
        String msg = JsonUtil.toJSONString(message);
        log.info("headers send message : " + msg);

        MessageProperties properties = new MessageProperties();
        properties.setHeader("header1", "value1");
        properties.setHeader("header2", "value2");

        Message obj = new Message(msg.getBytes(),properties);
        amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE_NAME, "", obj);
    }*/

    public void sendSeckillMessage(SeckillMessage message) {
        String msg = JsonUtil.toJSONString(message);
        log.info("send message : " + msg);
        amqpTemplate.convertAndSend(MQConfig.SECKILL_QUEUE, msg);
    }
}
