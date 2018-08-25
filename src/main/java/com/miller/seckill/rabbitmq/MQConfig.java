package com.miller.seckill.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by miller on 2018/8/22
 */
@Configuration
public class MQConfig {

    public static final String QUEUE_NAME = "queue";
    public static final String TOPIC_QUEUE_NAME_1 = "topic.queue1";
    public static final String TOPIC_QUEUE_NAME_2 = "topic.queue2";


    public static final String TOPIC_EXCHANGE_NAME = "topic.exchange";
    public static final String ROUTING_KEY_1 = "topic.key1";
    public static final String ROUTING_KEY_2 = "topic.#";



    public static final String FANOUT_QUEUE_NAME_1 = "fanout.queue1";
    public static final String FANOUT_QUEUE_NAME_2 = "fanout.queue2";
    public static final String FANOUT_EXCHANGE_NAME = "fanout.exchange";


    public static final String HEADERS_EXCHANGE_NAME = "headers.exchange";
    public static final String HEADERS_QUEUE_NAME = "headers.queue";


    public static final String SECKILL_QUEUE = "seckill.queue";

    @Bean
    public Queue seckillQueue() {
        return new Queue(SECKILL_QUEUE, true);
    }


    /**
     * Dirct模式 交换机Exchange
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }


    /**
     * topic queue1
     */
    @Bean
    public Queue topQueue1() {
        return new Queue(TOPIC_QUEUE_NAME_1, true);
    }

    /**
     * topic queue2
     */
    @Bean
    public Queue topQueue2() {
        return new Queue(TOPIC_QUEUE_NAME_2, true);
    }

    /**
     * topic exchange
     *
     */
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    public Binding topicBinding1() {
        return BindingBuilder.bind(topQueue1()).to(topicExchange()).with(ROUTING_KEY_1);
    }

    @Bean
    public Binding topicBinding2() {
        return BindingBuilder.bind(topQueue2()).to(topicExchange()).with(ROUTING_KEY_2);
    }


    @Bean
    public Queue fanoutQueue1() {
        return new Queue(FANOUT_QUEUE_NAME_1, true);
    }
    @Bean
    public Queue fanoutQueue2() {
        return new Queue(FANOUT_QUEUE_NAME_2, true);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE_NAME);
    }

    @Bean
    public Binding fanoutBinding1() {
        return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
    }
    @Bean
    public Binding fanoutBinding2() {
        return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
    }


    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange(HEADERS_EXCHANGE_NAME);
    }

    @Bean
    public Queue headersQueue1() {
        return new Queue(HEADERS_QUEUE_NAME, true);
    }

    @Bean
    public Binding headersBinding() {
        Map<String, Object> map = new HashMap<>();
        map.put("header1", "value1");
        map.put("header2", "value2");
        return BindingBuilder.bind(headersQueue1()).to(headersExchange()).whereAll(map).match();
    }
}
