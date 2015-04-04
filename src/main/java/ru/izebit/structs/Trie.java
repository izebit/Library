package ru.izebit.structs;

import java.util.ArrayList;
import java.util.List;

/**
 * сжатый бор
 *
 * @author Artem Konovalov
 * @version 0.1
 */
public class Trie<V> {
    private final Node root = new Node("", null);
    private int size;

    public Trie() {
        size = 0;
    }

    public void add(String key, V value) {
        if (key.isEmpty()) {
            throw new IllegalArgumentException("key is empty");
        }

        Node parent = null;
        for (Node node : root.childs) {
            if (node.key.charAt(0) == key.charAt(0)) {
                parent = node;
                break;
            }
        }

        if (parent == null) {
            root.addChild(new Node(key, value));
            size++;
        } else {
            root.childs.remove(parent);
            parent = addition(key, value, parent);
            root.addChild(parent);
        }
    }

    private Node addition(String key, V value, Node node) {

        int index = 0;
        while (index < key.length() && index < node.key.length() && key.charAt(index) == node.key.charAt(index)) {
            index++;
        }

        if (index == key.length()) {
            if (index == node.key.length()) {
                node.value = value;
                size = !node.used ? size + 1 : size;
                node.used = true;
                return node;
            } else {
                size++;
                Node n = new Node(key.substring(0, index), value);
                n.addChild(node);
                node.key = node.key.substring(index);
                return n;
            }
        } else {
            if (index == node.key.length()) {
                Node parent = null;
                for (Node n : node.childs) {
                    if (n.key.charAt(0) == key.charAt(index)) {
                        parent = n;
                        break;
                    }
                }
                if (parent == null) {
                    Node n = new Node(key.substring(index), value);
                    node.addChild(n);
                    size++;
                    return node;
                } else {
                    node.childs.remove(parent);
                    parent = addition(key.substring(index), value, parent);
                    node.addChild(parent);
                    return node;
                }
            } else {
                size++;
                Node middle = new Node(key.substring(0, index), null);
                middle.used = false;
                node.key = node.key.substring(index);
                key = key.substring(index);
                middle.addChild(new Node(key, value));
                middle.addChild(node);
                return middle;
            }
        }
    }

    public void remove(String key) {
        if (key.isEmpty()) {
            throw new IllegalArgumentException("key is empty");
        }

        Node parent = null;
        for (Node node : root.childs) {
            if (node.key.charAt(0) == key.charAt(0)) {
                parent = node;
                break;
            }
        }

        if (parent != null) {
            root.childs.remove(parent);
            parent = removeRecursive(key, parent);
            if (parent != null) {
                root.addChild(parent);
            }
        }
    }

    private Node removeRecursive(String key, Node node) {
        int index = 0;
        while (index < key.length() && index < node.key.length() && key.charAt(index) == node.key.charAt(index)) {
            index++;
        }


        if (index == key.length()) {
            if (index == node.key.length()) {
                if (node.used) {
                    size--;
                    if (node.childs.size() == 0) {
                        return null;
                    }
                    if (node.childs.size() == 1) {
                        Node child = node.childs.iterator().next();
                        if (!child.used && child.childs.size() == 0) {
                            return null;
                        }

                        child.key = node.key + child.key;
                        return child;
                    }
                    node.used = false;
                    node.value = null;
                }
                return node;
            } else {
                return node;
            }
        } else {
            if (index == node.key.length()) {
                Node parent = null;
                for (Node n : node.childs) {
                    if (n.key.charAt(0) == key.charAt(index)) {
                        parent = n;
                        break;
                    }
                }
                if (parent == null) {
                    return node;
                } else {
                    node.childs.remove(parent);
                    parent = removeRecursive(key.substring(index), parent);
                    if (parent != null) {
                        node.addChild(parent);
                    }
                    return node;
                }
            } else {
                return node;
            }
        }
    }


    public boolean contains(String key) {
        if (key.isEmpty()) {
            throw new IllegalArgumentException("key is empty");
        }

        Node parent = null;
        for (Node node : root.childs) {
            if (node.key.charAt(0) == key.charAt(0)) {
                parent = node;
                break;
            }
        }

        if (parent == null) {
            return false;
        } else {
            return containsRecursive(key, parent);
        }
    }

    private boolean containsRecursive(String key, Node node) {

        int index = 0;
        while (index < key.length() && index < node.key.length() && key.charAt(index) == node.key.charAt(index)) {
            index++;
        }

        if (index == key.length()) {
            return index == node.key.length();
        } else {
            if (index == node.key.length()) {
                Node parent = null;
                for (Node n : node.childs) {
                    if (n.key.charAt(0) == key.charAt(index)) {
                        parent = n;
                        break;
                    }
                }

                return parent != null && containsRecursive(key.substring(index), parent);
            } else {
                return false;
            }
        }
    }


    public V get(String key) {
        if (key.isEmpty()) {
            throw new IllegalArgumentException("key is empty!");
        }

        Node parent = null;
        for (Node node : root.childs) {
            if (node.key.charAt(0) == key.charAt(0)) {
                parent = node;
                break;
            }
        }

        if (parent == null) {
            return null;
        } else {
            return getRecursive(key, parent);
        }

    }

    private V getRecursive(String key, Node node) {
        int index = 0;
        while (index < key.length() && index < node.key.length() && key.charAt(index) == node.key.charAt(index)) {
            index++;
        }

        if (index == key.length()) {
            if (index == node.key.length() && node.used) {
                return node.value;
            }
            return null;

        } else {
            if (index == node.key.length()) {
                Node parent = null;
                for (Node n : node.childs) {
                    if (n.key.charAt(0) == key.charAt(index)) {
                        parent = n;
                        break;
                    }
                }
                if (parent == null) {
                    return null;
                } else {
                    return getRecursive(key.substring(index), parent);
                }
            } else {
                return null;
            }
        }

    }


    public int size() {
        return size;
    }


    private class Node {
        private String key;
        private V value;
        private List<Node> childs;
        private boolean used = false;

        private Node(String key, V value) {
            this.key = key;
            this.value = value;
            this.used = true;
            this.childs = new ArrayList<Node>(2);
        }

        private void addChild(Node child) {
            childs.add(child);
        }

        private void removeChild(Node child) {
            childs.remove(child);
        }

    }
}
