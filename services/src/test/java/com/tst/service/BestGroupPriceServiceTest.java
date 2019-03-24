package com.tst.service;

import com.tst.domain.CabinPrice;
import com.tst.domain.Rate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BestGroupPriceServiceTest {

    BestGroupPriceService service = new BestGroupPriceService();

    public void givenCurrentRatesAndCurrentCabinPrices_whenGetBestGroupPrices_thenReceiveOrderedListOfBestPrices() {
        service.getBestGroupPrices( getCurrentRates(), getCurrentCabinPrices() );

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