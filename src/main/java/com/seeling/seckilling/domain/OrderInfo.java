package com.seeling.seckilling.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Match Fu
 * @date 2020/6/13 11:13 上午
 */
@Data
public class OrderInfo {
    private Long id;
    private Long userId;
    private Long goodsId;
    private Long  deliveryAddrId;
    private String goodsName;
    private Integer goodsCount;
    private Double goodsPrice;
    private Integer orderChannel;
    private Integer status;
    private Date createDate;
    private Date payDate;
}
