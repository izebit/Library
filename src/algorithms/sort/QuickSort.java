package algorithms.sort;

import java.util.Collections;
import java.util.List;

/**
 * быстрая сортировка
 * время работы O(n^2)
 */
public class QuickSort<T extends Comparable<? super T>> extends Sort<T> {

    @Override
    public void customSort(List<T> list) {
        qSort(list, 0, list.size() - 1);
    }

    private void qSort(List<T> list, int left, int right) {
        T pivot = getPivot(list, left, right);
        int leftIndex = left;
        int rightIndex = right;
        while (leftIndex < rightIndex) {
            while (leftIndex <= right && pivot.compareTo(list.get(leftIndex)) > 0) {
                leftIndex++;
            }
            while (rightIndex >= left && pivot.compareTo(list.get(rightIndex)) < 0) {
                rightIndex--;
            }
            if (leftIndex <= rightIndex) {
                Collections.swap(list, leftIndex, rightIndex);
                leftIndex++;
                rightIndex--;
            }
        }
        if (left < rightIndex) qSort(list, left, rightIndex);
        if (leftIndex < right) qSort(list, leftIndex, right);
    }

    //функция выбора опорного элемента
    private T getPivot(List<T> list, int left, int right) {
        return list.get((left+right)>>>1);
    }
}
