package com.seeling.seckilling.redis;

/**
 * @author Match Fu
 * @date 2020/3/21 9:08 下午
 */
public class MiaoshaKey extends BasePrefix{

    private MiaoshaKey(String prefix) {
        super(prefix);
    }

    public static MiaoshaKey isGoodsOver = new MiaoshaKey("go");
}
