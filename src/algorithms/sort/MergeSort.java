package algorithms.sort;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: artem
 * Date: 03.08.12
 * Time: 23:19
 * сортировка слиянием
 * время работы O(nlog(n))
 */
public class MergeSort<T extends Comparable<? super T>> extends Sort<T> {
    @Override
    public void customSort(List list) {
        partition(list, 0, list.size() - 1);
    }

    private void partition(List<T> list, int left, int rigth) {
        int center = left + (rigth - left) / 2;
        if (left < center) partition(list, left, center);
        if (center + 1 < rigth) partition(list, center, rigth);
        merge(list, left, center, rigth);
    }

    private void merge(List<T> list, int left, int center, int rigth) {
        int leftRange = left;
        int rightRange = center + 1;
        ArrayList<T> buffer = new ArrayList<T>(rigth - left);
        while (leftRange <= center && rightRange <= rigth) {
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
        while (rightRange <= rigth) {
            buffer.add(list.get(rightRange));
            rightRange++;
        }
        for (int i = 0; i < buffer.size(); i++) {
            list.set(left + i, buffer.get(i));
        }
    }

}
