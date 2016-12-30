package ru.izebit.structs.tree;

import java.util.Arrays;

/**
 * @author Artem Konovalov
 *         date 4/4/15
 *         дерево отрезков, для подсчета суммы для произвольного интервала
 * @see <a href="https://en.wikipedia.org/wiki/Segment_tree">segment tree</>
 */
public class SegmentTree {
    private final int[] array;
    private final int[] values;


    public SegmentTree(int[] array) {
        this.array = Arrays.copyOf(array, array.length);
        this.values = new int[array.length * 4];
        build(0, array.length, 1);
    }

    public SegmentTree(int size) {
        this.array = new int[size];
        this.values = new int[size * 4];
    }

    /**
     * изменяет на заданое значение
     *
     * @param index индекс изменяемого элемента
     * @param value значение
     */
    public void set(int index, int value) {
        values[1] = set(index, value, 0, array.length, 1);
    }

    private int set(int index, int value, int left, int right, int vertexNumber) {
        if (left == right) {
            return values[vertexNumber] = value;
        }
        int middle = (left + right) >>> 1;
        if (index <= middle) {
            return values[vertexNumber] = (set(index, value, left, middle, vertexNumber * 2) + values[vertexNumber * 2 + 1]);
        } else {
            return values[vertexNumber] = (values[vertexNumber * 2] + set(index, value, middle + 1, right, vertexNumber * 2 + 1));
        }
    }

    /**
     * возращает сумму для произвольного интервала массива
     *
     * @param begin начало интервала
     * @param end   индекс конца интервала
     * @return сумма элементов
     */
    public int getSum(int begin, int end) {
        return getSum(begin, end, 1, 0, array.length);
    }

    private int getSum(int begin, int end, int vertexNumber, int beginSegment, int endSegment) {
        if (begin > end) {
            return 0;
        }
        if (begin == beginSegment && end == endSegment) {
            return values[vertexNumber];
        }
        int middle = (beginSegment + endSegment) >>> 1;
        return getSum(begin, Math.min(end, middle), vertexNumber * 2, beginSegment, middle) +
                getSum(Math.max(begin, middle + 1), end, vertexNumber * 2 + 1, middle + 1, endSegment);
    }

    private int build(int left, int right, int vertexNumber) {
        if (left == right) {
            if (left >= array.length) {
                return 0;
            }
            return values[vertexNumber] = array[left];
        }
        int middle = (left + right) >>> 1;
        int leftValue = build(left, middle, vertexNumber * 2);
        int rightValue = build(middle + 1, right, vertexNumber * 2 + 1);
        return values[vertexNumber] = leftValue + rightValue;
    }
}
