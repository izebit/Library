package ru.izebit.algorithms.sort;

import ru.izebit.other.InvalidTypeListException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Artem Konovalov
 * @version 0.1
 */
public class Shell<T extends Comparable<T>> extends Sort<T> {
    @Override
    public void customSort(final List<T> list) throws InvalidTypeListException {
        List<Integer> steps = new ArrayList<Integer>();
        int value = 1;
        do {
            steps.add(value);
            value = value * 3 + 1;
        } while (3 * value < list.size());

        for (Integer step : steps) {
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
