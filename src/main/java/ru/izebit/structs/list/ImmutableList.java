package ru.izebit.structs.list;


import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * аналог scala collection.immutable.List
 *
 * @param <T> тип содержащихся элементов
 */
public abstract class ImmutableList<T> {
    public final int size;

    protected ImmutableList(int size) {
        this.size = size;
    }

    @SuppressWarnings("unchecked")
    public static <T> ImmutableList<T> empty() {
        return (ImmutableList<T>) Empty.EMPTY;
    }

    /**
     * добавление элемента
     *
     * @param element добавляемый элемент
     * @return новый список содержащий элемент
     */
    public abstract ImmutableList<T> add(T element);

    /**
     * удаление элемента
     *
     * @param element элемент
     * @return новый список не содержащий данный элемент
     */
    public abstract ImmutableList<T> remove(T element);

    /**
     * удаление элемента по индексу
     *
     * @param index индекс удаляемого элемента
     * @return новый список без элемента с заданным индексом
     */
    public ImmutableList<T> remove(int index) {
        if (index < 0)
            throw new IllegalArgumentException("index must be more 0");

        return innerRemove(index);
    }

    protected abstract ImmutableList<T> innerRemove(int index);

    /**
     * проверка содержания элемента в списке
     *
     * @param element элементв
     * @return true если элемент содержится иначе false
     */
    public abstract boolean contains(T element);

    /**
     * объединение двух списков
     *
     * @param other другой список
     * @return список содержащий элементы из исходного списка и other
     */
    public abstract ImmutableList<T> union(ImmutableList<T> other);

    /**
     * интерация по списку
     *
     * @param consumer функция для работы с элементами
     */
    public void foreach(Consumer<? super T> consumer) {
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private static class Empty<T> extends ImmutableList<T> {

        private static final Empty<?> EMPTY = new Empty<>();

        private Empty() {
            super(0);
        }

        @Override
        public ImmutableList<T> add(T element) {
            return new Cons<>(element, empty());
        }

        @Override
        public ImmutableList<T> remove(T element) {
            return this;
        }

        @Override
        public ImmutableList<T> innerRemove(int index) {
            return this;
        }

        @Override
        public boolean contains(T element) {
            return false;
        }

        @Override
        public ImmutableList<T> union(ImmutableList<T> other) {
            return other;
        }

    }

    public static class Cons<T> extends ImmutableList<T> {
        public final T head;
        public final ImmutableList<T> tail;

        public Cons(T head, ImmutableList<T> tail) {
            super(1 + tail.size);
            this.head = head;
            this.tail = tail;
        }

        @Override
        public ImmutableList<T> add(T element) {
            return new Cons<>(head, tail.add(element));
        }

        @Override
        public ImmutableList<T> remove(T element) {
            if (head.equals(element))
                if (tail.isEmpty())
                    return empty();
                else {
                    Cons<T> t = (Cons<T>) tail;
                    return new Cons<>(t.head, t.tail);
                }
            else
                return new Cons<>(head, tail.remove(element));
        }

        @Override
        public ImmutableList<T> innerRemove(int index) {

            BiFunction<Integer, ImmutableList<T>, ImmutableList<T>> recursion = (idx, list) -> {
                if (idx.intValue() == 0) {
                    if (list.isEmpty())
                        return empty();

                    Cons<T> cons = (Cons<T>) list;
                    return cons.remove(cons.head);
                } else {

                    if (list.isEmpty())
                        return list;

                    Cons<T> cons = (Cons<T>) list;
                    return new Cons<>(cons.head, cons.tail.remove(--idx));
                }
            };


            return recursion.apply(index, this);
        }

        @Override
        public boolean contains(T element) {
            return head.equals(element) || tail.contains(element);
        }

        @Override
        public ImmutableList<T> union(ImmutableList<T> other) {
            if (other.isEmpty())
                return this;

            Function<ImmutableList<T>, ImmutableList<T>> recursion = list -> {
                if (list.isEmpty())
                    return other;

                @SuppressWarnings("unchecked")
                Cons<T> cons = (Cons<T>) list;
                return new Cons<>(cons.head, cons.tail.union(other));
            };


            return recursion.apply(this);
        }

        @Override
        public void foreach(Consumer<? super T> consumer) {
            consumer.accept(head);
            tail.foreach(consumer);
        }
    }
}
