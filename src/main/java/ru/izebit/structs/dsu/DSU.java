package ru.izebit.structs.dsu;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Artem Konovalov on 3/17/16.
 * <p>
 * интерфейс для системы непересекающихся множеств
 * временная сложность для всех операций О(1)
 *
 * @see <a href="https://habrahabr.ru/post/104772/">disjoint set union</a>
 */
public abstract class DSU<T> {
    protected final Map<T, T> tree = new HashMap<>();

    /**
     * создание множества с одним элементом
     *
     * @param element элемент
     */
    public void makeSet(T element) {
        tree.put(element, element);
    }

    /**
     * поиск элемента к множеству которого относится данный
     *
     * @param element элемент
     * @return элемент с которым отождествляется множество содержащее переданный элемент.
     * если такого нет null
     */
    public T find(T element) {
        if (element == null)
            return null;

        if (element.equals(tree.get(element)))
            return element;

        T head = find(tree.get(element));
        if (head == null)
            return null;

        tree.put(element, head);

        return head;
    }

    /**
     * объединение двух множеств
     *
     * @param firstElement  элемент с которым отождествляется первое множество
     * @param secondElement элемент с которым отождествляется второе
     * @return возвращает элемент представляющий объединенной множество
     */
    abstract T union(T firstElement, T secondElement);
}
