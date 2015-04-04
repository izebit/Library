package ru.izebit.algorithms.sort;

import java.util.Collections;
import java.util.List;

/**
 * Сортировка пузырьком
 * Время работы O(n*n)
 */
public class BubbleSort<T extends Comparable<? super T>> extends Sort<T> {

    @Override
    public void customSort(List<T> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).compareTo(list.get(j)) > 0) {
                    Collections.swap(list, i, j);
                }
            }
        }
    }
}
