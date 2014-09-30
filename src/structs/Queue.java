package structs;

/*
саморасширяющаяся очередь основанная на массиве
 */

public class Queue<E> {
    private E[] array;
    private int head;
    private int tail;
    private int size;

    public Queue() {
        this(10);
    }

    public Queue(int capacity) {
        array = (E[]) new Object[capacity];
        head = 0;
        tail = 0;
        size = 0;
    }

    public void add(E element) {
        expansion();
        size++;
        tail = (tail + 1 == array.length) ? 0 : tail + 1;
        array[tail] = element;
    }

    public E poll() {
        size--;
        E value = array[head];
        head = (head + 1 == array.length) ? 0 : head + 1;
        return value;
    }

    public E peek() {
        return array[head];
    }

    private void expansion() {
        if (size + 1 > array.length) {
            E[] newArray = (E[]) new Object[size * 2];
            if (head < tail) {
                System.arraycopy(array, 0, newArray, 0, array.length);
            } else {
                System.arraycopy(array, head, newArray, 0, size - head);
                System.arraycopy(array, 0, newArray, size - head, head);
            }
            tail = array.length - 1;
            head = 0;
            array = newArray;
        }
    }
}
