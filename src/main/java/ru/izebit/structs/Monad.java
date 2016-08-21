package ru.izebit.structs;


import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class Monad<T> {

    private final Supplier<? extends T> creator;
    private volatile T value;


    private Monad(Supplier<? extends T> creator) {
        this.creator = creator;
    }

    public static <T> Monad<? extends T> pure(Monad<? extends T> monad) {
        return monad;
    }

    public static <T> Monad<T> pure(T value) {
        return new Monad<>(() -> value);
    }

    public static <T> Monad<T> pure(Supplier<? extends T> supplier) {
        return new Monad<>(supplier);
    }

    public <K> Monad<K> bind(Function<T, Monad<K>> function) {
        return new Monad<>(() -> function.apply(get()).get());
    }


    public T get() {
        T result = value;

        if (result == null) {
            synchronized (this) {
                result = value;
                if (result == null) {
                    result = creator.get();
                    value = result;
                }
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Monad))
            return false;

        Monad<?> m = (Monad<?>) o;
        return Objects.equals(m.get(), this.get());
    }

    @Override
    public int hashCode() {
        return get().hashCode();
    }
}
