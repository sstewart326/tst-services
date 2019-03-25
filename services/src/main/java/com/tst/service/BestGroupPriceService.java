package com.tst.service;

import com.tst.domain.CabinPrice;
import com.tst.domain.GroupPrice;
import com.tst.domain.Rate;
import com.tst.service.utility.CabinPriceComparator;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *  Service class to get best group prices
 *
 * @author Shawn Stewart
 */
public class BestGroupPriceService {

    /**
     * Gets list of best group prices
     *
     * Creates a HashMap for key value lookup with rateCode as key and rate group as value
     * Sorts prices in order from smallest to largest
     * Loops through sorted prices and generates list of GroupPrices
     * Returns bestGroupPrices list
     *
     * @param rates
     * @param prices
     * @return
     */
    public List<GroupPrice> getBestGroupPrices(List<Rate> rates, List<CabinPrice> prices) {
        if(CollectionUtils.isEmpty(rates) || CollectionUtils.isEmpty(prices)) {
            return Collections.emptyList();
        }

        Map<String, String> rateMap = new HashMap<>();
        rates.forEach(rate -> {
            if (rate == null) {
                return;
            }
            rateMap.put(rate.getRateCode(), rate.getRateGroup());
        });

        SortedSet<CabinPrice> sortedCabinPrices = new TreeSet<>(new CabinPriceComparator());
        prices.forEach(cabinPrice -> {
            if (cabinPrice == null) {
                return;
            }
            sortedCabinPrices.add(cabinPrice);
        });

        List<GroupPrice> bestGroupPrices = new ArrayList<>();
        sortedCabinPrices.forEach(cabinPrice -> {
            String rateGroup = rateMap.get(cabinPrice.getRateCode());
            if (rateGroup == null) {
                return;
            }
            GroupPrice groupPrice = mapGroupPrice(cabinPrice, rateGroup);
            bestGroupPrices.add(groupPrice);
        });
        return bestGroupPrices;
    }

    private GroupPrice mapGroupPrice(CabinPrice cabinPrice, String rateGroup) {
        GroupPrice groupPrice = new GroupPrice();
        groupPrice.setPrice(cabinPrice.getPrice());
        groupPrice.setCabinCode(cabinPrice.getCabinCode());
        groupPrice.setRateGroup(rateGroup);
        groupPrice.setRateCode(cabinPrice.getRateCode());
        return groupPrice;
    }
}
