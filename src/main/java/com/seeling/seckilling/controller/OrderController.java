package com.seeling.seckilling.controller;

import com.seeling.seckilling.domain.OrderInfo;
import com.seeling.seckilling.domain.User;
import com.seeling.seckilling.result.CodeMsg;
import com.seeling.seckilling.result.Result;
import com.seeling.seckilling.service.GoodsService;
import com.seeling.seckilling.service.OrderService;
import com.seeling.seckilling.vo.GoodsVo;
import com.seeling.seckilling.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Match Fu
 * @date 2020/7/26 8:52 下午
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> detail(User user, @RequestParam("orderId") long orderId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderInfo order = orderService.getOrderById(orderId);
        if (order == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        GoodsVo goods = goodsService.getGoodsVoById(order.getGoodsId());
        OrderDetailVo vo = new OrderDetailVo();
        vo.setGoods(goods);
        vo.setOrder(order);
        return Result.success(vo);
    }
}
