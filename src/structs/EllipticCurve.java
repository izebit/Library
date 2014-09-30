package structs;

import java.math.BigInteger;

/**
 * Created with IntelliJ IDEA.
 * Date: 11/30/12
 * Time: 4:08 AM
 *
 * @author Artem Konovalov
 * реализация эллептической кривой вида y^2=x^3+ax+b (mod q) 
 */

public class EllipticCurve {
    public final BigInteger a;
    public final BigInteger q;

    private static final BigInteger THREE =BigInteger.valueOf(3);
    private static final BigInteger TWO =BigInteger.valueOf(2);

    public EllipticCurve(BigInteger a, BigInteger q) {
        this.a = a;
        this.q = q;
    }
    //точка эллиптической кривой
    public class Point {
        private BigInteger x;
        private BigInteger y;

        public Point(BigInteger x, BigInteger y) {
            this.x = x;
            this.y = y;
        }
        //умножение на число
        public Point multiplication(BigInteger k) throws HasNotInverseElement {
            String str=k.toString(2);
            Point[] array = new Point[str.length()];
            array[0] = this;
            for (int i = 1; i < array.length; i++) {
                array[i] = array[i - 1].add(array[i - 1]);
            }

            Point result = null;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(str.length() - i - 1) == '1') {
                    result = (result == null) ? array[i] : result.add(array[i]);
                }
            }
            return result;
        }
        //вычитание точек
        public Point subtraction(Point point) throws HasNotInverseElement {
            return add(new Point(point.x, point.y.negate()));
        }

        //сложение точек
        public Point add(Point point) throws HasNotInverseElement {
            if(this.x.equals(point.x) && this.y.equals(point.y.negate())){
                return new Point(null,null);
            }

            if (this.equals(point)) {
                return doublePoint();
            }

            BigInteger dx = this.x.subtract(point.x);
            BigInteger dy = this.y.subtract(point.y);

            dx = (dx.compareTo(BigInteger.ZERO) < 0) ? dx.add(q) : dx;
            dy = (dy.compareTo(BigInteger.ZERO) < 0) ? dy.add(q) : dy;

            BigInteger s;
            try {
                s = (dy.multiply(dx.modInverse(q))).mod(q);
            } catch (ArithmeticException e) {
                throw new HasNotInverseElement(dx);
            }
            s = (s.compareTo(BigInteger.ZERO) < 0) ? s.add(q) : s;

            BigInteger x3 = s.pow(2).subtract(this.x).subtract(point.x).mod(q);
            BigInteger y3 = s.multiply(this.x.subtract(x3)).subtract(this.y).mod(q);

            return new Point(x3, y3);
        }
        //удвоение точки 
        public Point doublePoint() throws HasNotInverseElement {
            BigInteger dy = THREE.multiply(this.x.pow(2)).add(a);
            BigInteger dx = TWO.multiply(this.y);

            dy = (dy.compareTo(BigInteger.ZERO) < 0) ? dy.add(q) : dy;
            dx = (dx.compareTo(BigInteger.ZERO) < 0) ? dx.add(q) : dx;

            BigInteger s;
            try {
                s = dy.multiply(dx.modInverse(q)).mod(q);
            } catch (ArithmeticException e) {
                throw new HasNotInverseElement(dx);
            }
            BigInteger x3 = s.pow(2).subtract(this.x.multiply(TWO)).mod(q);
            BigInteger y3 = s.multiply(this.x.subtract(x3)).subtract(this.y).mod(q);

            return new Point(x3, y3);
        }

        public String toString() {
            return x + " " + y;
        }

        public boolean equals(Object o) {
            Point point = (Point) o;
            return this.x.equals(point.x) && this.y.equals(point.y);
        }
    }
    //исключение появляющиеся когда невозможно найти обратный элемент в поле Fq
    public static class HasNotInverseElement extends Exception {
        public final BigInteger dx; //элемент для которого нельзя найти обратный элемент

        public HasNotInverseElement(BigInteger dx) {
            this.dx = dx;
        }
    }
}
