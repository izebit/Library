package ru.izebit.structs;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Artem Konovalov on 4/24/16.
 */
public class TrieTest {

    @Test
    public void containsTest() {
        Trie<String> trie = new Trie<>();
        trie.add("hello", "world");
        trie.add("hi", "anton");
        trie.add("bye", "moscow");
        trie.add("empty", "empty");

        assertTrue(trie.contains("hello"));
        assertFalse(trie.contains("h"));
        assertTrue(trie.contains("hi"));
        assertTrue(trie.contains("bye"));
        assertFalse(trie.contains("by"));
        assertTrue(trie.contains("empty"));
    }

    @Test
    public void removeTest() {
        Trie<String> trie = new Trie<>();
        trie.add("hello", "world");
        trie.add("hi", "anton");
        trie.add("bye", "moscow");
        trie.add("by", "saint-petersburg");
        trie.add("b", "saratov");
        trie.add("empty", "empty");


        trie.remove("hello");
        assertNull(trie.get("hello"));
        assertEquals("anton", trie.get("hi"));
        trie.remove("hi");
        assertNull(trie.get("hi"));


        assertEquals("empty", trie.get("empty"));
        trie.remove("empty");
        assertNull(trie.get("empty"));

        trie.remove("by");
        assertTrue(trie.contains("bye"));
        assertTrue(trie.contains("b"));
        assertFalse(trie.contains("by"));

        trie.remove("b");
        assertTrue(trie.contains("bye"));
        assertFalse(trie.contains("b"));

        trie.add("", "test");
        assertTrue(trie.contains(""));
        trie.remove("");
        assertFalse(trie.contains(""));

        trie.add("aaa", "byby");
        trie.add("aab", "bymu");
        trie.add("aa", "hello-world");
        trie.remove("aa");
        assertFalse(trie.contains("aa"));
    }

    @Test
    public void addTest() {
        Trie<String> trie = new Trie<>();
        trie.add("hello", "world");
        trie.add("hi", "anton");
        trie.add("bye", "moscow");
        trie.add("bye", "artem");
        trie.add("empty", "empty");
        trie.add("e", "first-value");
        trie.add("empt", "second-value");

        assertEquals("world", trie.get("hello"));
        assertEquals("anton", trie.get("hi"));
        assertEquals("artem", trie.get("bye"));
        assertEquals("empty", trie.get("empty"));
        assertEquals("first-value", trie.get("e"));
        assertEquals("second-value", trie.get("empt"));


        trie.add("hello-world", "hello-world");
        assertTrue(trie.contains("hello-world"));

        trie.add("byby", "byby");
        trie.add("bymy", "bymu");
        trie.add("bye-world", "hello-world");
        assertTrue(trie.contains("bye-world"));
    }
}
