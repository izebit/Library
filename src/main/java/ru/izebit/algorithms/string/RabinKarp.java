package ru.izebit.algorithms.string;

/**
 * @author Konovalov Artem
 *         Алгоритм Рабина-Карпа
 *         Время работы O(m*(n-m+1))
 */
public class RabinKarp implements StringSearch {
    private long q;

    @Override
    public int search(String text, String pattern) {
        return this.search(text, pattern, 0);
    }

    //TODO применить хороший rolling hash
    @Override
    public int search(String text, String pattern, int fromIndex) {
        q = (long) Math.pow(10, pattern.length());
        long hashPattern = hash(pattern);
        long hashString = hash(text.substring(0, pattern.length()));

        if (hashPattern == hashString && text.substring(0, pattern.length()).equals(pattern)) {
            return 0;
        }

        for (int i = pattern.length() + fromIndex; i < text.length(); i++) {
            hashString = ((hashString * 10) % q) + text.charAt(i) % 10;
            if (hashPattern == hashString) {
                if (text.substring(i - pattern.length() + 1, i + 1).equals(pattern)) {
                    return i - pattern.length() + 1;
                }
            }
        }
        return -1;
    }

    private long hash(String s) {
        long hs = 0;
        for (int i = 0; i < s.length(); i++) {
            long h = (long) (((int) s.charAt(i) % 10) * Math.pow(10, s.length() - i - 1));
            hs += h;
        }
        return hs % q;
    }
}