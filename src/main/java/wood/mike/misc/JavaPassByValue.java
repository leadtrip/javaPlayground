package wood.mike.misc;

/**
 * Java is pass by value, NOT pass by reference
 */
public class JavaPassByValue {

    public static void main(String[] args) {
        new JavaPassByValue().test();
    }

    private void test() {
        var item = new Item("A1");

        updateReference(item);
        System.out.printf("%s%n", item.sku);     // sku is still A1

        updateSku(item);
        System.out.printf("%s%n", item.sku);     // sku is still C3
    }

    /**
     * It is not possible to update the value of the item variable itself
     */
    private void updateReference( Item item ) {
        item = new Item("B2");                                             // assign passed in Item to newly created Item
        System.out.printf("item sku is B2 %s%n", item.getSku().equals("B2"));   // the passed in Item's sku reflects the change in here but not once back in test method
    }

    /**
     * But we can of course update variables of the passed object
     */
    private void updateSku(Item item) {
        item.sku = "C3";
    }

    private static class Item {
        private String sku;

        public Item(String skuX) {
            sku = skuX;
        }

        public String getSku() {
            return sku;
        }
    }
}
