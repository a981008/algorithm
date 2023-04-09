package com.wang.algorithm.structure;

import com.wang.algorithm.utils.RandomUtils;

import java.util.HashSet;
import java.util.Random;

/**
 * 对数器
 *
 * @author Wang
 * @since 2023/4/9
 */
public class BloomFilterTest {
    public static void main(String[] args) {
        BloomFilter bf = new BloomFilter(Integer.MAX_VALUE >> 10, 0.5);
        HashSet<String> set = new HashSet<>();
        Random random = new Random();

        while (true) {
            int len = random.nextInt(20) + 1;
            String s = RandomUtils.string(len);
            bf.add(s);
            set.add(s);
            if (bf.contains(s) && set.contains(s)) {
                System.out.println("s: " + s);
            } else {
                System.out.println("Oops! " + s);
                return;
            }
        }
    }
}
