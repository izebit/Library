package ru.izebit.algorithms.sort;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

/**
 * @author : artem
 *         <p>
 *         набор тестов для проверки корректности работы алгоритма сортировки
 */
@RunWith(value = Parameterized.class)
public class SortTest {


    private final List<Integer> list;
    private final List<Integer> original;

    public SortTest(Sort<Integer> sort, int size, int range) {
        original = getArray(size, range);
        list = new ArrayList<>();
        list.addAll(original);

        sort.sort(list);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> data = new ArrayList<>();
        data.add(new Object[]{new HeapSort<Integer>(), 100_000, Integer.MAX_VALUE});
        data.add(new Object[]{new HeapSort<Integer>(), 10_000, Integer.MAX_VALUE});
        data.add(new Object[]{new HeapSort<Integer>(), 10, Integer.MAX_VALUE});

        data.add(new Object[]{new MergeSort<Integer>(), 100_000, Integer.MAX_VALUE});
        data.add(new Object[]{new MergeSort<Integer>(), 10_000, Integer.MAX_VALUE});
        data.add(new Object[]{new MergeSort<Integer>(), 10, Integer.MAX_VALUE});


        data.add(new Object[]{new QuickSort<Integer>(), 100_000, Integer.MAX_VALUE});
        data.add(new Object[]{new QuickSort<Integer>(), 10_000, Integer.MAX_VALUE});
        data.add(new Object[]{new QuickSort<Integer>(), 10, Integer.MAX_VALUE});


        data.add(new Object[]{new BubbleSort<Integer>(), 10_000, Integer.MAX_VALUE});
        data.add(new Object[]{new BubbleSort<Integer>(), 1_000, Integer.MAX_VALUE});
        data.add(new Object[]{new BubbleSort<Integer>(), 10, Integer.MAX_VALUE});


        return data;
    }

    /**
     * создает list с произвольными числами
     *
     * @param size  размер массива
     * @param range верхняя граница чисел, которыми будет заполнен массив
     * @return сгенерированный массив
     */
    public static List<Integer> getArray(int size, int range) {
        List<Integer> list = new ArrayList<Integer>(size);
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < size; i++) {
            list.add(r.nextInt(range));
        }
        Collections.shuffle(list);
        return list;
    }

    @Test
    public void checkSort() {
        for (int i = 1; i < list.size(); i++)
            Assert.assertTrue(list.get(i - 1).compareTo(list.get(i)) <= 0);
    }

    public void checkContainElement() {
        Assert.assertTrue(original.size() != list.size());

        List<Integer> subList = new ArrayList<>(original);
        Collections.sort(subList);
        List<Integer> subSortList = new ArrayList<>(list);
        for (int i = 0; i < subList.size(); i++)
            Assert.assertTrue(subList.get(i).equals(subSortList.get(i)));
    }

}
