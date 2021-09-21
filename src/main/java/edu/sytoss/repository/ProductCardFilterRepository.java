package edu.sytoss.repository;

import edu.sytoss.model.product.Price;
import edu.sytoss.model.product.ProductCard;
import edu.sytoss.model.product.ProductTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ProductCardFilterRepository {
    @PersistenceContext
    EntityManager em;

    /*
    startPrice must be on 0 index
    endPrice must be on 0 index


    */
    public List<ProductCard> findProductCardsByFilter(Long categoryId, Map<String, List<String>> params) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ProductCard> cq = cb.createQuery(ProductCard.class);


        Root<ProductCard> productCard = cq.from(ProductCard.class);
        List<Predicate> predicates = new ArrayList<>();

        //category filtering
        predicates.add(cb.equal(productCard.get("productTemplate").get("category"), categoryId));

        //Price filtering
        BigDecimal startPrice = null;
        BigDecimal endPrice = null;
        if (params.containsKey("startPrice")) {
            startPrice = new BigDecimal(params.get("startPrice").get(0));
        }
        if (params.containsKey("endPrice")) {
            endPrice = new BigDecimal(params.get("endPrice").get(0));
        }

        if (startPrice != null && endPrice != null) {
            predicates.add(cb.and(
                    cb.between(productCard.get("prices").<BigDecimal>get("value"), startPrice, endPrice),
                    cb.equal(productCard.join("prices").get("priceType"), "regular")));
        } else if (startPrice != null) {
            predicates.add(cb.and(
                    cb.greaterThan(productCard.join("prices").<BigDecimal>get("value"), startPrice),
                    cb.equal(productCard.join("prices").get("priceType"), "regular")));
        } else if (endPrice != null) {
            predicates.add(cb.and(
                    cb.lessThan(productCard.join("prices").<BigDecimal>get("value"), endPrice),
                    cb.equal(productCard.join("prices").get("priceType"), "regular")));
        }


        for (String key : params.keySet()) {
            if (key.equals("startPrice") || key.equals("endPrice"))
                continue;
            List<String> values = params.get(key);
            Predicate[] valuesPredicates = new Predicate[params.get(key).size()];
            for (int i = 0; i < valuesPredicates.length; i++) {
                valuesPredicates[i] = cb.and(cb.equal(productCard.join("characteristics").get("value"), values.get(i)),
                        cb.equal(productCard.join("characteristics").get("name"), key));
            }
            predicates.add(cb.or(valuesPredicates));

        }

        cq.where(predicates.toArray(new Predicate[0])).distinct(true);
        return em.createQuery(cq).getResultList();
    }

}
