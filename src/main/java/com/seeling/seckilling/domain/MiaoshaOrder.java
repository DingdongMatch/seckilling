package com.seeling.seckilling.domain;

import lombok.Data;

/**
 * @author Match Fu
 * @date 2020/6/13 11:10 上午
 */
@Data
public class MiaoshaOrder {
    private Long id;
    private Long userId;
    private Long  orderId;
    private Long goodsId;
}
