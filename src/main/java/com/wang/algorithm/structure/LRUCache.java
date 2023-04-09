package com.wang.algorithm.structure;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Least recently used，最近最少使用
 * <p>
 * {@link LinkedHashMap} 当 accessOrder 为 true 时，内部链表就会按照访问顺序构建，所以可以直接用 LinkedHashMap 做 LRUCache
 *
 * @author Wang
 * @since 2023/4/8
 */
public class LRUCache<K, V> {
    private class Node<K, V> {
        K key;
        V val;
        Node<K, V> prev, next;

        public Node() {
        }

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    private final Node<K, V> head; // 虚拟头节点
    private final Node<K, V> tail; // 虚拟尾节点
    private final Map<K, Node<K, V>> map; // key -> node
    private final int cap;

    public LRUCache(int cap) {
        this.cap = cap;
        map = new HashMap<>(cap);
        head = new Node<>();
        tail = new Node<>();
        head.next = tail;
        tail.prev = head;
    }

    /**
     * 增加一个 kv
     */
    public void put(K key, V val) {
        Node<K, V> node = map.get(key);
        if (node != null) { // 包含 key
            node.val = val;
            moveLast(node);
        } else {
            node = new Node<>(key, val);
            map.put(key, node);
            if (map.size() > cap) { // 超出容量，移除
                Node<K, V> first = removeFirst();
                map.remove(first.key);
            }
            addLast(node);
        }
    }

    public V get(K key) {
        Node<K, V> node = map.get(key);
        if (node != null) {
            moveLast(node);
            return node.val;
        }

        return null;
    }

    /**
     * 尾部增加节点
     */
    private void addLast(Node<K, V> node) {
        Node<K, V> prev = tail.prev;
        prev.next = node;
        node.next = tail;
        node.prev = prev;
        tail.prev = node;
    }

    /**
     * 移除队头
     */
    private Node<K, V> removeFirst() {
        Node<K, V> next = head.next;
        head.next = next.next;
        if (next.next != null) {
            next.next.prev = head;
        }
        return next;
    }

    /**
     * 移除节点
     */
    private void remove(Node<K, V> node) {
        Node<K, V> prev = node.prev;
        Node<K, V> next = node.next;
        prev.next = next;
        next.prev = prev;
    }

    /**
     * 移动到队尾
     */
    private void moveLast(Node<K, V> node) {
        remove(node);
        addLast(node);
    }
}
