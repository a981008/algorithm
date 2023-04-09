package com.wang.algorithm.structure;

import java.util.Objects;

/**
 * 布隆过滤器
 * <p>
 * 容积为 N 的过滤器，进行 K 次不同的 hash，将比特数组对应下标标为 1
 *
 * @author Wang
 * @since 2023/4/8
 */
public class BloomFilter {
    private final int N;
    private final int[] filter; // 一个 int 32bit，将其看为二维数组进一步压缩空间
    private final int K;
    private final int bN = 32;

    public BloomFilter(int n, int k) {
        N = n;
        filter = new int[N / bN];
        K = k;
    }

    /**
     * 第 k 位置 1
     */
    private int put(int x, int k) {
        return x | 1 << k;
    }

    /**
     * 取第 k 位
     */
    private int get(int x, int k) {
        return x >> k & 1;
    }

    /**
     * 参考 {@link java.util.Arrays#hashCode(int[] a)}
     */
    private int hashCode(String s, int seed) {
        if (Objects.isNull(s)) return 0;

        int res = 1;
        for (int i = 0; i < s.length(); i++) {
            res = seed * res + s.charAt(i);
        }

        return (N - 1) & res;
    }

    /**
     * 向过滤器增加一个元素 s
     */
    public void add(String s) {
        for (int i = 0; i < K; i++) {
            int idx = hashCode(s, i);
            filter[idx / bN] = put(filter[idx / bN], idx % bN);
        }
    }

    /**
     * 判断元素 s 是否在过滤器中
     */
    public boolean contains(String s) {
        for (int i = 0; i < K; i++) {
            int idx = hashCode(s, i);
            if (get(filter[idx / bN], idx % bN) == 0) return false;
        }
        return true;
    }

}
