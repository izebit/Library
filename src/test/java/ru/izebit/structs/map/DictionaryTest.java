package ru.izebit.structs.map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;
import java.util.Map.Entry;

import static org.junit.Assert.*;

/**
 * @author Artem Konovalov
 *         creation date  12/30/16.
 * @since 1.0
 */
@RunWith(value = Parameterized.class)
public class DictionaryTest {
    private final Dictionary<String, Integer> map;
    private final Map<String, Integer> data;


    public DictionaryTest(Dictionary<String, Integer> map, int count) {
        this.map = map;
        data = getData(count);
    }

    private static Map<String, Integer> getData(int count) {
        Random random = new Random(System.currentTimeMillis());

        Map<String, Integer> data = new HashMap<>();
        for (int i = 0; i < count; i++) {
            String key = random.nextInt() + "";
            Integer value = random.nextInt();
            data.put(key, value);
        }

        return data;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> data = new ArrayList<>();
        data.add(new Object[]{new LRUCache<>(10_000), 10_000});

        return data;
    }


    @Test
    public void getTest() {
        for (Entry<String, Integer> entry : data.entrySet())
            map.put(entry.getKey(), entry.getValue());

        assertTrue(map.size() == data.size());

        for (Entry<String, Integer> entry : data.entrySet())
            assertEquals(entry.getValue(), map.get(entry.getKey()));

        map.clear();
    }

    @Test
    public void containsKeyTest() {
        for (Entry<String, Integer> entry : data.entrySet())
            map.put(entry.getKey(), entry.getValue());

        assertTrue(map.size() == data.size());

        for (Entry<String, Integer> entry : data.entrySet())
            assertTrue(map.containsKey(entry.getKey()));

        map.clear();
    }

    @Test
    public void containsValueTest() {
        for (Entry<String, Integer> entry : data.entrySet())
            map.put(entry.getKey(), entry.getValue());

        assertTrue(map.size() == data.size());

        for (Entry<String, Integer> entry : data.entrySet())
            assertTrue(map.containsValue(entry.getValue()));

        map.clear();
    }

    @Test
    public void clearTest() {
        for (Entry<String, Integer> entry : data.entrySet())
            map.put(entry.getKey(), entry.getValue());

        map.clear();
        assertEquals(map.size(), 0);

        for (Entry<String, Integer> entry : data.entrySet()) {
            assertFalse(map.containsValue(entry.getValue()));
            assertFalse(map.containsKey(entry.getKey()));
        }

        map.clear();
    }

    @Test
    public void cleanTest() {
        for (Entry<String, Integer> entry : data.entrySet())
            map.put(entry.getKey(), entry.getValue());

        assertTrue(map.size() == data.size());
        map.clear();
        assertEquals(map.size(), 0);

        for (Entry<String, Integer> entry : data.entrySet())
            assertEquals(null, map.get(entry.getKey()));

        map.clear();
    }

    @Test
    public void removeTest() {
        for (Entry<String, Integer> entry : data.entrySet())
            map.put(entry.getKey(), entry.getValue());

        int count = data.size() / 2;
        List<String> removedKeys = new ArrayList<>(count);
        for (Entry<String, Integer> entry : data.entrySet()) {
            String key = entry.getKey();
            map.remove(key);
            removedKeys.add(key);
            if (count == removedKeys.size())
                break;
        }

        assertEquals(map.size(), data.size() - count);
        for (Entry<String, Integer> entry : data.entrySet()) {
            String key = entry.getKey();
            if (removedKeys.contains(key))
                assertEquals(map.get(key), null);
            else
                assertEquals(map.get(key), entry.getValue());
        }

        map.clear();
    }
}
