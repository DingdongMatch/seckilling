package com.seeling.seckilling.vo;

import com.seeling.seckilling.domain.OrderInfo;
import lombok.Data;

/**
 * @author Match Fu
 * @date 2020/7/26 8:51 下午
 */
@Data
public class OrderDetailVo {
    private GoodsVo goods;
    private OrderInfo order;
}
