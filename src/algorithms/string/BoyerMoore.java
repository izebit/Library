package algorithms.string;
/*
 алгоритм Бойера-Мура
 время работы в худшем о(n*m) на практике должно быть почти линейное
*/

public class BoyerMoore implements StringSearch {

    @Override
    public int search(String text,String pattern){
        return this.search(text,pattern,0);
    }

    @Override
    public int search(String text, String pattern, int fromIndex) {
        if (text.length() < pattern.length()) {
            return -1;
        }

        int[] offsetTable = new int[256];
        for (int i = 0; i < offsetTable.length; i++) {
            offsetTable[i] = pattern.length();
        }
        for (int i = 0; i < pattern.length()-1; i++) {
            offsetTable[hash(pattern.charAt(i))] = pattern.length() - i - 1;
        }


        int i = fromIndex;
        while (i <= text.length() - pattern.length()) {
            for (int j = pattern.length() - 1; j >= 0; j--) {
                if (pattern.charAt(j) == text.charAt(i + j)) {
                    if (j == 0) {
                        return i;
                    }
                } else {
                    i += offsetTable[hash(text.charAt(i + pattern.length() - 1))];
                    break;
                }
            }
        }
        return -1;
    }

    private static int hash(char c) {
        return c & 0xFF;
    }
}