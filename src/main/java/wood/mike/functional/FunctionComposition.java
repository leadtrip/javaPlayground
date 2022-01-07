package wood.mike.functional;

import java.util.function.Function;

/**
 * Examples of Functions being combined with other functions to create composed functions
 */
public class FunctionComposition {

    public static void main(String[] args) {

        // Function takes and returns a Double
        Function<Double, Double> discountPremium = price -> price * 0.8;

        // Function takes a Double and returns a String
        Function<Double, String> getPriceTag = price ->String.format("Discounted Price : %s ", price);

        // Function takes a Double and returns a String & is combination of previous 2 Functions chained with andThen
        Function<Double, String> getDiscountedPriceTag = discountPremium.andThen(getPriceTag);

        // Call the composed Function
        var discountedPrice = getDiscountedPriceTag.apply(100.0);
        System.out.println(discountedPrice);

        // Ordering of functions can be reversed by calling compose instead of andThen
        Function<Double, String> getDiscountedPriceTagCompose = getPriceTag.compose(discountPremium);
        var getDiscountedPriceTagReversed = getDiscountedPriceTagCompose.apply(100.0);
        System.out.println(getDiscountedPriceTagReversed);
    }
}
