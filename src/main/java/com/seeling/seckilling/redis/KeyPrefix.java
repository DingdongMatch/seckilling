package com.seeling.seckilling.redis;

/**
 * @author Match Fu
 * @date 2020/3/21 8:56 下午
 */
public interface KeyPrefix {
    int expireSeconds();

    String getPrefix();
}
