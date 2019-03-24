package com.tst.service.utility;

import com.tst.domain.CabinPrice;

import java.math.BigDecimal;
import java.util.Comparator;

public class CabinPriceComparator implements Comparator<CabinPrice> {

    @Override
    public int compare(CabinPrice cabinPrice1, CabinPrice cabinPrice2) {
        BigDecimal price1 = cabinPrice1.getPrice();
        BigDecimal price2 = cabinPrice2.getPrice();
        return price1.compareTo(price2);
    }
}
