package wood.mike.numbers;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BigDecimalPlay {

    static void main() {
        new BigDecimalPlay();
    }

    private BigDecimalPlay() {
        create();
        add();
        subtract();
        multiply();
        divide();
        divideToIntegralValue();
        divideAndRemainder();
        values();
    }

    private void create() {
        new BigDecimal(1);
        new BigDecimal(1L);
        new BigDecimal(1.0);
        new BigDecimal("1.0");
        new BigDecimal("1.0", MathContext.DECIMAL64);
        new BigDecimal((double) 1, new MathContext(2, RoundingMode.HALF_UP));

        BigDecimal.valueOf(1.0);
        BigDecimal.valueOf((double) 1);
        BigDecimal.valueOf(1, 2);
    }

    private void add() {
        BigDecimal a = new BigDecimal("1");
        BigDecimal b = new BigDecimal("1");
        System.out.printf("%s add %s is %s%n",a, b, a.add(b));

        BigDecimal c = new BigDecimal("1.001");
        BigDecimal d = new BigDecimal("1.01");
        BigDecimal res = c.add(d,  new MathContext(3, RoundingMode.HALF_UP));
        System.out.printf("%s add %s with precision 3 is %s%n",c, d, res);
    }

    private void subtract() {
        BigDecimal a = new BigDecimal("1");
        BigDecimal b = new BigDecimal("1");
        System.out.printf("%s subtract %s is %s%n",a, b, a.subtract(b));

        BigDecimal c = new BigDecimal("1.001");
        BigDecimal d = new BigDecimal("1.01");
        BigDecimal res = c.subtract(d,  new MathContext(3, RoundingMode.HALF_UP));
        System.out.printf("%s subtract %s with precision 3 is %s%n",c, d, res);
    }

    private void multiply() {
        BigDecimal a = new BigDecimal("1");
        BigDecimal b = new BigDecimal("1");
        System.out.printf("%s multiplied by %s is %s%n",a, b, a.multiply(b));

        BigDecimal c = new BigDecimal("1.001");
        BigDecimal d = new BigDecimal("2");
        BigDecimal res = c.multiply(d,  new MathContext(3, RoundingMode.HALF_UP));
        System.out.printf("%s multiplied by %s with precision 3 is %s%n", c, d, res);
    }

    private void divide() {
        BigDecimal a = new BigDecimal("10");
        BigDecimal b = new BigDecimal("2");
        System.out.printf("%s divided by %s is %s%n", a, b, a.divide(b));

        BigDecimal c = new BigDecimal("10.5");
        BigDecimal d = new BigDecimal("2");
        BigDecimal res = c.divide(d, new MathContext(3, RoundingMode.HALF_UP));
        System.out.printf("%s divided by %s with precision 3 is %s%n", c,  d, res);

        BigDecimal resHalfUp = c.divide(d, RoundingMode.HALF_UP);
        System.out.printf("%s divided by %s rounded half up is %s%n",c, d, resHalfUp);

        BigDecimal e = new BigDecimal("10.51");
        BigDecimal f = new BigDecimal("2");
        BigDecimal resScaleCeil = e.divide(f, 2, RoundingMode.CEILING);
        System.out.printf("%s divided by %s scale 3 rounded ceiling is %s%n", e, f, resScaleCeil);
    }

    private void divideToIntegralValue() {
        BigDecimal a  = new BigDecimal("10.23");
        BigDecimal b = new BigDecimal("2");
        BigDecimal res = a.divideToIntegralValue(b);
        System.out.printf("%s divided by %s is %s%n", a, b, res);
    }

    private void divideAndRemainder() {
        BigDecimal a =  new BigDecimal("10.23");
        BigDecimal b = new BigDecimal("2");
        BigDecimal[] res = a.divideAndRemainder(b);
        System.out.printf("%s divided by %s is %s with remainder %s%n", a, b, res[0], res[1]);
    }

    private void values() {
        System.out.println("=== values ===");
        BigDecimal a = new BigDecimal(Integer.MAX_VALUE);
        System.out.println(a.intValue());
        System.out.println(a.longValue());
        System.out.println(a.doubleValue());
        System.out.println(a.floatValue());
        System.out.println(a.byteValue());
    }
}
