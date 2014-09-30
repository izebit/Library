package numericalMethods;

import structs.EllipticCurve;

import java.math.BigInteger;
import java.util.Random;

//TODO переделать, совсем нехорошо
public class Ecm {
    private static final BigInteger upperBound=new BigInteger("42");
    public static BigInteger ecm(BigInteger numeric, int attemptCount) {
        BigInteger divisor = null;
        for (int currentAttempt = 0; currentAttempt <= attemptCount && divisor == null; currentAttempt++) {
            divisor = lenstraAlgorithm(numeric, currentAttempt, attemptCount);
        }
        return divisor;
    }


    private static BigInteger lenstraAlgorithm(BigInteger n, int currentAttempt, int attemptCount) {
        BigInteger a, x, y, b;
        Random random = new Random(System.currentTimeMillis());
        do {
            int upperBound = (n.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) <= 0) ? n.intValue() - 1 : Integer.MAX_VALUE;
            a = BigInteger.valueOf(random.nextInt(upperBound));
            x = BigInteger.valueOf(random.nextInt(upperBound));
            y = BigInteger.valueOf(random.nextInt(upperBound));
            b = (y.pow(2).subtract(x.pow(3)).subtract(a.multiply(x))).mod(n);
        }
        while (n.gcd(BigInteger.valueOf(4).multiply(a.pow(3)).add(BigInteger.valueOf(27).multiply(b.pow(2)))).equals(n));

        EllipticCurve ellipticCurve = new EllipticCurve(a, n);
        EllipticCurve.Point point = ellipticCurve.new Point(x, y);




        BigInteger factorial = BigInteger.ONE;
        for (BigInteger i = BigInteger.ONE; i.compareTo(upperBound) <= 0; i = i.add(BigInteger.ONE)) {
            factorial = factorial.multiply(i);
            try {
                point = point.multiplication(i);
            } catch (EllipticCurve.HasNotInverseElement e) {
                return n.gcd(e.dx);
            }
        }
        return null;
    }


    private static BigInteger sqrt(BigInteger numeric) {
        final BigInteger two = BigInteger.ONE.add(BigInteger.ONE);
        BigInteger left = BigInteger.ZERO;
        BigInteger right = numeric;
        BigInteger middle = numeric;

        BigInteger prevValue;
        do {
            prevValue = middle;
            middle = (left.add(right)).divide(two);
            BigInteger pow = middle.pow(2);
            if (pow.equals(numeric)) {
                break;
            } else {
                if (pow.compareTo(numeric) > 0) {
                    right = middle;
                } else {
                    left = middle;
                }
            }
        } while (!prevValue.equals(middle));
        return middle;
    }
}
