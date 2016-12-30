package ru.izebit.structs.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * сжатый бор
 *
 * @author Artem Konovalov
 * @version 0.1
 */
public class Trie<V> {
    private Node<V> root = new Node<>("", null, false, null);


    public void add(String key, V value) {
        if (key.length() == 0) {
            root.value = value;
            root.isContainsValue = true;
            return;
        }

        AtomicInteger offset = new AtomicInteger(0);
        Node<V> node = recursiveSearch(key, offset, root);
        if (node == null) {
            node = new Node<>(key, value, true, root);
            root.children.add(node);
        } else {

            int index = 0;
            for (int i = 0; i < node.key.length() && (offset.get() + i) < key.length(); i++) {
                if (node.key.charAt(i) != key.charAt(offset.get() + i))
                    break;
                index = i + 1;
            }

            if (index < node.key.length()) {
                node.parent.children.remove(node);
                Node<V> middle = new Node<>(node.key.substring(0, index), null, false, node.parent);
                middle.parent.children.add(middle);
                node.parent = middle;
                if (index + offset.get() == key.length()) {
                    middle.isContainsValue = true;
                    middle.value = value;
                } else {
                    Node<V> newChild = new Node<>(key.substring(offset.get() + index), value, true, middle);
                    middle.children.add(newChild);
                }
                node.key = node.key.substring(index, node.key.length());
                middle.children.add(node);
            } else if (index == node.key.length()) {

                if (index + offset.get() == key.length()) {
                    node.value = value;
                    node.isContainsValue = true;
                } else {
                    if (node.isContainsValue) {
                        Node<V> newNode = new Node<>(key.substring(offset.get() + index), value, true, node);
                        node.children.add(newNode);

                    } else {
                        node.key += key.substring(offset.get() + index);
                        node.isContainsValue = true;
                        node.value = value;
                    }
                }

            }
        }
    }

    private Node<V> recursiveSearch(String key, AtomicInteger offset, Node<V> node) {
        Node<V> currentNode = null;
        for (Node<V> child : node.children)
            if (key.charAt(offset.get()) == child.key.charAt(0)) {
                currentNode = child;
                break;
            }

        if (currentNode == null)
            return null;


        for (int i = 0; i < currentNode.key.length() && (offset.get() + i) < key.length(); i++)
            if (currentNode.key.charAt(i) != key.charAt(offset.get() + i))
                return currentNode;


        int index = offset.get() + currentNode.key.length();
        if (index >= key.length())
            return currentNode;
        else {
            offset.set(offset.get() + currentNode.key.length());
            Node<V> result = recursiveSearch(key, offset, currentNode);
            if (result == null)
                offset.set(offset.get() - currentNode.key.length());

            return result == null ? currentNode : result;
        }
    }

    public boolean contains(String key) {
        if (key.length() == 0)
            return root.isContainsValue;


        AtomicInteger offset = new AtomicInteger(0);
        Node<V> node = recursiveSearch(key, offset, root);
        if (node == null || !node.isContainsValue)
            return false;

        if (node.key.length() != key.length() - offset.get())
            return false;

        for (int i = 0; i < node.key.length(); i++)
            if (node.key.charAt(i) != key.charAt(i + offset.get()))
                return false;

        return true;
    }

    public V get(String key) {
        if (key.length() == 0)
            return root.isContainsValue ? root.value : null;

        AtomicInteger offset = new AtomicInteger(0);
        Node<V> node = recursiveSearch(key, offset, root);
        if (node == null || !node.isContainsValue)
            return null;

        if (node.key.length() != key.length() - offset.get())
            return null;

        for (int i = 0; i < node.key.length(); i++)
            if (node.key.charAt(i) != key.charAt(i + offset.get()))
                return null;

        return node.value;
    }

    public boolean remove(String key) {
        if (key.length() == 0) {
            root.value = null;
            boolean result = root.isContainsValue;
            root.isContainsValue = false;
            return result;
        }

        AtomicInteger offset = new AtomicInteger();
        Node<V> node = recursiveSearch(key, offset, root);
        if (node == null || !node.isContainsValue)
            return false;

        if (node.key.length() != key.length() - offset.get())
            return false;

        for (int i = 0; i < node.key.length(); i++)
            if (node.key.charAt(i) != key.charAt(i + offset.get()))
                return false;


        node.parent.children.remove(node);
        if (node.children.size() == 0) {
            recursiveRemove(node.parent);
            node.parent = null;
        } else {
            if (node.children.size() == 1) {
                Node<V> child = node.children.remove(0);
                child.parent = node.parent;
                child.key = node.key + child.key;
                child.parent.children.add(child);
            } else {
                node.isContainsValue = false;
                node.value = null;
            }
        }


        return true;
    }

    private void recursiveRemove(Node<V> node) {
        if (node == null || node.parent == null)
            return;

        if (node.children.size() != 0 || node.isContainsValue)
            return;


        node.parent.children.remove(node);
        node.parent = null;

        recursiveRemove(node);
    }


    private static class Node<V> {
        private V value;
        private String key;
        private boolean isContainsValue = false;
        private List<Node<V>> children = new ArrayList<>(0);
        private Node<V> parent;

        public Node(String key, V value, boolean isContainsValue, Node<V> parent) {
            this.key = key;
            this.value = value;
            this.isContainsValue = isContainsValue;
            this.parent = parent;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Node))
                return false;

            Node<?> other = (Node<?>) obj;
            return Objects.equals(this.value, other.value) && Objects.equals(this.key, other.value);
        }

        @Override
        public int hashCode() {
            return 11
                    + (this.key == null ? 1 : this.key.hashCode()) * 17
                    + (this.value == null ? 1 : this.value.hashCode()) * 31;
        }
    }
}
