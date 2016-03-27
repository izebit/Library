package ru.izebit.algorithms.sort;

import java.util.ArrayList;
import java.util.List;

/**
 * Сортировка слиянием
 * время работы O(nlog(n))
 */
public class MergeSort<T extends Comparable<? super T>> extends Sort<T> {
    @Override
    protected void customSort(List<T> list) {
        partition(list, 0, list.size() - 1);
    }

    private void partition(List<T> list, final int left, final int right) {
        if (right - left <= 0) return;

        int middle = left + (right - left) / 2;
        if (left < middle) partition(list, left, middle);
        if (middle + 1 < right) partition(list, middle + 1, right);
        merge(list, left, middle, right);
    }

    private void merge(List<T> list, final int left, final int center, final int right) {
        int leftRange = left;
        int rightRange = center + 1;
        List<T> buffer = new ArrayList<>(right - left);
        while (leftRange <= center && rightRange <= right) {
            if (list.get(leftRange).compareTo(list.get(rightRange)) < 0) {
                buffer.add(list.get(leftRange));
                leftRange++;
            } else {
                buffer.add(list.get(rightRange));
                rightRange++;
            }
        }
        while (leftRange <= center) {
            buffer.add(list.get(leftRange));
            leftRange++;
        }
        while (rightRange <= right) {
            buffer.add(list.get(rightRange));
            rightRange++;
        }
        for (int i = 0; i < buffer.size(); i++) {
            list.set(left + i, buffer.get(i));
        }
    }

}
