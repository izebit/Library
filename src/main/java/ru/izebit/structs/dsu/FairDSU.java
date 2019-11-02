package ru.izebit.structs.dsu;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Artem Konovalov on 3/17/16.
 * При объединении учитывается верхняя граница высоты дерева
 */
public class FairDSU<T> extends DSU<T> {
    private final Map<T, Integer> rank = new HashMap<>();

    /**
     * {@inheritDoc}
     *
     * @param element элемент
     */
    @Override
    public void makeSet(T element) {
        super.makeSet(element);
        rank.put(element, 1);
    }

    /**
     * {@inheritDoc}
     *
     * @param firstElement  элемент с которым отождествляется первое множество
     * @param secondElement элемент с которым отождествляется второе
     * @return элемент для объединенного множества
     */
    @Override
    public T union(T firstElement, T secondElement) {
        if (rank.get(firstElement).compareTo(rank.get(secondElement)) < 0) {
            T buffer = firstElement;
            firstElement = secondElement;
            secondElement = buffer;
        }

        tree.put(firstElement, secondElement);
        rank.remove(firstElement);
        rank.compute(secondElement, (key, value) -> Objects.isNull(value) ? 1 : value + 1);
        return secondElement;
    }
}
