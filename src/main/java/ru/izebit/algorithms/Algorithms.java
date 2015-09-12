package ru.izebit.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

/**
 * класс содержащий набор различных алгоритмов написанных собственноручно для их запоминания
 */
public class Algorithms {

    /**
     * возвращает индекс  заданного элемента  в списке
     *
     * @param subList   список элементов
     * @param element   элемент индекс которого следует найти
     * @param fromIndex начальный индекс с которого производится поиск
     * @param toIndex   индекс до которого производится поиск
     * @return индекс элемента, если его нет -1
     */
    public static <T extends Comparable<? super T>> int binarySearch(List<T> subList, T element, int fromIndex, int toIndex) {
        if (subList.size() == 0) {
            return -1;
        }

        List<T> list;
        if (subList instanceof RandomAccess) {
            list = new ArrayList<T>(subList);
        } else {
            list = subList;
        }

        if (list.get(fromIndex).compareTo(element) > 0) {
            return -(fromIndex + 1);
        }
        if (list.get(toIndex - 1).compareTo(element) < 0) {
            return -(toIndex + 1);
        }

        int left = fromIndex;
        int right = toIndex - 1;
        int middle = 0;
        while (left <= right) {
            middle = (left + right) >>> 1;
            if (list.get(middle).compareTo(element) > 0) {
                right = middle;
            } else {
                if (list.get(middle).compareTo(element) < 0) {
                    left = middle + 1;
                } else if (list.get(middle).compareTo(element) == 0) {
                    return middle;
                }
            }
        }
        return -(middle + 1);
    }

    /**
     * возвращает индекс указаного элемента который первый встрачается в списке
     *
     * @param list      список элементов
     * @param element   элемент индекс которого следует найти
     * @param fromIndex начальный индекс с которого производится поиск
     * @param toIndex   индекс до которого производится поиск
     * @return индекс элемента, если его нет -1
     */
    public static <T extends Comparable<? super T>> int lowerBounds(List<T> list, T element, int fromIndex, int toIndex) {
        int currentIndex = binarySearch(list, element, fromIndex, toIndex);
        if (currentIndex < 0) {
            return -1;
        }
        int bufIndex = currentIndex;
        while (bufIndex >= 0) {
            currentIndex = bufIndex;
            bufIndex = binarySearch(list, element, fromIndex, currentIndex - 1);
        }
        return currentIndex;
    }

    /**
     * возвращает индекс указанного элемента который последним встрачается в списке
     *
     * @param list      список элементов
     * @param element   элемент индекс которого следует найти
     * @param fromIndex начальный индекс с которого производится поиск
     * @param toIndex   индекс до которого производится поиск
     * @return индекс элемента, если его нет -1
     */
    public static <T extends Comparable<? super T>> int upperBounds(List<T> list, T element, int fromIndex, int toIndex) {
        int currentIndex = binarySearch(list, element, fromIndex, toIndex);
        if (currentIndex < 0) {
            return -1;
        }
        int bufIndex = currentIndex;
        while (bufIndex >= 0) {
            currentIndex = bufIndex;
            bufIndex = binarySearch(list, element, currentIndex + 1, toIndex);
        }
        return currentIndex;
    }

    /**
     * возвращает указанную порядковую статистику
     *
     * @param list       список элементов
     * @param orderIndex порядковый индекс элемента
     * @return возвращает элемент с указаным индексом если бы массив был отсортирван
     */
    public static <T extends Comparable<? super T>> T kth(List<T> list, int orderIndex) {
        return search(list, 0, list.size() - 1, orderIndex);
    }

    private static <T extends Comparable<? super T>> T search(List<T> list, int left, int right, int index) {
        T pivot = list.get((left + right) >>> 1);

        int lRange = left;
        int rRange = right;
        while (lRange < rRange) {
            while (lRange <= right && list.get(lRange).compareTo(pivot) < 0) {
                lRange++;
            }
            while (rRange >= left && list.get(rRange).compareTo(pivot) > 0) {
                rRange--;
            }
            if (lRange < rRange) {
                Collections.swap(list, rRange, lRange);
                rRange--;
                lRange++;
            }
        }
        if (left <= index && index < rRange) return search(list, left, rRange, index);
        if (lRange < index && index <= right) return search(list, lRange, right, index);
        return list.get(index);
    }


    /**
     * вещественнозначный поиск
     *
     * @param function монотонновозрастающая функция
     * @param value    значение функции
     * @param error    заданная погрешность
     * @return аргумент функции, при котором она дает значение value
     */
    public static double search(Function function, double value, double error) {
        double left = 0;
        while (function.invoke(left) > value) {
            left -= 10;
        }
        double right = 0;
        while (function.invoke(right) < value) {
            right += 10;
        }
        double currentValue;
        double middle;
        do {
            middle = left + (right - left) / 2;
            System.out.println(left + " " + middle + " " + right);
            currentValue = function.invoke(middle);
            if (currentValue > value) right = middle;
            else left = middle;
        } while (Math.abs(currentValue - value) > error);

        return middle;
    }

    /**
     * тернарный поиск осуществляет поиск минимума для унимодальной функции
     *
     * @param function   унимодальная функция
     * @param leftRange  левая граница области в которой будет осуществлен поиск
     * @param rightRange правая граница
     * @param error      разность левой и правой границы до которой будет происходить поиск
     * @return возвращает абсцисс в котором функция достигает своего минимума
     */
    public static double ternarySearch(Function function, double leftRange, double rightRange, double error) {
        double a = 0;
        double b = 0;
        while (Math.abs(rightRange - leftRange) > error) {
            a = leftRange + (rightRange - leftRange) * 4d / 10;
            b = leftRange + (rightRange - leftRange) * 6d / 10;
            if (function.invoke(a) > function.invoke(b)) {
                leftRange = a;
            } else {
                rightRange = b;
            }
        }
        return leftRange;
    }

    public static interface Function {
        public double invoke(double x);
    }
}

