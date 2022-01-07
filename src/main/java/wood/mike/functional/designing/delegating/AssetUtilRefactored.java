package wood.mike.functional.designing.delegating;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * An optimal and notably slimmed down class from the badly designed AssetUtil, offering access to the Asset class
 */
public class AssetUtilRefactored {

    /**
     * The duplicated methods in AssetUtil have been replaced with this single method that takes a Predicate as an argument.
     * AKA the strategy pattern
     * We've separated a concern from a method
     *
     * @param assets            - the list of Assets
     * @param assetSelector     - the predicate to filter on
     */
    public static int totalAssetValues(final List<Asset> assets,
                                       final Predicate<Asset> assetSelector) {
        return assets.stream()
                .filter(assetSelector)
                .mapToInt(Asset::getValue)
                .sum();
    }

    public static void main(final String[] args) {
        List<Asset> assets = Arrays.asList(
                new Asset(Asset.AssetType.BOND, 1000),
                new Asset(Asset.AssetType.BOND, 2000),
                new Asset(Asset.AssetType.STOCK, 3000),
                new Asset(Asset.AssetType.STOCK, 4000)
        );

        System.out.println("Total of all assets: " +
                totalAssetValues(assets, asset -> true));

        System.out.println("Total of bonds: " +
                totalAssetValues(assets, asset -> asset.getType() == Asset.AssetType.BOND));

        System.out.println("Total of stocks: " +
                totalAssetValues(assets, asset -> asset.getType() == Asset.AssetType.STOCK));
    }
}