package ru.izebit.algorithms.sort;

import ru.izebit.other.InvalidTypeListException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * сортировка посчетом
 * время работы О(n)
 */
public class CountSort<T extends Integer> extends Sort<T> {

    @Override
    @SuppressWarnings("unchecked")
    public void customSort(List<T> list) throws InvalidTypeListException {
        if (Collections.min(list).compareTo(0) < 0) {
            throw new InvalidTypeListException("список должен содержать не отрицательные элементы");
        }

        int[] c = new int[Collections.max(list).intValue() + 1];
        List<Integer> b = new ArrayList<Integer>(list.size());
        for (T aList : list) {
            c[aList.intValue()] += 1;
            b.add(null);
        }
        for (int i = 1; i < c.length; i++) {
            c[i] += c[i - 1];
        }
        for (T aList : list) {
            b.set(--c[aList.intValue()], aList);
        }
        list.clear();
        list.addAll((Collection<? extends T>) b);
    }
}
