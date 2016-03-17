package ru.izebit.algorithms.string;

/**
 * Date: 11/1/12
 * Time: 12:44 AM
 *
 * @author Artem Konovalov
 */
public class ZFunction {
    /**
     * строит z функцию для заданой строки
     *
     * @param str строка функцию необходимо построить
     * @return z функция
     */
    public static int[] invoke(String str) {
        int[] z = new int[str.length()];
        z[0] = str.length();
        int r = 0, l = 0;
        for (int i = 1; i < str.length(); i++) {
            if (i > r) {
                int j;
                for (j = 0; (i + j) < str.length() && str.charAt(j) == str.charAt(i + j); j++) ;
                z[i] = j;
                l = i;
                r = i + j - 1;
            } else {
                if (z[i - l] < r - i + 1) {
                    z[i] = z[i - l];
                } else {
                    int j;
                    for (j = 1; (r + j) < str.length() && str.charAt(r + j) == str.charAt(r - i + j); j++) ;
                    z[i] = r - i + j;
                    l = i;
                    r = r + j - 1;
                }
            }

        }
        return z;
    }
}
