package com.tst.service;

import com.tst.domain.CabinPrice;
import com.tst.domain.GroupPrice;
import com.tst.domain.Rate;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class BestGroupPriceServiceTest {

    BestGroupPriceService service = new BestGroupPriceService();

    @Test
    public void givenCurrentRatesAndCurrentCabinPrices_whenGetBestGroupPrices_thenReceiveOrderedListOfBestPrices() {
        List<GroupPrice> bestPrices = service.getBestGroupPrices(getCurrentRates(), getCurrentCabinPrices());
        BigDecimal previous = null;
        for (GroupPrice groupPrice : bestPrices) {
            if (previous == null) {
                previous = groupPrice.getPrice();
            } else {
                assertTrue(previous.compareTo(groupPrice.getPrice()) == -1);
                previous = groupPrice.getPrice();
            }
        }
    }

    @Test
    public void givenEmptyRates_whenGetBestGroupPrices_thenReceiveEmptyList() {
        List<GroupPrice> bestPrices = service.getBestGroupPrices(Collections.emptyList(), getCurrentCabinPrices());
        assertTrue(CollectionUtils.isEmpty(bestPrices));
    }

    @Test
    public void givenEmptyCabinPrices_whenGetBestGroupPrices_thenReceiveEmptyList() {
        List<GroupPrice> bestPrices = service.getBestGroupPrices(getCurrentRates(), Collections.emptyList());
        assertTrue(CollectionUtils.isEmpty(bestPrices));
    }

    @Test
    public void givenOneCabinPriceRateCodeIsNotFoundInRates_whenGetBestGroupPrices_thenIgnoreUnknownAndContinueProcessingOtherBestPrices() {
        List<CabinPrice> cabinPrices = getCurrentCabinPrices();
        CabinPrice cabinPrice = new CabinPrice("CA", "unkown_code", new BigDecimal(100));
        cabinPrices.add(cabinPrice);
        List<GroupPrice> bestPrices = service.getBestGroupPrices(getCurrentRates(), cabinPrices);
        bestPrices.forEach(groupPrice -> assertTrue(groupPrice.getPrice().compareTo(cabinPrice.getPrice()) != 0));
    }

        private List<Rate> getCurrentRates() {
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

    private List<CabinPrice> getCurrentCabinPrices() {
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