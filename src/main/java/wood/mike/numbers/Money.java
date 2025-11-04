package wood.mike.numbers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

public final class Money implements Comparable<Money> {

    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING = RoundingMode.HALF_EVEN;

    private final BigDecimal amount;
    private final Currency currency;

    private Money(BigDecimal amount, Currency currency) {
        this.amount = amount.setScale(SCALE, ROUNDING);
        this.currency = Objects.requireNonNull(currency, "Currency must not be null");
    }

    public static Money of(BigDecimal amount, Currency currency) {
        return new Money(amount, currency);
    }

    public static Money of(double value, Currency currency) {
        return new Money(BigDecimal.valueOf(value), currency);
    }

    public static Money of(long value, Currency currency) {
        return new Money(BigDecimal.valueOf(value).divide(BigDecimal.valueOf(100), 2, ROUNDING), currency);
    }

    public static Money of(String amount, String currencyCode) {
        return new Money(new BigDecimal(amount), Currency.getInstance(currencyCode));
    }

    public Money add(Money other) {
        checkCurrency(other);
        return new Money(this.amount.add(other.amount), this.currency);
    }

    public Money subtract(Money other) {
        checkCurrency(other);
        return new Money(this.amount.subtract(other.amount), this.currency);
    }

    public Money multiply(BigDecimal multiplier) {
        return new Money(this.amount.multiply(multiplier), this.currency);
    }

    public Money divide(BigDecimal divisor) {
        return new Money(this.amount.divide(divisor, SCALE, ROUNDING), this.currency);
    }

    @Override
    public int compareTo(Money other) {
        checkCurrency(other);
        return this.amount.compareTo(other.amount);
    }

    private void checkCurrency(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException(
                    STR."Cannot operate on different currencies: \{this.currency.getCurrencyCode()} vs \{other.currency.getCurrencyCode()}"
            );
        }
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money money)) return false;
        return amount.compareTo(money.amount) == 0 &&
                currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount.stripTrailingZeros(), currency);
    }

    @Override
    public String toString() {
        return STR."\{amount} \{currency.getCurrencyCode()}";
    }
}

