package edu.sytoss.service.impl;

import edu.sytoss.model.order.Order;
import edu.sytoss.model.product.Kit;
import edu.sytoss.model.product.Price;
import edu.sytoss.model.product.Product;
import edu.sytoss.model.product.ProductCard;
import edu.sytoss.model.shop.Promotion;
import edu.sytoss.repository.OrderRepository;
import edu.sytoss.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * Service that calculate prices in:
 * <ul>
 * <li>{@link Order orders},</li>
 * <li>{@link Product products},</li>
 * <li>{@link ProductCard product cards},</li>
 * <li>{@link Kit kits}</li>
 * </ul>
 * </p>
 * <p>
 * Calculate with or without {@link Promotion promotions}
 * </p>
 *
 * @author Oleg Pentsov
 */
@Service
public class PriceCalculator {
    @Autowired
    ProductRepository productRepository;

    /**
     * Calculate summary price for {@link Order order} without any sales like
     * {@link Kit kits} and {@link Promotion promotions}
     *
     * <p>
     * Order must be with all products
     *
     * @param orderId id of order
     * @return summary price of {@link Order order} without sales
     * @author Oleg Pentsov
     */
    public BigDecimal calculatePriceForOrderWithoutSale(Long orderId) {
        BigDecimal result = BigDecimal.ZERO;
        for (Product product : productRepository.findByOrderId(orderId)) {
            //like result += price of product
            result = result.add(product.getProductCard().getPrice());
        }
        return result;
    }

    /**
     * Calculate summary price for {@link Order order} with all sales like
     * {@link Kit kits} and {@link Promotion promotions}
     *
     * <p>
     * If {@link Product product} in {@link Kit kit}: difference between summary price
     * of all {@link ProductCard product cards} (without sales) in {@link Kit kit}
     * subtracts from most expensive {@link Product product} in {@link Kit kit}
     * </p>
     * Order must be with all products, their kits with all product cards and all product cards with prices of all products
     *
     * @param orderId id of order
     * @return summary price of {@link Order order} with sales
     * @author Oleg Pentsov
     */
    public BigDecimal calculatePriceForOrderWithSale(Long orderId) {
        BigDecimal result = BigDecimal.ZERO;
        for (Product product : productRepository.findByOrderIdWithAllPriceFetches(orderId)) {
            System.out.println(product.getId() + ". " + calculateProductPrice(product));
            result = result.add(calculateProductPrice(product));
        }
        return result;
    }

    /**
     * @param product with product card and prices, and kit with all product cards of kit
     * @return price
     * @author Oleg Pentsov
     */
    public BigDecimal calculateProductPrice(Product product) {
        ProductCard productCard = product.getProductCard();
        if (product.getKit() != null) {
            return calculateProductPriceInKit(product);
        } else {
            return calculateProductCardPriceWithSale(productCard);
        }
    }

    /**
     * Calculate price for {@link Product products} in {@link Kit kits}
     * where difference between summary price
     * of all {@link ProductCard product cards} (without sales) in {@link Kit kit}
     * subtracts from most expensive {@link Product product} in {@link Kit kit}
     *
     * @param product with kit with loaded all product cards
     * @return price
     * @author Oleg Pentsov
     */
    public BigDecimal calculateProductPriceInKit(Product product) {
        ProductCard productCard = product.getProductCard();
        if (productCard.getId().equals(product.getKit().getMostExpensiveProduct().getId())) {
            return productCard.getPrice().subtract(calculateDifferenceInKit(product.getKit()));
        } else {
            return productCard.getPrice();
        }

    }

    /**
     * Calculate price of product card with sale from {@link Promotion promotions} with {@link Price prices}
     * <p>
     * Product card must have only current prices, their period of work won't check there
     *
     * @param productCard object with prices to calculate
     * @return price of productCard with all sales
     * @author Oleg Pentsov
     */
    public BigDecimal calculateProductCardPriceWithSale(ProductCard productCard) {
        if (productCard.getPrices() != null && productCard.getPrices().size() > 0) {
            BigDecimal minPrice = productCard.getPrice();
            for (Price price : productCard.getPrices()) {
                if (price.getUnit().equals("%")) {
                    //minPrice > minPrice - (minPrice * sale / 100)
                    if (minPrice.compareTo(minPrice.subtract(minPrice
                            .multiply(price.getValue())
                            .multiply(new BigDecimal("0.01")))) > 0) {
                        //minPrice = minPrice - (minPrice * sale / 100)
                        minPrice = minPrice.subtract(minPrice
                                .multiply(price.getValue())
                                .multiply(new BigDecimal("0.01")));
                    }
                } else {
                    // minPrice > minPrice - sale
                    if (minPrice.compareTo(minPrice.subtract(price.getValue())) > 0) {
                        minPrice = minPrice.subtract(price.getValue());
                    }
                }
            }
            return minPrice;
        } else {
            return productCard.getPrice();
        }
    }

    /**
     * Calculate difference in summary price of all product cards in kit, and kit price
     *
     * @param kit with all product cards
     * @return price difference
     * @author Oleg Pentsov
     */
    public BigDecimal calculateDifferenceInKit(Kit kit) {
        return calculateSummaryKitPrice(kit).subtract(kit.getPrice());
    }

    /**
     * Calculate summary price of all product cards in kit without any sales
     *
     * @param kit with all product cards
     * @return summary price
     * @author Oleg Pentsov
     */
    public BigDecimal calculateSummaryKitPrice(Kit kit) {
        BigDecimal summaryPrice = BigDecimal.ZERO;
        for (ProductCard productCard : kit.getProductCards()) {
            summaryPrice = summaryPrice.add(productCard.getPrice());
        }
        return summaryPrice;
    }
}