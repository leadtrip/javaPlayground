package wood.mike.anything;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

public class CartServiceAllInOne {

    static void main(String[] args) {
        new CartServiceAllInOne().runDemo();
    }

    void runDemo() {
        CartItem ci1 = new CartItem("1", 1);
        CartItem ci2 = new CartItem("2", 2);
        Cart cart = new Cart(List.of(ci1, ci2));

        ProductRepository productRepository = new StandardProductRepository();
        ProductService productService = new StandardProductService(productRepository);
        CartService cartService = new StandardCartService(productService);

        BigDecimal cartTotal = cartService.sumCart(cart);

        System.out.println(cartTotal);
    }

    interface ProductRepository {
        Optional<Product> findForSku(String sku);
    }

    static class StandardProductRepository implements ProductRepository {
        static final List<Product> products =
                List.of(
                        new Product("1", "P1", BigDecimal.valueOf(100.00)),
                        new Product("2", "P2", BigDecimal.valueOf(200.00))
                );

        public Optional<Product> findForSku(String sku) {
            return products.stream().filter(product -> product.sku.equals(sku)).findFirst();
        }
    }

    interface ProductService {
        Product findForSku(String sku);
    }

    static class StandardProductService implements ProductService {

        private final ProductRepository productRepository;

        public StandardProductService(final ProductRepository productRepository) {
            this.productRepository = productRepository;
        }

        public Product findForSku(String sku) {
            return productRepository.findForSku(sku).orElseThrow(()->new ProductNotFoundException(sku));
        }
    }

    interface CartService {
        BigDecimal sumCart(Cart cart);
    }

    static class StandardCartService implements CartService {

        private final ProductService productService;

        StandardCartService(ProductService productService) {
            this.productService = productService;
        }

        public BigDecimal sumCart(Cart cart) {
            return cart.items.stream()
                    .map(cartItem -> {
                        Product p = productService.findForSku(cartItem.sku);
                        return p.price.multiply(BigDecimal.valueOf(cartItem.quantity));
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }

    static class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(String sku) {
            super(STR."product not found: \{sku}");
        }
    }

    record CartItem(String sku, Integer quantity) {}

    record Cart(List<CartItem> items) {}

    record Product(String sku, String name, BigDecimal price) {}

    static final class Money {
        static final Integer SCALE = 2;
        static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;

        private Currency currency;
        private BigDecimal amount;

        private Money() {}

        private Money(Currency currency, BigDecimal amount) {
            this.currency = currency;
            this.amount = amount.setScale(SCALE, ROUNDING_MODE);
        }

        public Money of(Currency currency, BigDecimal amount) {
            return new Money(currency, amount);
        }

        public Money of (Currency currency, double amount) {
            return new Money(currency, new BigDecimal(amount));
        }

        public Money add(Money other) {
            checkCurrency(other);
            return new Money(currency, amount.add(other.amount));
        }

        private void  checkCurrency(Money other) {
            if (currency.equals(other.currency)) {
                throw new UnsupportedOperationException("Currencies are not equal");
            }
        }
    }
}

