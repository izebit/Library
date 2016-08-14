package ru.izebit.structs.immutable;


import org.junit.Assert;
import org.junit.Test;
import ru.izebit.structs.immutable.List.Cons;

import java.util.Arrays;
import java.util.LinkedList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class ListTest {

    @Test
    public void containsTest() {
        List<Number> list = new Cons<>(10, List.empty());
        assertTrue(list.contains(10));
        list = list.add(23);
        assertTrue(list.contains(23));
        assertFalse(list.contains(13));


        list = List.empty();
        assertFalse(list.contains(0));
    }

    @Test
    public void unionTest() {
        List<Number> l1 = new Cons<>(10, List.empty());
        l1 = l1.add(23);

        List<Number> l2 = new Cons<>(11, List.empty());
        List<Number> list = l1.union(l2);

        Assert.assertTrue(list.contains(10));
        Assert.assertTrue(list.contains(11));
        Assert.assertTrue(list.contains(23));
        Assert.assertFalse(list.contains(9));
    }

    @Test
    public void removeTest() {
        List<Number> list = new Cons<>(10, List.empty());
        list = list.add(23);
        list = list.add(12);
        list = list.add(14);

        list = list.remove(2);

        Assert.assertFalse(list.contains(12));
        Assert.assertTrue(list.contains(23));
        Assert.assertTrue(list.contains(10));
        Assert.assertTrue(list.contains(14));

        list = list.remove(Integer.valueOf(14));

        Assert.assertFalse(list.contains(14));
        Assert.assertTrue(list.contains(23));
        Assert.assertTrue(list.contains(10));
    }

    @Test
    public void foreachTest() {
        List<Number> list = new Cons<>(10, List.empty());
        list = list.add(23);
        list = list.add(12);
        list = list.add(14);

        LinkedList<Number> linkedList = new LinkedList<>();
        list.foreach(linkedList::add);

        assertTrue(linkedList.containsAll(Arrays.asList(10, 23, 14, 12)));
    }

}
