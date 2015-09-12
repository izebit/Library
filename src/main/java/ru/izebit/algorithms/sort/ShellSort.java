package ru.izebit.algorithms.sort;

import java.util.List;

/**
 * Сортировка Шелла, время работы O(n^(2/3))
 *
 * @author Artem Konovalov
 * @version 0.1
 */
public class ShellSort<T extends Comparable<T>> extends Sort<T> {
    @Override
    protected void customSort(final List<T> list) throws IllegalArgumentException {
        for (int step = list.size() / 2; step > 0; step--) {
            for (int j = step; j < list.size(); j++) {
                int index = j - step;
                T k = list.get(j);
                T r = list.get(j);
                while (index >= 0 && k.compareTo(list.get(index)) < 0) {
                    list.set(index + step, list.get(index));
                    index -= step;
                }
                list.set(index + step, r);
            }
        }
    }
}
