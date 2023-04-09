package com.wang.algorithm.structure;

import com.wang.algorithm.simple.SimpleCMSketch;
import com.wang.algorithm.utils.RandomUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wang
 * @since 2023/4/9
 */
public class CMSketchTest {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        SimpleCMSketch sketch = new SimpleCMSketch(1000, 10);
        int i = 0;
        while (true) {
            int len = RandomUtils.integer(3) + 1;
            String s = RandomUtils.string(len);
            map.put(s, map.getOrDefault(s, 0) + 1);
            sketch.put(s);

            int i1 = map.get(s);
            int i2 = sketch.count(s);

            if (i1 != i2) {
                System.out.println(i);
                System.out.println("Oops! " + s);
                System.out.println("map-cnt: " + i1 + " sketch-cnt: " + i2);
                return;
            }
            i++;
        }
    }
}
