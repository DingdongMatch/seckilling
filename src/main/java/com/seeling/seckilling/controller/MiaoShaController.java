package com.seeling.seckilling.controller;

import com.seeling.seckilling.domain.MiaoshaOrder;
import com.seeling.seckilling.domain.User;
import com.seeling.seckilling.rabbitmq.MQSender;
import com.seeling.seckilling.rabbitmq.MiaoshaoMessage;
import com.seeling.seckilling.redis.GoodsKey;
import com.seeling.seckilling.redis.MiaoshaKey;
import com.seeling.seckilling.redis.OrderKey;
import com.seeling.seckilling.redis.RedisService;
import com.seeling.seckilling.result.CodeMsg;
import com.seeling.seckilling.result.Result;
import com.seeling.seckilling.service.GoodsService;
import com.seeling.seckilling.service.MiaoshaService;
import com.seeling.seckilling.service.OrderService;
import com.seeling.seckilling.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * @author Match Fu
 * @date 2020/7/1 11:09 下午
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoShaController implements InitializingBean {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MiaoshaService miaoshaService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private MQSender sender;

    private HashMap<Long,Boolean> localOverMap = new HashMap<>();

    /**
     * 系统初始化
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        if (goodsList == null) {
            return;
        }
        for (GoodsVo goods : goodsList ) {
            redisService.set(GoodsKey.getMiaoshaGoodsStock,""+goods.getId(),goods.getStockCount());
            localOverMap.put(goods.getId(),false);
        }
    }

    @RequestMapping(value="/reset", method=RequestMethod.GET)
    @ResponseBody
    public Result<Boolean> reset(Model model) {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        for(GoodsVo goods : goodsList) {
            goods.setStockCount(10);
            redisService.set(GoodsKey.getMiaoshaGoodsStock, ""+goods.getId(), 10);
            localOverMap.put(goods.getId(), false);
        }
        redisService.delete(OrderKey.getMiaoShaOrderByUidGid);
        redisService.delete(MiaoshaKey.isGoodsOver);
        miaoshaService.reset(goodsList);
        return Result.success(true);
    }

    /**
     * QPS 2469
     * 1000*50
     * 优化后
     * QPS 3711
     * 1000*50
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/do_miaosha",method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> list(User user, @RequestParam("goodsId") long goodsId) {
        if (user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        if(over) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        // 预减库存
        long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock,""+goodsId);
        if (stock < 0) {
            localOverMap.put(goodsId,true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        // 判断是否秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaoOrderByUserIdGoodsId(user.getId(),goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEAT_MIAOSHA);
        }
        // 入队
        MiaoshaoMessage mm = new MiaoshaoMessage();
        mm.setUser(user);
        mm.setGoodsId(goodsId);
        sender.sendMiaoshaMessage(mm);
        return Result.success(0);
        /*// 判断库存
        GoodsVo goods = goodsService.getGoodsVoById(goodsId);
        int stock =  goods.getStockCount();
        if (stock <= 0) {
            return Result.error(CodeMsg.MIAO_SHA_OVERE);
        }
        // 判断是否秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaoOrderByUserIdGoodsId(user.getId(),goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEAT_MIAOSHA);
        }
        // 减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user,goods);
        return Result.success(orderInfo);*/
    }

    /**
     *
     * @param user
     * @param goodsId
     * @return orderId:成功 -1:秒杀失败 0:排队中
     */
    @RequestMapping(value = "/result",method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> miaoshaResult(User user, @RequestParam("goodsId") long goodsId) {
        if (user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long result = miaoshaService.getMiaoshaReuslt(user.getId(),goodsId);
        return Result.success(result);
    }
}
