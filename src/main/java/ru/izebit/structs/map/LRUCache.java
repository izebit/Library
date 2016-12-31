package ru.izebit.structs.map;

import ru.izebit.structs.list.LinkList;
import ru.izebit.structs.list.LinkList.Node;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Artem Konovalov
 *         creation date  12/30/16.
 * @since 1.0
 */
public class LRUCache<K, V> implements Dictionary<K, V> {
    private final int capacity;
    private final Map<K, Entry<Node<K>, V>> values;
    private final LinkList<K> queue = new LinkList<>();
    private int size;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.values = new HashMap<>(capacity);
    }

    @Override
    public V get(K key) {

        Entry<Node<K>, V> entry = values.get(key);
        if (entry == null)
            return null;

        Node<K> node = entry.getKey();
        queue.remove(node);
        queue.addToHead(node);

        return entry.getValue();
    }

    @Override
    public V put(K key, V value) {
        V prevValue = null;

        Entry<Node<K>, V> entry = values.get(key);
        if (entry == null) {
            size++;
            Node<K> node = new Node<>(key);
            entry = new SimpleEntry<>(node, value);
            values.put(key, entry);
            queue.addToHead(node);
        } else {
            prevValue = entry.getValue();
            entry.setValue(value);
        }


        if (size > capacity) {
            Node<K> node = queue.removeTail();
            if (node != null)
                values.remove(node.value);
            size--;
        }

        return prevValue;
    }

    @Override
    public V remove(K key) {
        Entry<Node<K>, V> node = values.remove(key);
        if (node == null)
            return null;

        queue.remove(node.getKey());
        size--;

        return node.getValue();
    }

    @Override
    public boolean clear() {
        size = 0;
        queue.clear();
        values.clear();

        return true;
    }

    @Override
    public boolean containsValue(V value) {
        for (Entry<Node<K>, V> entry : values.values())
            if (value.equals(entry.getValue()))
                return true;

        return false;
    }

    @Override
    public boolean containsKey(K key) {
        return values.containsKey(key);
    }

    @Override
    public int size() {
        return size;
    }
}
