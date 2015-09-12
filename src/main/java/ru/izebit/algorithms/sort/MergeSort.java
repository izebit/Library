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

    private void partition(List<T> list, int left, int right) {
        int center = left + (right - left) / 2;
        if (left < center) partition(list, left, center);
        if (center + 1 < right) partition(list, center, right);
        merge(list, left, center, right);
    }

    private void merge(List<T> list, int left, int center, int right) {
        int leftRange = left;
        int rightRange = center + 1;
        ArrayList<T> buffer = new ArrayList<T>(right - left);
        while (leftRange <= center && rightRange <= right) {
            if (list.get(leftRange).compareTo(list.get(rightRange)) > 0) {
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
