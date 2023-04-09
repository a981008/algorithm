package com.wang.algorithm.utils;

import java.util.Objects;

/**
 * Hash 工具类
 *
 * @author Wang
 * @since 2023/4/9
 */
public class HashUtils {
    /**
     * 参考 {@link java.lang.String#hashCode()}
     */
    public static int hashCode(String s, int seed) {
        if (Objects.isNull(s)) return 0;

        int res = 1;
        for (int i = 0; i < s.length(); i++) {
            res = seed * res + s.charAt(i);
        }

        return res;
    }
}
