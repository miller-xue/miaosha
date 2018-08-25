package com.miller.seckill.rabbitmq;

import afu.org.checkerframework.checker.units.qual.A;
import com.miller.seckill.common.Result;
import com.miller.seckill.domain.Order;
import com.miller.seckill.domain.SeckillOrder;
import com.miller.seckill.domain.User;
import com.miller.seckill.enums.SeckillResult;
import com.miller.seckill.redis.RedisService;
import com.miller.seckill.service.GoodsService;
import com.miller.seckill.service.OrderService;
import com.miller.seckill.service.SeckillOrderService;
import com.miller.seckill.service.SeckillService;
import com.miller.seckill.util.JsonUtil;
import com.miller.seckill.vo.GoodsVo;
import com.sun.tools.corba.se.idl.constExpr.Or;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by miller on 2018/8/22
 */
@Slf4j
@Service
public class MQReceiver {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisService redisService;

    /**
     * Direct模式 交换机Exchange
     *
     * 
     * @param message

    @RabbitListener(queues = MQConfig.QUEUE_NAME)
    public void receive(String message) {
        log.info("receive message :" + message);

    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE_NAME_1)
    public void receiveTopic1(String message) {
        log.info("receive topic1 message :" + message);
    }


    @RabbitListener(queues = MQConfig.TOPIC_QUEUE_NAME_2)
    public void receiveTopic2(String message) {
        log.info("receive topic2 message :" + message);
    }

    @RabbitListener(queues = MQConfig.FANOUT_QUEUE_NAME_1)
    public void receiveFanout1(String message) {
        log.info("receive Fanout1 message :" + message);
    }


    @RabbitListener(queues = MQConfig.FANOUT_QUEUE_NAME_2)
    public void receiveFanout2(String message) {
        log.info("receive Fanout2 message :" + message);
    }


    @RabbitListener(queues = MQConfig.HEADERS_QUEUE_NAME)
    public void receiveHeaders(byte[] message) {
        log.info("receive headers message :" + new String(message));
    }
     */

    @RabbitListener(queues = MQConfig.SECKILL_QUEUE)
    public void receive(String message) {
        log.info("receive message :" + message);
        SeckillMessage msg = JsonUtil.parseToClass(message, SeckillMessage.class);
        long id = msg.getGoodsId();
        User user = msg.getUser();

        // 1.判断商品是否存在
        GoodsVo goodsVoId = goodsService.getGoodsVoId(id);
        if (goodsVoId == null) {
            // 进行操作
            return;
        }
        // 判断是否重复秒杀
        Order seckillOrder = orderService.getByUserIdAndGoodsId(user.getId(), id);
        if (seckillOrder != null) {
            return;
        }
        seckillService.seckill(user.getId(), id);
    }

}
