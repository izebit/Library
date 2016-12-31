package ru.izebit.structs.map;

/**
 * @author Artem Konovalov
 *         creation date  12/30/16.
 * @since 1.0
 */
public interface Dictionary<K, V> {
    /**
     * получения значения
     *
     * @param key ключ
     * @return значение
     */
    V get(K key);


    /**
     * добавления значения
     *
     * @param key   ключ
     * @param value значение
     * @return null если значения по такому ключу не было, иначе предыдующее значения
     */
    V put(K key, V value);

    /**
     * удаление значения
     *
     * @param key ключ
     * @return ассоциируемое значение
     */
    V remove(K key);

    /**
     * удаление всех элементов
     *
     * @return true если все ок, иначе false
     */
    boolean clear();


    /**
     * проверка на наличие значения
     *
     * @param value проверяемое значение
     * @return true если содержится иначе false
     */
    boolean containsValue(V value);

    /**
     * проверка на наличие значения
     *
     * @param key проверяемое значение
     * @return true если содержится иначе false
     */
    boolean containsKey(K key);

    /**
     * получение размера
     *
     * @return количество элементов
     */
    int size();
}
