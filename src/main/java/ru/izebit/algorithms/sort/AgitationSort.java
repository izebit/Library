package ru.izebit.algorithms.sort;

import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 22.09.12
 * Time: 16:14
 *
 * @author Artem Konovalov
 *         <p>
 *         Сортировка взбалтыванием
 *         Время работы О(n*n)
 */
public class AgitationSort<T extends Comparable<? super T>> extends Sort<T> {
    @Override
    public void customSort(List<T> list) {
        for (int i = list.size() - 1; i > 0; i--) {
            for (int j = 1; j <= i; j++) {
                if (list.get(j - 1).compareTo(list.get(j)) > 0) {
                    Collections.swap(list, j - 1, j);
                }
            }
        }
    }
}
