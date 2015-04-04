package ru.izebit.algorithms.string;

/**
 * Created with IntelliJ IDEA.
 * Date: 16.09.12
 * Time: 19:46
 *
 * @author Artem Konovalov
 *         алгоритм Кнута-Морриса-Пратта
 *         Время работы O(n);
 */

public class KnutMorrisPratt implements StringSearch {

    @Override
    public int search(String text, String pattern) {
        return search(text, pattern, 0);
    }

    @Override
    public int search(String text, String pattern, int fromIndex) {
        int[] p = new int[pattern.length()];
        p[0] = 0;
        int k = 0;
        for (int i = 1; i < pattern.length(); i++) {
            while (k > 0 && pattern.charAt(i) != pattern.charAt(k)) {
                k = p[k - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(k)) {
                k++;
            }
            p[i] = k;
        }

        k = 0;
        for (int i = fromIndex; i < text.length(); i++) {
            while (k > 0 && text.charAt(i) != pattern.charAt(k)) {
                k = p[k - 1];
            }
            if (text.charAt(i) == pattern.charAt(k)) {
                k++;
            }

            if (pattern.length() == k) {
                return i - pattern.length() + 2;
                //  если требуется найти все вхождения k = p[k-1];
            }
        }
        return -1;
    }
}
