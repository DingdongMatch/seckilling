package com.seeling.seckilling.redis;

/**
 * @author Match Fu
 * @date 2020/3/21 9:08 下午
 */
public class UserKey extends BasePrefix{
    private static final int TOKEN_EXPIRE = 3600*24*2;

    private UserKey(int expireSeconds, String prefix) {
        super(expireSeconds,prefix);
    }

    public static UserKey token = new UserKey(TOKEN_EXPIRE,"tk");
    public static UserKey getById = new UserKey(0,"id");
}
