package edu.sytoss.service;

import edu.sytoss.model.product.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public interface ProductApi {

    /* --------- PRODUCTS --------- */
    ProductCard findProductById(Long id);

    List<ProductCard> findAllProductCards();

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

}
