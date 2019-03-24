package com.tst.service;

import com.sun.istack.internal.NotNull;
import com.tst.domain.CabinPrice;
import com.tst.domain.GroupPrice;
import com.tst.domain.Rate;
import com.tst.service.utility.CabinPriceComparator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class BestGroupPriceService {

    public List<GroupPrice> getBestGroupPrices(@NotNull List<Rate> rates, @NotNull List<CabinPrice> prices) {
        Map<String, String> rateMap = new HashMap<>();
        rates.forEach(rate -> rateMap.put(rate.getRateCode(), rate.getRateGroup()));

        SortedSet<CabinPrice> sortedCabinPrices = new TreeSet<>(new CabinPriceComparator());
        prices.forEach(cabinPrice -> sortedCabinPrices.add(cabinPrice));

        List<GroupPrice> bestGroupPrices = new ArrayList<>();
        sortedCabinPrices.forEach(cabinPrice -> {
            String rateGroup = rateMap.get(cabinPrice.getRateCode());
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
