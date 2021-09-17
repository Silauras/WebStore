package edu.sytoss.service.impl;

import edu.sytoss.model.product.*;
import edu.sytoss.repository.*;
import edu.sytoss.service.ProductApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    /* --------- IMPLEMENTED METHODS --------- */

    @Override
    public ProductCard findProductById(Long id) {
        return productCardRepository.findById(id);
    }

    @Override
    public List<ProductCard> findAllProductCards() {
        return productCardRepository.findAll();
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

    @Override
    public ProductTemplate findProductTemplateById(Long id) {
        return productTemplateRepository.findById(id);
    }

    @Override
    public List<ProductTemplate> findProductTemplateByCategoryId(long categoryId) {
        return productTemplateRepository.findAllByCategory_Id(categoryId);
    }

    @Override
    public int countUniqueCharacteristicsPerCharacteristicTemplate(Long characteristicTemplateId) {
        List<Characteristic> characteristics = characteristicRepository.findAllByTemplate_Id(characteristicTemplateId);
        Set<String> uniqueNames = new HashSet<>();
        for (Characteristic characteristic : characteristics) {
            uniqueNames.add(characteristic.getName());
        }
        return uniqueNames.size();
    }

    @Override
    public List<CharacteristicTemplate> findCharacteristicTemplateByProductTemplateId(Long productTemplateId) {
        return characteristicTemplateRepository.findAllByProductTemplateId(productTemplateId);
    }

    @Override
    public List<Characteristic> findCharacteristicByTemplate(Long characteristicTemplateId) {
        return characteristicRepository.findAllByTemplate_Id(characteristicTemplateId);
    }

    @Transactional
    @Override
    public List<Characteristic> findCharacteristicsPerCategory(Long categoryId) {
        List<Characteristic> characteristics = new ArrayList<>();
        Category category = categoryRepository.findById(categoryId);
        for (ProductTemplate productTemplate : category.getProductTemplates()) {
            for (ProductCard productCard: productTemplate.getProductCards()){
                characteristics.addAll(productCard.getCharacteristics());
            }
        }
        return characteristics;
    }
}
