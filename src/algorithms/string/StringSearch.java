package algorithms.string;

/**
 * Created with IntelliJ IDEA.
 * @author : artem
 * Date: 18.08.12
 * Time: 17:53
 */
public interface StringSearch {
    /*
       Ищет индекс первого вхождения указанной подстроки в строку.
       Поиск производится с начала строки.
       @param text строка где производится поиск
       @param pattern подстрока которую надо найти
       @return индекс первого вхождения pattern в text, если такой подстроки нет, то -1
    */
    public int search(String text, String pattern);

    /*
       Ищет индекс первого вхождения указанной подстроки в строку с указанного индекса
       @param text строка где производится поиск
       @param pattern подстрока которую надо найти
       @param fromIndex индекс с которого начинается поиск
       @return индекс первого вхождения pattern в text, если такой подстроки нет, то -1
    */
    public int search(String text, String pattern, int fromIndex);
}
