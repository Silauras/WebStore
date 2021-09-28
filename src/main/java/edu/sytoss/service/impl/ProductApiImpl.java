package edu.sytoss.service.impl;

import edu.sytoss.model.order.Order;
import edu.sytoss.model.product.*;
import edu.sytoss.model.user.UserAccount;
import edu.sytoss.repository.*;
import edu.sytoss.service.ProductApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class ProductApiImpl implements ProductApi {
    /* --------- REPOSITORIES --------- */
    @Autowired
    ProductCardRepository productCardRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductTemplateRepository productTemplateRepository;

    @Autowired
    CharacteristicTemplateRepository characteristicTemplateRepository;

    @Autowired
    CharacteristicRepository characteristicRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;

    /* --------- IMPLEMENTED METHODS --------- */

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    @Override
    public ProductCard findProductCardById(Long id) {
        return productCardRepository.findById(id);
    }

    @Transactional
    @Override
    public ProductCard findProductCardByIdWithCharacteristicsAndCategory(Long id) {
        return productCardRepository.findByIdWithCharacteristicsAndCategory(id);
    }

    /**
     * @param
     * @param
     * @return
     * @author Andrey Kolesnyk
     */
    @Transactional
    @Override
    public boolean updateProductStatus(Product product, Long orderId, String status) {
        try {
            Order order = orderRepository.findById(orderId);
            product.setStatus(status);
            product.setOrder(order);
            productRepository.save(product);
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    public List<ProductCard> findAllProductCards() {
        return productCardRepository.findAll();
    }


    @Override
    public List<ProductCard> findAllProductCardsByFilter(Long CategoryId, HashMap<String, List<String>> filter) {
        return null;
    }

    @Override
    public List<ProductCard> filterProductsByPrice(BigDecimal startPrice, BigDecimal endPrice, List<ProductCard> productCards) {
        return null;
    }

    @Override
    public Category findCategoryById(Long id) {
        Category category = categoryRepository.findById(id);
        return categoryRepository.findById(id);
    }


    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public ProductTemplate findProductCardTemplateById(Long id) {
        return productTemplateRepository.findById(id);
    }

    public List<ProductTemplate> findProductCardTemplateByCategoryId(long categoryId) {
        return productTemplateRepository.findAllByCategory_Id(categoryId);
    }

    public int countUniqueCharacteristicsPerCharacteristicTemplate(Long characteristicTemplateId) {
        List<Characteristic> characteristics = characteristicRepository.findAllByTemplate_Id(characteristicTemplateId);
        Set<String> uniqueNames = new HashSet<>();
        for (Characteristic characteristic : characteristics) {
            uniqueNames.add(characteristic.getName());
        }
        return uniqueNames.size();
    }

    public List<CharacteristicTemplate> findCharacteristicTemplateByProductTemplateId(Long productTemplateId) {
        return characteristicTemplateRepository.findAllByProductTemplateId(productTemplateId);
    }

    public List<Characteristic> findCharacteristicByTemplate(Long characteristicTemplateId) {
        return characteristicRepository.findAllByTemplate_Id(characteristicTemplateId);
    }

    @Transactional
    @Override
    public List<Characteristic> findCharacteristicsPerCategory(Long categoryId) {
        List<Characteristic> characteristics = new ArrayList<>();
        Category category = categoryRepository.findById(categoryId);
        for (ProductTemplate productTemplate : category.getProductTemplates()) {
            for (ProductCard productCard : productTemplate.getProductCards()) {
                characteristics.addAll(productCard.getCharacteristics());
            }
        }
        return characteristics;
    }
}
