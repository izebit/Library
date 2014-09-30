package other;

/**
 * Created with IntelliJ IDEA.
 * Date: 22.09.12
 * Time: 0:12
 *
 * @author : Artem Konovalov
 */
public class InvalidTypeListException extends Exception {
    public InvalidTypeListException() {
        this("Неправильный тип массива");
    }

    public InvalidTypeListException(String msg) {
        super(msg);
    }
}
