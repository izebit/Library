package ru.izebit.algorithms.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * поразрядная сортировка
 * время работы O(n)
 */
public class RadixSort extends Sort<Integer> {

    /**
     * @param numeric    число в котором берется байт
     * @param byteNumber номер байта
     * @return значение byteNumber байта в числе numeric
     */
    private static int getByte(int numeric, int byteNumber) {
        return (numeric >> (8 * byteNumber)) & 255;
    }

    @Override
    protected void customSort(List<Integer> list) throws IllegalArgumentException {
        if (Collections.min(list).compareTo(0) < 0)
            throw new IllegalArgumentException("Массив не должен содержать отрицательные элементы");

        for (int i = 0; i < Integer.SIZE / 8; i++) {
            int[] c = new int[256];
            for (Integer aList : list)
                c[getByte(aList, i)]++;

            for (int j = 1; j < c.length; j++)
                c[j] += c[j - 1];

            Integer[] b = new Integer[list.size()];
            for (int j = list.size() - 1; j >= 0; j--)
                b[--c[getByte(list.get(j), i)]] = list.get(j);

            list.clear();
            list.addAll(Arrays.asList(b));
        }
    }
}
