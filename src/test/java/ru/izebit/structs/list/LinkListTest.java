package ru.izebit.structs.list;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author Artem Konovalov
 *         creation date  12/31/16.
 * @since 1.0
 */
public class LinkListTest {
    private static int count = 10_000;

    private static ArrayList<String> get(int count) {
        Random random = new Random(System.currentTimeMillis());
        ArrayList<String> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++)
            list.add(random.nextGaussian() + "");

        return list;
    }

    @Test
    public void addTest() {
        LinkList<String> linkList = new LinkList<>();
        ArrayList<String> data = get(count);

        for (String s : data)
            assertEquals(linkList.add(s), s);

        assertEquals(linkList.size(), count);
    }

    @Test
    public void containsTest() {
        LinkList<String> linkList = new LinkList<>();
        ArrayList<String> data = get(count);

        for (String s : data)
            assertEquals(linkList.add(s), s);

        assertEquals(linkList.size(), count);

        for (String s : data)
            assertTrue(linkList.contains(s));
    }

    @Test
    public void removeIndexTest() {
        LinkList<String> linkList = new LinkList<>();
        ArrayList<String> data = get(count);

        for (String s : data)
            assertEquals(linkList.add(s), s);

        assertEquals(linkList.size(), count);

        for (int i = data.size() - 1; i >= 0; i--)
            if (i % 3 == 0)
                assertNotNull(linkList.remove(i));

        for (int i = 0; i < data.size(); i++)
            if (i % 3 == 0)
                assertFalse(linkList.contains(data.get(i)));
            else
                assertTrue(linkList.contains(data.get(i)));
    }

    @Test
    public void clearTest() {
        LinkList<String> linkList = new LinkList<>();
        ArrayList<String> data = get(count);

        for (String s : data)
            assertEquals(linkList.add(s), s);

        linkList.clear();
        assertEquals(linkList.size(), 0);

        for (String s : data)
            assertFalse(linkList.contains(s));
    }

    @Test
    public void removeElementTest() {
        LinkList<String> linkList = new LinkList<>();
        ArrayList<String> data = get(count);

        for (String s : data)
            assertEquals(linkList.add(s), s);

        assertEquals(linkList.size(), count);

        for (int i = 0; i < data.size(); i++)
            if (i % 3 == 0)
                assertNotNull(linkList.remove(data.get(i)));


        for (int i = 0; i < data.size(); i++)
            if (i % 3 == 0)
                assertFalse(linkList.contains(data.get(i)));
            else
                assertTrue(linkList.contains(data.get(i)));
    }
}
