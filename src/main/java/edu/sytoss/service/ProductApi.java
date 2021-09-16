package edu.sytoss.service;

import edu.sytoss.model.product.*;

import java.util.List;

public interface ProductApi {

    /* --------- PRODUCTS --------- */
    Product findProductById(Long id);

    List<Product> findAllProducts();

    /* --------- CATEGORY --------- */
    Category findCategoryById(Long id);

    List<Category> findAllCategories();

    /* --------- PRODUCT TEMPLATE --------- */
    ProductTemplate findProductTemplateById(Long id);

    List<ProductTemplate> findProductTemplateByCategoryId(long categoryId);

    /* --------- CHARACTERISTIC TEMPLATE --------- */
    int countUniqueCharacteristicsPerCharacteristicTemplate(Long characteristicTemplateId);

    List<CharacteristicTemplate> findCharacteristicTemplateByProductTemplateId(Long productTemplateId);

    /* --------- CHARACTERISTIC --------- */
    List<Characteristic> findCharacteristicByTemplate(Long characteristicTemplateId);

    List<Characteristic> loadCategoryWithAllCharacteristics(Long categoryId);
}
