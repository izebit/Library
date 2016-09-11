package ru.izebit.structs.immutable;


import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ConcTreeListTest {

    @Test
    public void addTest() {
        ConcTreeList<String> list = ConcTreeList.empty();
        list = list.add("hello");

        assertThat(list.size, is(1));
    }

    @Test
    public void add_null() {
        ConcTreeList<String> list = ConcTreeList.empty();
        list = list.add("hello").add(null);

        assertThat(list.size, is(2));
        assertTrue(list.contains("hello"));
        assertTrue(list.contains(null));
    }

    @Test
    public void size_for_duplicate() {
        ConcTreeList<String> list = ConcTreeList.empty();
        list = list.add(null).add(null);
        assertThat(list.size, is(2));
    }


    @Test
    public void concat_empty_list() {
        ConcTreeList<Integer> l1 = ConcTreeList.empty();
        ConcTreeList<Integer> l2 = ConcTreeList.empty();

        ConcTreeList<Integer> union = l2.concat(l1);
        assertThat(union.size, is(0));
    }

    @Test
    public void cancatTest() {
        ConcTreeList<Integer> l1 = ConcTreeList.<Integer>empty().add(1).add(2);
        ConcTreeList<Integer> l2 = ConcTreeList.<Integer>empty().add(0);

        ConcTreeList<Integer> union = l2.concat(l1);
        assertThat(union.size, is(3));
        assertTrue(union.contains(0));
        assertTrue(union.contains(1));
        assertTrue(union.contains(2));
    }

    @Test
    public void cancat_with_empty_list() {
        ConcTreeList<Integer> l1 = ConcTreeList.empty();
        ConcTreeList<Integer> l2 = ConcTreeList.<Integer>empty().add(0);

        ConcTreeList<Integer> union = l2.concat(l1);
        assertThat(union.size, is(1));
        assertTrue(union.contains(0));
    }

    @Test
    public void set_and_check_position() {
        ConcTreeList<String> list = ConcTreeList.empty();
        list = list.add("hello").add("world").add("i").add("love").add("java");
        list = list.set(1, "artem");
        assertThat(list.size, is(5));
        assertThat(list.get(1), is("artem"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void set_when_index_more_than_size() {
        ConcTreeList<String> list = ConcTreeList.empty();
        list = list.add("hello").add("world");
        list.set(2, "artem");
    }

    @Test(expected = IllegalArgumentException.class)
    public void set_when_list_is_empty() {
        ConcTreeList<String> list = ConcTreeList.empty();
        list.set(0, "artem");
    }

    @Test
    public void insertTest() {
        ConcTreeList<String> list = ConcTreeList.empty();
        list = list.add("hello").add("world").insert(1, "artem");
        assertThat(list.size, is(3));
        assertTrue(list.contains("artem"));
    }

    @Test
    public void insert_null_into_list() {
        ConcTreeList<String> list = ConcTreeList.empty();
        list = list.add("hello").add("world").insert(1, null);
        assertThat(list.size, is(3));
        assertTrue(list.contains(null));
    }

    @Test
    public void insert_into_list_when_index_is_zero() {
        ConcTreeList<String> list = ConcTreeList.empty();
        list = list.add("hello").add("world").insert(0, null);
        assertThat(list.size, is(3));
        assertTrue(list.contains(null));
    }

    @Test
    public void insert_into_list_when_index_more_list_size() {
        ConcTreeList<String> list = ConcTreeList.empty();
        list = list.add("hello").add("world").insert(10, null);
        assertThat(list.size, is(3));
        assertTrue(list.contains(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void insert_into_list_when_index_less_list_size() {
        ConcTreeList<String> list = ConcTreeList.empty();
        list.add("hello").add("world").insert(-1, null);
    }
}
