package com.tst;

import com.tst.domain.CabinPrice;
import com.tst.domain.GroupPrice;
import com.tst.domain.Promotion;
import com.tst.domain.Rate;
import com.tst.service.BestGroupPriceService;
import com.tst.service.PromotionComboService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;
import java.util.*;

/**
 * Main entry point into application
 *
 * @author Shawn Stewart
 */
@SpringBootApplication
@ComponentScan("com.tst")
public class CruiseApplication {


    private static BestGroupPriceService bestGroupPriceService = new BestGroupPriceService();
    private static PromotionComboService promotionComboService = new PromotionComboService();

    public static void main(String[] args) {
        SpringApplication.run(CruiseApplication.class, args);

        List<GroupPrice> bestPrices = bestGroupPriceService.getBestGroupPrices(getCurrentRates(), getCurrentCabinPrices());
        System.out.println("=======================================================================");
        System.out.println("Best Group Prices:");
        bestPrices.forEach(groupPrice -> System.out.println(groupPrice.toString()));
        System.out.println("=======================================================================\n\n\n\n");

        Set<SortedSet<String>> allCombinablePromotions = promotionComboService.allCombinablePromotions(getAllPromotions());
        System.out.println("=======================================================================");
        System.out.println("All Combinable Promotions:");
        allCombinablePromotions.forEach(combinablePromos -> System.out.println(combinablePromos.toString()));
        System.out.println("=======================================================================\n\n\n\n");

        Set<SortedSet<String>> combinablePromotionsForP1 = promotionComboService.combinablePromotions(getAllPromotions(), "P1");
        System.out.println("=======================================================================");
        System.out.println("All Combinable Promotions for P1");
        combinablePromotionsForP1.forEach(combinablePromos -> System.out.println(combinablePromos));
        System.out.println("=======================================================================\n\n\n\n");
    }

    private static List<Promotion> getAllPromotions() {
        Promotion p1 = new Promotion("P1", mapNotCombinableWith("P3"));
        Promotion p2 = new Promotion("P2", mapNotCombinableWith("P4", "P5"));
        Promotion p3 = new Promotion("P3", mapNotCombinableWith("P1"));
        Promotion p4 = new Promotion("P4", mapNotCombinableWith("P2"));
        Promotion p5 = new Promotion("P5", mapNotCombinableWith("P2"));
        List<Promotion> promotions = new ArrayList<>();
        promotions.add(p1);
        promotions.add(p2);
        promotions.add(p3);
        promotions.add(p4);
        promotions.add(p5);
        return promotions;
    }

    private static Set<String> mapNotCombinableWith(String... notCombinableWith) {
        Set<String> notCombinableWithSet = new HashSet<>();
        for (String s : notCombinableWith) {
            notCombinableWithSet.add(s);
        }
        return notCombinableWithSet;
    }

    private static List<Rate> getCurrentRates() {
        Rate rate1 = new Rate("M1", "Military");
        Rate rate2 = new Rate("M2", "Military");
        Rate rate3 = new Rate("S1", "Senior");
        Rate rate4 = new Rate("S2", "Senior");
        List<Rate> rates = new ArrayList<>();
        rates.add(rate1);
        rates.add(rate2);
        rates.add(rate3);
        rates.add(rate4);
        return rates;
    }

    private static List<CabinPrice> getCurrentCabinPrices() {
        CabinPrice price1 = new CabinPrice("CA", "M1", new BigDecimal(200));
        CabinPrice price2 = new CabinPrice("CA", "M2", new BigDecimal(250));
        CabinPrice price3 = new CabinPrice("CA", "S1", new BigDecimal(225));
        CabinPrice price4 = new CabinPrice("CA", "S2", new BigDecimal(260));
        CabinPrice price5 = new CabinPrice("CB", "M1", new BigDecimal(230));
        CabinPrice price6 = new CabinPrice("CB", "M2", new BigDecimal(260));
        CabinPrice price7 = new CabinPrice("CB", "S1", new BigDecimal(245));
        CabinPrice price8 = new CabinPrice("CB", "S2", new BigDecimal(270));

        List<CabinPrice> cabinPrices = new ArrayList<>();
        cabinPrices.add(price1);
        cabinPrices.add(price2);
        cabinPrices.add(price3);
        cabinPrices.add(price4);
        cabinPrices.add(price5);
        cabinPrices.add(price6);
        cabinPrices.add(price7);
        cabinPrices.add(price8);

        return cabinPrices;
    }

}
