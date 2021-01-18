package com.seeling.seckilling.rabbitmq;

import com.seeling.seckilling.domain.MiaoshaOrder;
import com.seeling.seckilling.domain.User;
import com.seeling.seckilling.redis.RedisService;
import com.seeling.seckilling.result.CodeMsg;
import com.seeling.seckilling.result.Result;
import com.seeling.seckilling.service.GoodsService;
import com.seeling.seckilling.service.MiaoshaService;
import com.seeling.seckilling.service.OrderService;
import com.seeling.seckilling.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Match Fu
 * @date 2020/8/2 10:44 下午
 */
@Service
public class MQReceiver {
    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);
    @Autowired
    RedisService redisService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    MiaoshaService miaoshaService;

    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receive(String message) {
        log.info("receive message:" + message);
        MiaoshaoMessage mm = RedisService.stringToBean(message,MiaoshaoMessage.class);
        User user = mm.getUser();
        long goodsId = mm.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoById(goodsId);
        int stock = goods.getGoodsStock();
        if (stock <= 0) {
            return;
        }
        // 判断是否秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaoOrderByUserIdGoodsId(user.getId(),goodsId);
        if (order != null) {
            return;
        }
        // 减库存 下订单 写入秒杀订单
        miaoshaService.miaosha(user,goods);
    }

    /**
     * Direct模式 交换机Exchange
     * @param message
    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message) {
        log.info("receive message:" + message);
    }*/

    /**
     * @param message
     */
    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        log.info("receive topic queue1 message:" + message);
    }

    /**
     * @param message
     */
    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        log.info("receive topic queue2 message:" + message);
    }

    /**
     * @param message
     */
    @RabbitListener(queues = MQConfig.HEADERS_QUEUE)
    public void receiveHeadersQueue(byte[] message) {
        log.info("receive headers queue message:" + new String(message));
    }
}
