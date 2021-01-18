package com.seeling.seckilling.domain;

import lombok.Data;

/**
 * @author Match Fu
 * @date 2020/6/13 11:08 上午
 */
@Data
public class Goods {
    private Long id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private Double goodsPrice;
    private Integer goodsStock;
}
