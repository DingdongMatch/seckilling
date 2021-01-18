package com.seeling.seckilling.dao;

import com.seeling.seckilling.domain.MiaoshaOrder;
import com.seeling.seckilling.domain.OrderInfo;
import com.seeling.seckilling.vo.GoodsVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Match Fu
 * @date 2020/6/13 11:20 上午
 */
@Mapper
public interface OrderDao {

    @Select("SELECT * FROM miaosha_order WHERE user_id = #{userId} AND goods_id = #{goodsId}")
    MiaoshaOrder getMiaoshaoOrderByUserIdGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

    @Insert("INSERT INTO order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)VALUES("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="SELECT last_insert_id()")
    long insert(OrderInfo orderInfo);

    @Insert("INSERT INTO miaosha_order (user_id, goods_id, order_id)VALUES(#{userId}, #{goodsId}, #{orderId})")
    int insertMiaoshaoOrder(MiaoshaOrder miaoshaOrder);

    @Select("SELECT * FROM order_info WHERE id = #{orderId}")
    OrderInfo getOrderById(@Param("orderId")long orderId);

    @Delete("delete from order_info")
    public void deleteOrders();

    @Delete("delete from miaosha_order")
    public void deleteMiaoshaOrders();
}
