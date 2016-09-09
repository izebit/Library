package ru.izebit.structs;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static ru.izebit.structs.RedBlackTree.NIL;

public class RedBlackTreeTest {


    @Test
    public void balancingTest() {
        RedBlackTree<Integer, String> tree = new RedBlackTree<>();
        Random random = new Random();
        final int count = 100_000;
        Set<Integer> keys = new HashSet<>();
        for (int i = 0; i < count; i++) {
            Integer key = random.nextInt();
            String value = "number is " + key;
            tree.add(key, value);
            keys.add(key);
        }

        assertThat(tree.size(), is(keys.size()));

        redBlackTreePropertyCheck(tree);
    }


    @Test
    public void removeTest() {
        RedBlackTree<String, String> tree = new RedBlackTree<>();
        tree.add("apple", "pineapple");
        tree.add("appl", "pear");
        assertTrue(tree.remove("apple"));
        assertThat(tree.size(), is(1));

        assertTrue(tree.remove("appl"));
        assertThat(tree.size(), is(0));

        redBlackTreePropertyCheck(tree);
    }


    @Test
    public void remove_key_when_tree_is_empty() {
        RedBlackTree<String, String> tree = new RedBlackTree<>();
        assertFalse(tree.remove("hello"));

        redBlackTreePropertyCheck(tree);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exception_when_remove_key_is_null() {
        RedBlackTree<String, String> tree = new RedBlackTree<>();
        tree.remove(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exception_when_add_key_is_null() {
        RedBlackTree<String, String> tree = new RedBlackTree<>();
        tree.add(null, "hello");
    }

    @Test
    public void add_value_is_null() {
        RedBlackTree<String, String> tree = new RedBlackTree<>();
        tree.add("hello", null);
        assertNull(tree.get("hello"));
    }

    @Test
    public void addTest() {
        RedBlackTree<String, String> tree = new RedBlackTree<>();
        tree.add("hello", "world");
        tree.add("world", "hello");
        tree.add("hello world", "hello world");

        assertThat(tree.size(), is(3));
        assertThat(tree.get("hello world"), is("hello world"));
        assertNull(tree.get("apple"));
        assertThat(tree.get("world"), is("hello"));
        assertThat(tree.get("hello"), is("world"));

        redBlackTreePropertyCheck(tree);
    }


    private <K extends Comparable<? super K>, V> void redBlackTreePropertyCheck(RedBlackTree<K, V> tree) {
        List<Integer> list = new ArrayList<>();
        test(tree.head, 0, list);
        for (int i = 1; i < list.size(); i++)
            assertTrue(list.get(i - 1).equals(list.get(i)));
    }

    //проверяет коректность красно-черных свойств
    private <K extends Comparable<? super K>, V> void test(RedBlackTree.Node<K, V> node, int count, List<Integer> results) {
        if (node == NIL)
            return;

        if (node.colour == RedBlackTree.Colour.BLACK)
            count++;

        if (node.leftChild == NIL && node.rightChild == NIL) {
            results.add(count);
            return;
        }

        test(node.leftChild, count, results);
        test(node.rightChild, count, results);
    }
}
