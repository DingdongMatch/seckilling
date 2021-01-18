package com.seeling.seckilling.vo;

import com.seeling.seckilling.domain.Goods;
import lombok.Data;

import java.util.Date;

/**
 * @author Match Fu
 * @date 2020/6/13 11:21 上午
 */
@Data
public class GoodsVo extends Goods {
    private Double miaoshaPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
