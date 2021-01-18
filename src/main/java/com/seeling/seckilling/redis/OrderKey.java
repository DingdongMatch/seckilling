package com.seeling.seckilling.redis;

/**
 * @author Match Fu
 * @date 2020/3/21 9:08 下午
 */
public class OrderKey extends BasePrefix{

    private OrderKey(String prefix) {
        super(prefix);
    }

    public static OrderKey getMiaoShaOrderByUidGid = new OrderKey("moug");
}
