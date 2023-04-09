package com.wang.algorithm.simple;

import com.wang.algorithm.utils.HashUtils;

/**
 * 概率数据结构 —— 布隆过滤器
 * <p>
 * 容积为 N 的过滤器，进行 K 次不同的 hash，将比特数组对应下标标为 1
 * <p>
 * 优化点
 * 1. 利用位运算做标记
 * 2. 更好的 hash
 * 3. 最佳比特数组大小
 *
 * @author Wang
 * @since 2023/4/8
 */
public class SimpleBloomFilter {
    private final int N;
    private final int[] filter; // 一个 int 32bit，将其看为二维数组进一步压缩空间
    private final int K;
    private final int bN = 32;

    /**
     * @param num 数据量大小
     * @param fp  误报率
     */
    public SimpleBloomFilter(int n, int k) {
        N = n;
        K = k;
        filter = new int[N];
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

    private int hashCode(String s, int seed) {
        return HashUtils.hashCode(s, seed) & (N - 1);
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
