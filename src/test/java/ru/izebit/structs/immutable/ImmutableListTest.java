package ru.izebit.structs.immutable;


import org.junit.Assert;
import org.junit.Test;
import ru.izebit.structs.immutable.ImmutableList.Cons;

import java.util.Arrays;
import java.util.LinkedList;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class ImmutableListTest {

    @Test
    public void containsTest() {
        ImmutableList<Number> list = new Cons<>(10, ImmutableList.empty());
        assertTrue(list.contains(10));
        list = list.add(23);
        assertTrue(list.contains(23));
        assertFalse(list.contains(13));


        list = ImmutableList.empty();
        assertFalse(list.contains(0));
    }

    @Test
    public void unionTest() {
        ImmutableList<Number> l1 = new Cons<>(10, ImmutableList.empty());
        l1 = l1.add(23);

        ImmutableList<Number> l2 = new Cons<>(11, ImmutableList.empty());
        ImmutableList<Number> list = l1.union(l2);

        Assert.assertTrue(list.contains(10));
        Assert.assertTrue(list.contains(11));
        Assert.assertTrue(list.contains(23));
        Assert.assertFalse(list.contains(9));
    }

    @Test
    public void removeTest() {
        ImmutableList<Number> list = new Cons<>(10, ImmutableList.empty());
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
        ImmutableList<Number> list = new Cons<>(10, ImmutableList.empty());
        list = list.add(23);
        list = list.add(12);
        list = list.add(14);

        LinkedList<Number> linkedList = new LinkedList<>();
        list.foreach(linkedList::add);

        assertTrue(linkedList.containsAll(Arrays.asList(10, 23, 14, 12)));
    }

    @Test
    public void zero_size_for_empty_list() {
        ImmutableList<Number> list = ImmutableList.empty();
        assertThat(list.size, is(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void exception_when_index_is_less_zero() {
        ImmutableList<String> list = ImmutableList.empty();
        list.remove(-1);
    }

    @Test
    public void size_is_two_after_double_added_zero() {
        ImmutableList<String> list = ImmutableList.empty();
        list = list.add(null).add(null);
        assertThat(list.size, is(2));
    }

    @Test
    public void remove_when_list_has_duplicate() {
        ImmutableList<String> list = ImmutableList.empty();
        list = list.add("hello").add("hello").remove("hello");
        assertThat(list.size, is(1));
        assertTrue(list.contains("hello"));
    }
}
