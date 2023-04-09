package com.wang.algorithm.utils;

import java.util.Random;

/**
 * 随机数生成工具类
 *
 * @author Wang
 * @since 2023/4/8
 */
public class RandomUtils {
    private static final Random RANDOM = new Random();
    public static String string(int len) {
        String s = "abcdefghijkumnopqrstuvwsyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int idx = RANDOM.nextInt(len);
            sb.append(s.charAt(idx));
        }
        return sb.toString();
    }

    public static int integer(int bound) {
        return RANDOM.nextInt(bound);
    }

    public static boolean bool() {
        return RANDOM.nextBoolean();
    }

}
