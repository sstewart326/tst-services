package com.tst.service.utility;

import com.tst.domain.GroupPrice;

import java.math.BigDecimal;
import java.util.Comparator;

public class GroupPriceComparator implements Comparator<GroupPrice> {

    @Override
    public int compare(GroupPrice groupPrice1, GroupPrice groupPrice2) {
        BigDecimal price1 = groupPrice1.getPrice();
        BigDecimal price2 = groupPrice2.getPrice();
        return price1.compareTo(price2);
    }

}
