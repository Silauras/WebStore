package edu.sytoss.repository;

import edu.sytoss.model.product.ProductCard;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public class ProductCardFilterRepository {
    @PersistenceContext
    EntityManager em;

    /**
     * Finds product cards by specified parameters.
     * <p>
     * There are some reserved names for deeper searches:
     *     <ul>
     *         <li><b>startPrice</b> - value on 0 index will show minimum cost of product card</li>
     *         <li><b>endPrice</b> - value on 0 index will show maximum cost of product card</li>
     *         <li><b>productStatus</b> - if not exists show all products,
     *         otherwise will show product only with given statuses
     *         <ul>
     *             <li><i>available</i></li>
     *             <li><i>small_amount</i></li>
     *             <li><i>not_available</i></li>
     *             <li><i>waiting</i></li>
     *             <li><i>out_of_production</i></li>
     *             <li><i>delete</i> - only for sellers, moderators and admin</li>
     *         </ul>
     *         </li>
     *        <li><b>sortType</b> - will sort by:
     *        <ul>
     *            <li><i>mostExpensive</i> - from most expensive to cheap</li>
     *            <li><i>mostCheap</i> - from most cheap to expensive</li>
     *            <li><i>mostCommented</i> - from productCard with biggest count of commentaries</li>
     *            <li><i>leastCommented</i> - count of comments will grow</li>
     *            <li><i>bestRated</i> - firstly check average rating then count of ratings</li>
     *            <li><i>bestSales</i> - from biggest sales to small</li>
     *            <li><i>popular</i> - firstly sort by purchases for last month then rating</li>
     *            check value on index 0
     *        </ul>
     *        </li>
     *     </ul>
     * </p>
     *
     * @param categoryId id for category which will be filtered
     *                   //   * @param params     map with names and values of for filtering
     * @return product cards that fit the search criteria
     * @author Oleg Pentsov
     */
    @Transactional
    public List<ProductCard> findProductCardsByFilter(Long categoryId, Map<String, List<String>> params) {

        String baseSQL = "select " +
                "distinct " +
                "pc.product_id, " +
                "pc.fullDescription, " +
                "pc.name, " +
                "pc.price, " +
                "pc.product_template, " +
                "pc.shortDescription, " +
                "pc.status " +
                "from product_card pc ";

        String joinBuilder = "inner join product_template pt on pc.product_template = pt.product_template_id " +
                "inner join category c on pt.category = c.category_id " +
                "inner join characteristic c2 on pc.product_id = c2.product " +
                "left join price p on pc.product_id = p.product ";

        StringBuilder whereBuilder = new StringBuilder("where c.category_id = :categoryId ");

        //price filtering
        BigDecimal startPrice = null;
        BigDecimal endPrice = null;
        if (params.containsKey("startPrice")) {
            startPrice = new BigDecimal(params.get("startPrice").get(0));
        }
        if (startPrice != null) {
            whereBuilder.append(" and if(p.unit = '%'," +
                    " pc.price * (100 - p.value) / 100," +
                    " pc.price - p.value) > :startPrice ");
        }
        if (params.containsKey("endPrice")) {
            endPrice = new BigDecimal(params.get("endPrice").get(0));
        }
        if (endPrice != null) {
            whereBuilder.append(" and if(p.unit = '%'," +
                    " pc.price * (100 - p.value) / 100," +
                    " pc.price - p.value) > :endPrice ");
        }

        //status filtering
        if (params.containsKey("productStatus")) {
            whereBuilder.append(" and ( ");
            int countOfStatuses = params.get("productStatus").size() - 1;
            for (int i = 0; i <= countOfStatuses; i++) {
                if (i != 0 || i != countOfStatuses) {
                    whereBuilder.append(" or ");
                }
                whereBuilder.append("pc.status = :productStatus").append(i);
            }
            whereBuilder.append(") ");
        }

        //sorting
        if (params.containsKey("sortType")){
            whereBuilder.append(" order by ");
            switch (params.get("sortType").get(0)){
                case "mostExpensive":
                    whereBuilder.append(" if(p.unit = '%', pc.price * (100 - p.value) / 100, pc.price - p.value) desc ");
                    break;
                case "mostCheap":
                    whereBuilder.append(" if(p.unit = '%', pc.price * (100 - p.value) / 100, pc.price - p.value) ");
                    break;
                case "mostCommented":
                    break;
                default:
                    throw new IllegalArgumentException("Sorting Type is illegal");
            }
        }


        String queryBuilder =
                baseSQL +
                        joinBuilder +
                        whereBuilder;
        Query query = em.createNativeQuery(queryBuilder, ProductCard.class);

        //put categoryId
        query.setParameter("categoryId", categoryId);
        //put start price
        if (startPrice != null) {
            query.setParameter("startPrice", startPrice);
        }
        //put end price
        if (endPrice != null) {
            query.setParameter("endPrice", endPrice);
        }
        //put status
        if (params.containsKey("productStatus")) {
            for (int i = 0; i < params.get("productStatus").size(); i++) {
                query.setParameter("productStatus" + i, params.get("productStatus").get(i));
            }
        }

        return query.getResultList();
    }

}
