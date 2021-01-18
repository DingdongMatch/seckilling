package com.seeling.seckilling.util;

import java.util.UUID;

/**
 * @author Match Fu
 * @date 2020/5/31 1:59 下午
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-","");
    }
}
