package ru.izebit.structs.map;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Artem Konovalov
 *         creation date  12/31/16.
 * @since 1.0
 */
public class LRUCacheTest {

    @Test
    public void evictTest() {
        int size = 10;
        int index = 10;

        Dictionary<Integer, String> cache = new LRUCache<>(size);
        fill(cache, size);
        cache.put(index++, "hello");
        cache.put(index++, "world");

        assertFalse(cache.containsKey(0));
        assertFalse(cache.containsKey(1));

        assertNotNull(cache.get(2));

        cache.put(index++, "hi");
        cache.put(index, "bue");

        assertTrue(cache.containsKey(2));
        assertFalse(cache.containsKey(3));

        assertEquals(cache.size(), size);
    }

    @Test
    public void evictTest() {
        int size = 10;
        int index = 10;

        Dictionary<Integer, String> cache = new LRUCache<>(size);
        fill(cache, size);
        cache.put(index++, "hello");
        cache.put(index++, "world");

        assertFalse(cache.containsKey(0));
        assertFalse(cache.containsKey(1));

        assertNotNull(cache.get(2));

        cache.put(index++, "hi");
        cache.put(index, "bue");

        assertTrue(cache.containsKey(2));
        assertFalse(cache.containsKey(3));

        assertEquals(cache.size(), size);
    }

    private static void fill(Dictionary<Integer, String> cache, int size) {
        for (int i = 0; i < size; i++)
            cache.put(i, System.currentTimeMillis() + "_" + i);
    }
}
