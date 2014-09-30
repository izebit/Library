package other;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * @author : artem
 *
 * набор тестов для проверки корректности работы алгоритма сортировки
 */
public class TestForSort {
    public static <T extends Comparable<T>> void checkCorrect(List<T> original, List<T> sortedList) {
        System.out.print("проверка на отсортированности: ");
        if (checkSort(sortedList)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

        System.out.print("проверка присутствия всех элементов: ");
        if (checkContainElement(original, sortedList)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }

    /*
    проверяет список на отсортированность в естественном порядке
    @param array список для проверки
    @return результат проверки
     */
    public static <T extends Comparable<T>> boolean checkSort(List<T> list) {
        if (list.size() < 2) {
            return true;
        }
        boolean result = true;
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).compareTo(list.get(i)) > 0) {
                result = false;
                break;
            }
        }
        return result;
    }

    /*
   проверка на содержимость всех элементов после сортировки
   @param original исходный список
   @param sortedList отсортированый список
   @return если все хорошо то true, else false
    */
    public static <T extends Comparable<T>> boolean checkContainElement(List<T> original, List<T> sortedList) {
        if (original.size() != sortedList.size()) {
            return false;
        }

        boolean result = true;
        List<T> subList = new ArrayList<T>(original);
        Collections.sort(subList);
        List<T> subSortList = new ArrayList<T>(sortedList);
        for (int i = 0; i < subList.size(); i++) {
            if (!subList.get(i).equals(subSortList.get(i))) {
                result = false;
                break;
            }
        }
        return result;
    }

    /*
   создает list с произвольными числами
   @param size размер массива
   @param range верхняя граница чисел, которыми будет заполнен массив
   @return сгенерированный массив
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

}
