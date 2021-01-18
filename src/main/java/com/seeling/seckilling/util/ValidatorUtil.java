package com.seeling.seckilling.util;

import org.thymeleaf.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Match Fu
 * @date 2020/5/17 4:39 下午
 */
public class ValidatorUtil {
    private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");
    public static boolean isMobile(String src) {
        if (StringUtils.isEmpty(src)) {
            return false;
        }
        Matcher m = mobile_pattern.matcher(src);
        return m.matches();
    }
    public static void main(String[] args) {
        System.out.println(isMobile("13777895428"));
        System.out.println(isMobile("1891234123"));
	}
}
