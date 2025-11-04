package wood.mike.numbers;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalAsMoney {

    static void main() {
        new BigDecimalAsMoney();
    }

    public BigDecimalAsMoney() {
        minorUnitsToPoundsAndPence();
    }

    public void minorUnitsToPoundsAndPence() {
        long pennies = 1;
        printMoney(toPoundsAndPence(pennies)); // £0.01

        pennies = 99;
        printMoney(toPoundsAndPence(pennies)); // £0.99

        pennies = 12599;
        printMoney(toPoundsAndPence(pennies));  // £125.99

        pennies = 1234567;
        printMoney(toPoundsAndPence(pennies)); // £12345.67

        pennies = 123456789;
        printMoney(toPoundsAndPence(pennies)); // £1234567.89
    }

    private void printMoney(BigDecimal poundsAndPence) {
        System.out.printf("£%s\n", poundsAndPence);
    }

    private BigDecimal toPoundsAndPence(long minorUnits) {
        return BigDecimal.valueOf(minorUnits).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
}
