package ru.izebit.structs.dsu;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Artem Konovalov on 3/17/16.
 * При объединении учитывается верхняя граница высоты дерева
 */
public class FairDSU<T> implements DSU<T> {
    private final Map<T, T> tree = new HashMap<>();
    private final Map<T, Integer> rank = new HashMap<>();


    @Override
    public void makeSet(T element) {
        tree.put(element, element);
        rank.put(element, 1);
    }

    @Override
    public T find(T element) {
        if (element.equals(tree.get(element)))
            return element;

        T head = find(tree.get(element));
        tree.put(element, head);

        return head;
    }

    @Override
    public T union(T firstElement, T secondElement) {
        if (rank.get(firstElement).compareTo(rank.get(secondElement)) < 0) {
            T buffer = firstElement;
            firstElement = secondElement;
            secondElement = buffer;
        }

        tree.put(firstElement, secondElement);
        rank.remove(firstElement);
        rank.compute(secondElement, (key, value) -> value + 1);
        return secondElement;
    }
}
