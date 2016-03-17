package ru.izebit.structs.dsu;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Artem Konovalov on 3/18/16.
 */
@RunWith(value = Parameterized.class)
public class DSUTest {

    private final DSU<String> set;

    public DSUTest(DSU<String> set) {
        this.set = set;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> data = new ArrayList<>();
        data.add(new Object[]{new FairDSU<String>()});
        data.add(new Object[]{new RandomDSU<String>()});
        return data;
    }

    @Test
    public void makeSetAndFindTest() {
        String first = "hello world";
        String second = "hello";
        String third = "world";

        set.makeSet(first);
        set.makeSet(second);
        set.makeSet(third);

        Assert.assertEquals(first, set.find(first));
        Assert.assertEquals(second, set.find(second));
        Assert.assertEquals(third, set.find(third));
    }

    @Test
    public void unionTest() {
        String first = "hello world";
        String second = "hello";
        String third = "world";
        String forth = "bue";

        set.makeSet(first);
        set.makeSet(second);
        set.makeSet(third);
        set.makeSet(forth);

        String head = set.union(first, forth);
        Assert.assertEquals(head, set.find(first));
        Assert.assertEquals(head, set.find(forth));

        head = set.union(head, second);
        Assert.assertEquals(head, set.find(first));
        Assert.assertEquals(head, set.find(second));
        Assert.assertEquals(third, set.find(third));
    }
}
