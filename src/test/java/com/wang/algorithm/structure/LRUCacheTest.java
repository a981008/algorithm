package com.wang.algorithm.structure;

import com.wang.algorithm.simple.SimpleLRUCache;
import com.wang.algorithm.utils.RandomUtils;

import java.util.Objects;

/**
 * @author Wang
 * @since 2023/4/9
 */
public class LRUCacheTest {
    public static void main(String[] args) {
        int cap = RandomUtils.integer(256);
        SimpleLRUCache<Integer, Integer> c1 = new SimpleLRUCache<>(cap);
        LRUCache<Integer, Integer> c2 = new LRUCache<>(cap);

        while (true) {
            boolean isPut = RandomUtils.bool();
            int key = RandomUtils.integer(1024);
            int val = RandomUtils.integer(1024);

            if (isPut) {
                c1.put(key, val);
                c2.put(key, val);
            } else {
                if (!Objects.equals(c1.get(key), c2.get(key))) {
                    System.out.println("Oops!");
                    return;
                }
                System.out.println("key: " + key + " val: " + c1.get(key));
            }
        }
    }
}

