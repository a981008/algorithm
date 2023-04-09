package com.wang.algorithm.structure;

import com.wang.algorithm.simple.SimpleBloomFilter;
import com.wang.algorithm.utils.RandomUtils;

import java.util.HashSet;

/**
 * 对数器
 *
 * @author Wang
 * @since 2023/4/9
 */
public class BloomFilterTest {
    public static void main(String[] args) {
        SimpleBloomFilter bf = new SimpleBloomFilter(Integer.MAX_VALUE >> 10, 10);
        HashSet<String> set = new HashSet<>();

        while (true) {
            int len = RandomUtils.integer(5) + 1;
            String s = RandomUtils.string(len);
            bf.add(s);
            set.add(s);
            if (!(bf.contains(s) && set.contains(s))) {
                System.out.println("Oops! " + s);
                return;
            }
        }
    }
}
