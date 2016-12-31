package ru.izebit.structs.list;

/**
 * @author Artem Konovalov
 *         creation date  12/30/16.
 * @since 1.0
 */
public interface List<V> {

    /**
     * добавление элемента
     *
     * @param value значение
     * @return добавленное значение
     */
    V add(V value);

    /**
     * удаление значения
     *
     * @param value значение
     * @return null если такого значения не содержится, иначе value
     */
    V remove(V value);

    /**
     * удаление по индексу
     *
     * @param index индекс
     * @return если индекс больше size - 1 или меньше 0, то null иначе удаленное значение
     */
    V remove(int index);

    /**
     * проверка на принадлежность
     *
     * @param value значение
     * @return true если значение содержится, иначе false
     */
    boolean contains(V value);

    /**
     * получение размера
     *
     * @return количество элементов
     */
    int size();

    /**
     * удаление всех элементов
     */
    void clear();
}
