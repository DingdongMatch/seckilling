package com.seeling.seckilling.rabbitmq;

import com.seeling.seckilling.domain.User;
import lombok.Data;

/**
 * @author Match Fu
 * @date 2020/8/8 10:05 下午
 */
@Data
public class MiaoshaoMessage {
    private User user;
    private long goodsId;
}
