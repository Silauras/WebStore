package edu.sytoss.service;

import edu.sytoss.model.product.*;
import edu.sytoss.model.shop.Shop;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ProductApi {

    /* --------- PRODUCTS --------- */
    Product findById(Long id);

    ProductCard findProductCardById(Long id);

    ProductCard findProductCardByIdWhitKits(Long id);

    List<ProductCard> findAllProductCards();

    ProductCard findProductCardByIdWithCharacteristicsAndCategory(Long id);

    boolean updateProductForOrder(Product product, Long orderId, String status);

    boolean updateProductSetKit(List<Product> products, Kit kit, String serialNumberKit);
    /**
     * * @param filter
     * @return
     */
    List<ProductCard> findAllProductCardsByFilter(Long CategoryId, HashMap<String, List<String>> filter);

    List<ProductCard> filterProductsByPrice(BigDecimal startPrice, BigDecimal endPrice, List<ProductCard> productCards);

    /* --------- CATEGORY --------- */

    Category findCategoryById(Long id);

    List<Category> findAllCategories();
    /* --------- PRODUCT TEMPLATE --------- */
    /* --------- CHARACTERISTIC TEMPLATE --------- */
    /* --------- CHARACTERISTIC --------- */

    List<Characteristic> findCharacteristicsPerCategory(Long categoryId);
    /*-------------------Product---------------------*/
    List<Product> findAvailableProductsByProductCardWithShop(ProductCard productCard, Integer quantity);

    Map<Shop, List<Product>> dividingProductsIntoOrders(Map<ProductCard, Integer> shoppingCartWithCard,
                                                        Map<Kit, Integer> shoppingCartWithKit);

    List<Kit> findKitByProductCard(long productCardId);


}
