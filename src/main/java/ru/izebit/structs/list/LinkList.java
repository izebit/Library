package ru.izebit.structs.list;

/**
 * @author Artem Konovalov
 *         creation date  12/30/16.
 * @since 1.0
 */
public class LinkList<T> implements List<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;


    @SuppressWarnings("unchecked")
    public LinkList() {
        this.head = (Node<T>) Node.EMPTY;
        this.tail = (Node<T>) Node.EMPTY;
    }

    @Override
    public T add(T value) {
        Node<T> node = new Node<>(value);

        if (head == Node.EMPTY)
            head = node;
        else {
            Node<T> current = head;
            while (current.next != Node.EMPTY)
                current = current.next;

            current.next = node;
            current.next.prev = current;
        }

        size++;
        tail = node;

        return node.value;
    }

    @SuppressWarnings("unchecked")
    public Node<T> removeTail() {
        if (tail == Node.EMPTY)
            return null;

        Node<T> node = tail;
        if (tail == head)
            head = (Node<T>) Node.EMPTY;

        tail.prev.next = (Node<T>) Node.EMPTY;
        tail = node.prev;

        size--;

        return node;
    }

    public Node<T> addToHead(Node<T> node) {
        if (head == Node.EMPTY) {
            head = node;
            tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }

        size++;

        return node;
    }

    @Override
    public T remove(T value) {
        Node<T> currentNode = head;
        do {
            if (value.equals(currentNode.value))
                break;

            currentNode = currentNode.next;
        } while (currentNode.next != Node.EMPTY);

        if (value.equals(currentNode.value))
            return remove(currentNode);

        return null;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size)
            return null;

        Node<T> currentNode = head;
        int currentIndex = 0;
        while (currentNode != Node.EMPTY && currentIndex != index) {
            currentNode = currentNode.next;
            currentIndex++;
        }

        return remove(currentNode);
    }

    public T remove(Node<T> node) {
        if (node == head)
            head = node.next;

        node.prev.next = node.next;
        node.next.prev = node.prev;

        if (node == tail)
            tail = node.prev;

        size--;

        return node.value;
    }

    @Override
    public boolean contains(T value) {
        Node<T> currentNode = head;
        while (currentNode != Node.EMPTY)
            if (value.equals(currentNode.value))
                return true;
            else
                currentNode = currentNode.next;


        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        size = 0;
        head = (Node<T>) Node.EMPTY;
    }


    public static class Node<T> {
        private static final Node<?> EMPTY = new Node<>("EMPTY");

        public T value;
        Node<T> prev;
        Node<T> next;

        @SuppressWarnings("unchecked")
        public Node(T value) {
            this(value, (Node<T>) EMPTY, (Node<T>) EMPTY);
        }

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public String toString() {
            return value + "";
        }
    }
}
