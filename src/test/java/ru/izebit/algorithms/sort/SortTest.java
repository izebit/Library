package ru.izebit.algorithms.sort;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

/**
 * набор тестов для проверки корректности работы алгоритма сортировки
 *
 * @author : Artem Lonovalov
 */
@RunWith(value = Parameterized.class)
public class SortTest {

    // пропускаем тесты которые были проинициализированы некорректными параметрами
    private final boolean skipTests;


    private final List<Integer> list;
    private final List<Integer> original;


    public SortTest(Sort<Integer> sort, int size, int range) {
        original = getArray(size, range);

        list = new ArrayList<>(original);


        boolean isWrongParams = false;
        try {
            sort.sort(list);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
            isWrongParams = true;
        }

        skipTests = isWrongParams;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> data = new ArrayList<>();
        data.add(new Object[]{new HeapSort<Integer>(), 10_000, Integer.MAX_VALUE});
        data.add(new Object[]{new HeapSort<Integer>(), 100_000, Integer.MAX_VALUE});
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

        data.add(new Object[]{new InsertSort<Integer>(), 10_000, Integer.MAX_VALUE});
        data.add(new Object[]{new InsertSort<Integer>(), 1_000, Integer.MAX_VALUE});
        data.add(new Object[]{new InsertSort<Integer>(), 10, Integer.MAX_VALUE});


        data.add(new Object[]{new AgitationSort<Integer>(), 1_000, Integer.MAX_VALUE});
        data.add(new Object[]{new AgitationSort<Integer>(), 10, Integer.MAX_VALUE});

        data.add(new Object[]{new ShellSort<Integer>(), 10_000, Integer.MAX_VALUE});
        data.add(new Object[]{new ShellSort<Integer>(), 1_000, Integer.MAX_VALUE});
        data.add(new Object[]{new ShellSort<Integer>(), 10, Integer.MAX_VALUE});


        data.add(new Object[]{new CountSort(), 10_000, 1_000_000});
        data.add(new Object[]{new CountSort(), 1_000, 1_000_000});
        data.add(new Object[]{new CountSort(), 10, 1_000_000});
        data.add(new Object[]{new CountSort(), 10, -1_000_000});

        data.add(new Object[]{new RadixSort(), 10_000, 1_000_000});
        data.add(new Object[]{new RadixSort(), 1_000, 1_000_000});
        data.add(new Object[]{new RadixSort(), 10, 1_000_000});
        data.add(new Object[]{new RadixSort(), 10, -1_000_000});

        return data;
    }

    /**
     * создает list с произвольными числами
     *
     * @param size  размер массива
     * @param range if > 0 массив заполняется числами из диапазона от 0 до range, иначе -range до 0
     * @return сгенерированный массив
     */
    public static List<Integer> getArray(int size, int range) {
        List<Integer> list = new ArrayList<>(size);
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < size; i++)
            list.add((int) Math.signum(range) * r.nextInt(Math.abs(range)));

        Collections.shuffle(list);
        return list;
    }

    @Test
    public void checkSort() {
        if (skipTests) return;

        for (int i = 1; i < list.size(); i++)
            Assert.assertTrue(list.get(i - 1).compareTo(list.get(i)) <= 0);
    }

    @Test
    public void checkContainElement() {
        if (skipTests) return;

        Assert.assertTrue(original.size() == list.size());

        List<Integer> subList = new ArrayList<>(original);
        Collections.sort(subList);
        List<Integer> subSortList = new ArrayList<>(list);
        for (int i = 0; i < subList.size(); i++)
            Assert.assertTrue(subList.get(i).equals(subSortList.get(i)));
    }
}
