package ru.izebit.structs;

import java.util.BitSet;

/**
 * @author Artem Konovalov
 *         Date: 20.08.12
 *         Time: 0:10
 *         Фильтр Блума - структура данных, позволяющая определить, имеется ли данный объект в
 *         множестве или нет
 *         может ошибочно определить, что добавлен. обратное неверно
 */
public class BlumFilter {

    //TODO найти подходящую хэш функцию
    private static double[] factors = new double[]{
            Math.E, Math.PI, (Math.sqrt(5) + 1) / 2, 1d / 9, 1d / 3
    };
    private BitSet table;

    public BlumFilter(int size) {
        table = new BitSet(size);
    }

    /**
     * добавляет объект в множество
     *
     * @param element объект который нужно добавить
     * @return true если такого элемента еще нет, иначе false
     */
    public boolean add(Object element) {
        boolean result = true;
        for (int i = 0; i < factors.length; i++) {
            int hash = hash(element, i);
            result = table.get(hash) && result;
            table.set(hash, true);
        }
        return !result;
    }

    /**
     * проверяет-добавлялся ли ранее этот объект
     *
     * @param element объект для которого нужно выполнить проверку
     * @return true-добавлялся, иначе false
     */
    public boolean contains(Object element) {
        for (int i = 0; i < factors.length; i++)
            if (!table.get(hash(element, i)))
                return false;


        return true;
    }

    private int hash(Object element, int i) {
        int hash = element.hashCode();
        return (int) (((hash * factors[i]) % 1) * table.size());
    }
}
