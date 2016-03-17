package ru.izebit.structs.dsu;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Artem Konovalov on 3/17/16.
 * Случайный выбор множества при объединении
 * Временная сложность операций О(1)
 */
public class RandomDSU<T> implements DSU<T> {
    private final Map<T, T> tree = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void makeSet(T element) {
        tree.put(element, element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T find(T element) {
        if (element.equals(tree.get(element)))
            return element;

        T head = find(tree.get(element));
        tree.put(element, head);

        return head;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T union(T firstElement, T secondElement) {
        if (System.currentTimeMillis() % 2 == 0) {
            tree.put(firstElement, secondElement);
            return secondElement;
        } else {
            tree.put(secondElement, firstElement);
            return firstElement;
        }
    }
}
