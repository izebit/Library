package ru.izebit.algorithms.concurrency;

/**
 * @author <a href="izebit@gmail.com">Artem Konovalov</a> <br/>
 * Date: 09/11/2017/.
 */
public interface Lock {
    void lock();

    void unlock();
}
