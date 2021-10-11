package edu.sytoss.service.impl;

import edu.sytoss.model.order.Order;
import edu.sytoss.model.product.*;
import edu.sytoss.model.shop.Shop;
import edu.sytoss.repository.*;
import edu.sytoss.service.ProductApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ProductApiImpl implements ProductApi {
    /* --------- REPOSITORIES --------- */
    @Autowired
    ProductCardRepository productCardRepository;
    @Autowired
    KitRepository kitRepository;
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
    @Transactional
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
    public ProductCard findProductCardByIdWhitKits(Long id) {
        return productCardRepository.findByIdWithKits(id);
    }

    @Transactional
    @Override
    public ProductCard findProductCardByIdWithCharacteristicsAndCategory(Long id) {
        return productCardRepository.findByIdWithCharacteristicsAndCategory(id);
    }

    @Transactional
    @Override
    public List<ProductCard> findAllProductCards() {
        return productCardRepository.findAll();
    }

    @Transactional
    @Override
    public List<ProductCard> findAllProductCardsByFilter(Long CategoryId, HashMap<String, List<String>> filter) {
        return null;
    }

    @Transactional
    @Override
    public List<ProductCard> filterProductsByPrice(BigDecimal startPrice, BigDecimal endPrice, List<ProductCard> productCards) {
        return null;
    }

    @Transactional
    @Override
    public Category findCategoryById(Long id) {
        Category category = categoryRepository.findById(id);
        return categoryRepository.findById(id);
    }

    @Transactional
    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    public ProductTemplate findProductCardTemplateById(Long id) {
        return productTemplateRepository.findById(id);
    }

    @Transactional
    public List<ProductTemplate> findProductCardTemplateByCategoryId(long categoryId) {
        return productTemplateRepository.findAllByCategory_Id(categoryId);
    }

    @Transactional
    public int countUniqueCharacteristicsPerCharacteristicTemplate(Long characteristicTemplateId) {
        List<Characteristic> characteristics = characteristicRepository.findAllByTemplate_Id(characteristicTemplateId);
        Set<String> uniqueNames = new HashSet<>();
        for (Characteristic characteristic : characteristics) {
            uniqueNames.add(characteristic.getName());
        }
        return uniqueNames.size();
    }

    @Transactional
    public List<CharacteristicTemplate> findCharacteristicTemplateByProductTemplateId(Long productTemplateId) {
        return characteristicTemplateRepository.findAllByProductTemplateId(productTemplateId);
    }

    @Transactional
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

    @Transactional
    @Override
    public boolean updateProductForOrder(Product product, Long orderId, String status) {
        try {
            Order order = orderRepository.findById(orderId);
            product.setStatus(status);
            product.setOrder(order);
            productRepository.saveAndFlush(product);
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Transactional
    @Override
    public boolean updateProductSetKit(List<Product> products, Kit kit, String serialNumberKit) {
        try {
            for (Product product : products) {
                product.setKit(kit);
                product.setSerialNumberKit(serialNumberKit);
                productRepository.save(product);
            }
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Transactional
    @Override
    public List<Product> findAvailableProductsByProductCardWithShop(ProductCard productCard, Integer quantity) {
        ProductCard pC = productCardRepository.findProductCardByIdAndProductStatusWithShopAndProducts(productCard.getId(), "AVAILABLE");
        List<Product> availableProducts = pC.getProducts();
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            products.add(availableProducts.get(i));
            updateProductForOrder(availableProducts.get(i), null, "BLOCKED");
        }
        return products;
    }

    /*Dividing Products into orders for create different orders*/
    @Override
    public Map<Shop, List<Product>> dividingProductsIntoOrders(Map<ProductCard, Integer> shoppingCartWithCard,
                                                               Map<Kit, Integer> shoppingCartWithKit) {
        List<Product> products = new ArrayList<>();

        for (ProductCard productCard : shoppingCartWithCard.keySet()) {
            products.addAll(findAvailableProductsByProductCardWithShop(productCard, shoppingCartWithCard.get(productCard)));
        }

        for (Kit kit : shoppingCartWithKit.keySet()) {
            Set<ProductCard> productCards = kit.getProductCards();
            for (int i = 0; i < shoppingCartWithKit.get(kit); i++) {
                StringBuilder serialNumberKit = new StringBuilder(String.valueOf(kit.getId()));
                List<Product> productsInKit = new ArrayList<>();
                for (ProductCard productCard : productCards) {
                    Product product = findAvailableProductsByProductCardWithShop(productCard, 1).get(0);
                    productsInKit.add(product);
                    serialNumberKit.append("-").append(product.getId());
                }
                updateProductSetKit(productsInKit, kit, serialNumberKit.toString());
                products.addAll(productsInKit);
            }
        }

        Map<Shop, List<Product>> productByShop = new HashMap<>();
        for (Product product : products) {
            productByShop.put(product.getWarehouse().getOwner(), new ArrayList<Product>());
        }
        for (Product product : products) {
            for (Shop shop : productByShop.keySet()) {
                if (product.getWarehouse().getOwner().equals(shop)) {
                    List<Product> productsInOrder = productByShop.get(shop);
                    productsInOrder.add(product);
                    productByShop.put(shop, productsInOrder);
                }
            }
        }
        return productByShop;
    }

    @Transactional
    @Override
    public List<Kit> findKitByProductCard(long productCardId) {
        return kitRepository.findKitByProductCard(productCardId);
    }
}
