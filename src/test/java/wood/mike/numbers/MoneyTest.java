package wood.mike.numbers;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTest {

    static final Currency GBP = Currency.getInstance("GBP");

    @Test
    public void testAdd() {
        Money total = create(19.99).add(create(3.00));
        assertEquals(create(2299), total);
    }

    @Test
    public void testSubtract() {
        Money total = create(19.99).subtract(create(3.00));
        assertEquals(create(1699), total);
    }

    @Test
    public void testMultiply() {
        Money total = create(19.99).multiply(BigDecimal.valueOf(2));
        assertEquals(create(3998), total);
    }

    @Test
    public void testDivide() {
        Money total = create(19.99).divide(BigDecimal.valueOf(2));
        assertEquals(create(1000), total);
    }

    private Money create(double amount) {
        return Money.of(amount, GBP);
    }

    private  Money create(long amount) {
        return Money.of(amount, GBP);
    }
}
