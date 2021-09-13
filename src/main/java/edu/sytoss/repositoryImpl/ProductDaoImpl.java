package edu.sytoss.repositoryImpl;

import edu.sytoss.model.product.Product;
import edu.sytoss.model.user.UserAccount;
import edu.sytoss.repository.ProductDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDaoImpl extends AbstractDao<Long, Product> implements ProductDao {
    @Override
    public Product findById(Long id) {
        return getByKey(id);
    }

    @Override
    public void save(Product product) {
        persist(product);
    }

    @Override
    public List<Product> findAllProducts() {
        return getAll(Product.class);
    }
}
