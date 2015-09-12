package ru.izebit.algorithms.sort;

import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;

public abstract class Sort<T> {
    /**
     * Производит сортировку списка
     *
     * @param list список который следует отсортировать
     * @throws java.lang.IllegalArgumentException некоторые алгоритмы могут отсортировать список элементов
     *                                            определенного типа, если передается список элементов не того типа, то выбрасывается исключение
     */
    public void sort(List<T> list) throws IllegalArgumentException {
        if (list.size() < 2) return;

        if (list instanceof RandomAccess) {
            customSort(list);
        } else {
            List<T> arrayList = new ArrayList<T>(list);
            customSort(arrayList);
            list.clear();
            list.addAll(arrayList);
        }
    }

    /**
     * метод с конкретной реализацией сортировки
     */
    protected abstract void customSort(List<T> list) throws IllegalArgumentException;
}
