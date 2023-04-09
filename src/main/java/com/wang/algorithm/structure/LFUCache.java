package com.wang.algorithm.structure;

import java.util.HashMap;
import java.util.Map;

/**
 * Least Frequently Used，最近最不常使用
 * <p>
 * PLFU：维护所有访问频率，维护元数据成本很大
 * <p>
 * WLFU：维护缓存中 key 访问频率
 * <p>
 * TinyLFU：利用 CM-Sketch 记录访问次数，从而降低维护元数据成本的 PLFU
 *
 * @author Wang
 * @since 2023/4/8
 */
public class LFUCache<K, V> {
    private class Node<K, V> {
        K key;
        V val;
        int freq;
        Node<K, V> prev, next;

        Node() {
        }

        Node(K key, V val, int freq) {
            this.key = key;
            this.val = val;
            this.freq = freq;
        }
    }

    private class DoublyLinkedList<K, V> {
        private final Node<K, V> head; // 虚拟头节点
        private final Node<K, V> tail; // 虚拟尾节点
        int size;

        DoublyLinkedList() {
            head = new Node<>();
            tail = new Node<>();
            head.next = tail;
            tail.prev = head;
            size = 0;
        }

        public void addFirst(Node<K, V> node) {
            Node<K, V> prevHead = head.next;
            node.prev = head;
            head.next = node;
            node.next = prevHead;
            prevHead.prev = node;
            size++;
        }

        public void remove(Node<K, V> node) {
            Node<K, V> prev = node.prev;
            Node<K, V> next = node.next;
            prev.next = next;
            next.prev = prev;
            size--;
        }

        public Node<K, V> getFirst() {
            return head.next;
        }

        public Node<K, V> getLast() {
            return tail.prev;
        }
    }

    private int minFreq;
    private final int cap;
    private final Map<K, Node<K, V>> keys; // key -> node
    private final Map<Integer, DoublyLinkedList<K, V>> freqs; // freq -> list

    public LFUCache(int cap) {
        this.cap = cap;
        keys = new HashMap<>(cap);
        freqs = new HashMap<>(cap);
    }

    /**
     * 缓存命中，提升到更高频率的队列
     */
    private void upgrade(K key) {
        Node<K, V> node = keys.get(key);
        V val = node.val;
        int freq = node.freq;

        // 从 freq 中删除
        freqs.get(freq).remove(node);
        // 如果当前链表为空，我们需要在哈希表中删除，且更新 minFreq
        if (freqs.get(freq).size == 0) {
            freqs.remove(freq);
            if (minFreq == freq) {
                minFreq += 1; // 访问增加频率
            }
        }

        // 插入到 freq + 1 中
        DoublyLinkedList<K, V> list = freqs.getOrDefault(freq + 1, new DoublyLinkedList<>());
        list.addFirst(new Node<>(key, val, freq + 1));
        freqs.put(freq + 1, list);
        keys.put(key, freqs.get(freq + 1).getFirst());
    }

    public V get(K key) {
        Node<K, V> node = keys.get(key);
        if (node == null) return null;
        upgrade(key);
        return node.val;
    }

    public void put(K key, V val) {
        if (keys.containsKey(key)) { // 包含
            Node<K, V> node = keys.get(key);
            node.val = val;
            upgrade(key);
        } else {
            if (keys.size() == cap) { // 超出容量，移除
                Node<K, V> node = freqs.get(minFreq).getLast();
                keys.remove(node.key);
                freqs.get(minFreq).remove(node);
                if (freqs.get(minFreq).size == 0) {
                    freqs.remove(minFreq);
                }
            }
            DoublyLinkedList<K, V> list = freqs.getOrDefault(1, new DoublyLinkedList<>());
            list.addFirst(new Node<>(key, val, 1));
            freqs.put(1, list);
            keys.put(key, freqs.get(1).getFirst());
            minFreq = 1;
        }
    }
}
