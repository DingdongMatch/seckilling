package com.seeling.seckilling.vo;

import com.seeling.seckilling.domain.User;
import lombok.Data;

/**
 * @author Match Fu
 * @date 2020/7/23 8:40 下午
 */
@Data
public class GoodsDetailVo {
    private int miaoshaStatus;
    private int remainSeconds;
    private GoodsVo goods;
    private User user;
}
