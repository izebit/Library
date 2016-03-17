package ru.izebit.structs.dsu;

/**
 * Created by Artem Konovalov on 3/17/16.
 * <p>
 * интерфейс для системы непересекающихся множеств
 * временная сложность для всех операций О(1)
 *
 * @see <a href="https://habrahabr.ru/post/104772/">disjoint set union</a>
 */
public interface DSU<T> {

    /**
     * создание множества с одним элементом
     *
     * @param element элемент
     */
    void makeSet(T element);

    /**
     * поиск элемента к множеству которого относится данный
     *
     * @param element элемент
     * @return элемент с которым отождествляется множество содержащее переданный элемент.
     * если такого нет null
     */
    T find(T element);

    /**
     * объединение двух множеств
     *
     * @param firstElement  элемент с которым отождествляется первое множество
     * @param secondElement элемент с которым отождествляется второе
     * @return возвращает элемент представляющий объединенной множество
     */
    T union(T firstElement, T secondElement);

}
