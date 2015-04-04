package ru.izebit.algorithms.sort;

import java.util.Collections;
import java.util.List;

/**
 * пирамидальная сортировка
 * время работы O(nlog(n))
 */

public class HeapSort<T extends Comparable<? super T>> extends Sort<T> {
    @Override
    public void customSort(List<T> list) {
        for (int i = list.size() / 2; i >= 0; i--) {
            heapify(list, i, list.size());
        }
        for (int i = 1; i < list.size(); i++) {
            Collections.swap(list, list.size() - i, 0);
            heapify(list, 0, list.size() - i);
        }
    }

    private void heapify(List<T> list, int index, int size) {
        int indexReplace = index;
        if (index * 2 < size) {
            indexReplace = (list.get(indexReplace).compareTo(list.get(index * 2)) < 0) ? index * 2 : indexReplace;
            if (index * 2 + 1 < size) {
                indexReplace = (list.get(indexReplace).compareTo(list.get(index * 2 + 1)) < 0) ? index * 2 + 1 : indexReplace;
            }
        }
        if (indexReplace != index) {
            Collections.swap(list, index, indexReplace);
            heapify(list, indexReplace, size);
        }
    }
}
