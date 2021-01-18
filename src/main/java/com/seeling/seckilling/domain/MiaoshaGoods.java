package com.seeling.seckilling.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Match Fu
 * @date 2020/6/13 11:09 上午
 */
@Data
public class MiaoshaGoods {
    private Long id;
    private Long goodsId;
    private Double miaoshaPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
