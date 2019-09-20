package ru.izebit.algorithms.concurrency;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="izebit@gmail.com">Artem Konovalov</a> <br/>
 * Date: 09/11/2017/.
 */
public class PetersonLock implements Lock {
    private static final AtomicInteger counter = new AtomicInteger();
    private static final Map<Long, Integer> numbers = new ConcurrentHashMap<>();
    private volatile boolean[] slots;
    private volatile int workingThreadNumber;

    @Override
    public void lock() {
        int currentThreadNumber = getNumberForCurrentThread();
        slots[currentThreadNumber] = true;
        workingThreadNumber = currentThreadNumber;
        int otherThreadNumber = 1 - currentThreadNumber;
        while (workingThreadNumber == currentThreadNumber && slots[otherThreadNumber]) ;
    }

    @Override
    public void unlock() {
        slots[getNumberForCurrentThread()] = false;
    }

    private int getNumberForCurrentThread() {
        return counter.get() % 2;
    }
}
