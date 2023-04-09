package com.wang.algorithm.simple;

import com.wang.algorithm.utils.HashUtils;

/**
 * 概率数据结构 —— Count-min Sketch
 * <p>
 * 生成 k * n 的二位数组，同时 k 为执行不同 hash 的次数
 * <p>
 * 设第 i 次 y = hash(x) % n
 * <p>
 * 计数时：将 (i, y) 增加
 * <p>
 * 统计时：收集 (i, y) 最小值
 *
 * @author Wang
 * @since 2023/4/9
 */
public class SimpleCMSketch {
    private final int N;
    private final int K;
    private final int[][] matrix;

    public SimpleCMSketch(int n, int k) {
        N = n;
        K = k;
        matrix = new int[K][N];
    }

    private int hashCode(String s, int seed) {
        return HashUtils.hashCode(s, seed) & (N - 1);
    }

    public void put(String s) {
        for (int i = 0; i < K; i++) {
            int j = hashCode(s, i);
            matrix[i][j]++;
        }
    }

    public int count(String s) {
        int cnt = Integer.MAX_VALUE;
        for (int i = 0; i < K; i++) {
            int j = hashCode(s, i);
            cnt = Math.min(matrix[i][j], cnt);
        }
        return cnt;
    }
}
