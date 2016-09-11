package ru.izebit;


public final class FunctionUtils {

    @FunctionalInterface
    public interface TernaryFunction<T, E, U, R> {
        R apply(T t, E e, U u);
    }

    public static class Recursion<T> {
        public T function;
    }
}
