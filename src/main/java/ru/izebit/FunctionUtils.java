package ru.izebit;


public final class FunctionUtils {

    @FunctionalInterface
    public interface TernaryFunction<T, E, U, R> {
        R apply(T t, E e, U u);
    }

    @FunctionalInterface
    public interface Function {
        double invoke(double x);
    }

    public static class Recursion<T> {
        public T function;
    }
}
