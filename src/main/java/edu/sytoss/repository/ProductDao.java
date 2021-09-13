package edu.sytoss.repository;

import edu.sytoss.model.product.Product;
import edu.sytoss.model.user.UserAccount;

import java.util.List;

public interface ProductDao{
    Product findById(Long id);

    void save(Product product);

    List<Product> findAllProducts();
}
