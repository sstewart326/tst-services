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
 *
 *
 */
public class BestGroupPriceService {

    /**
     *
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
        rates.forEach(rate -> rateMap.put(rate.getRateCode(), rate.getRateGroup()));

        SortedSet<CabinPrice> sortedCabinPrices = new TreeSet<>(new CabinPriceComparator());
        prices.forEach(cabinPrice -> sortedCabinPrices.add(cabinPrice));

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
