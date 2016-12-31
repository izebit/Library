package ru.izebit.algorithms.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * сортировка посчетом
 * время работы О(n)
 */
public class CountSort extends Sort<Integer> {

    @Override
    protected void customSort(List<Integer> list) throws IllegalArgumentException {
        if (Collections.min(list).compareTo(0) < 0)
            throw new IllegalArgumentException("Список должен содержать не отрицательные элементы");

        int[] c = new int[Collections.max(list) + 1];
        List<Integer> b = new ArrayList<>(list.size());
        for (Integer aList : list) {
            c[aList] += 1;
            b.add(null);
        }
        for (int i = 1; i < c.length; i++)
            c[i] += c[i - 1];

        for (Integer aList : list)
            b.set(--c[aList], aList);

        list.clear();
        list.addAll(b);
    }
}
