package com.tst.service;

import com.tst.domain.Promotion;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.Assert.assertTrue;

public class PromotionComboServiceTest {
    
    PromotionComboService service = new PromotionComboService();
    
    @Test
    public void givenListOfAllPromotions_whenAllCombinablePromotionsIsCalled_thenReturnSetOfAllPosibleCombinations() {
        Set<SortedSet<String>> combos = service.allCombinablePromotions(getAllPromotions());
        assertTrue(combos.contains(getSortedSet("P1", "P2")));
        assertTrue(combos.contains(getSortedSet("P1", "P4", "P5")));
        assertTrue(combos.contains(getSortedSet("P2", "P3")));
        assertTrue(combos.contains(getSortedSet("P3", "P4", "P5")));
    }

    @Test
    public void givenListOfAllPromotionsAndPromoCode_whenCombinablePromotionsIsCalled_thenReturnAllCombosForSecifiedPromoCode() {
        Set<SortedSet<String>> combos = service.combinablePromotions(getAllPromotions(), "P1");
        assertTrue(combos.contains(getSortedSet("P1", "P2")));
        assertTrue(combos.contains(getSortedSet("P1", "P4")));
        assertTrue(combos.contains(getSortedSet("P1", "P5")));
        assertTrue(combos.contains(getSortedSet("P1", "P4", "P5")));
    }

    @Test
    public void givenAnEmptyListOfPromotions_whenAllCombinablePromotionsIsCalled_thenReceiveAnEmptySet() {
        Set<SortedSet<String>> combos = service.allCombinablePromotions(Collections.emptyList());
        assertTrue(CollectionUtils.isEmpty(combos));
    }

    @Test
    public void givenAnEmptyListOfPromotions_whenCombinablePromotionsIsCalled_thenReceiveAnEmptySet() {
        Set<SortedSet<String>> combos = service.combinablePromotions(Collections.emptyList(), "P1");
        assertTrue(CollectionUtils.isEmpty(combos));
    }

    @Test
    public void givenNullPromoCode_whenCombinablePromotionsIsCalled_thenReceiveAnEmptySet() {
        Set<SortedSet<String>> combos = service.combinablePromotions(Collections.emptyList(), null);
        assertTrue(CollectionUtils.isEmpty(combos));
    }

    @Test
    public void givenListOfAllPromotionsWithOneNullValue_whenAllCombinablePromotionsIsCalled_thenNullShouldBeIgnoredAndReturnSetOfAllPosibleCombinations() {
        List<Promotion> promos = getAllPromotions();
        promos.add(null);
        Set<SortedSet<String>> combos = service.allCombinablePromotions(promos);
        assertTrue(combos.contains(getSortedSet("P1", "P2")));
        assertTrue(combos.contains(getSortedSet("P1", "P4", "P5")));
        assertTrue(combos.contains(getSortedSet("P2", "P3")));
        assertTrue(combos.contains(getSortedSet("P3", "P4", "P5")));
    }

    private SortedSet<String> getSortedSet(String... args) {
        SortedSet<String> promoCombo = new TreeSet<>();
        promoCombo.addAll(Arrays.asList(args));
        return promoCombo;
    }

    private List<Promotion> getAllPromotions() {
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

    private Set<String> mapNotCombinableWith(String... notCombinableWith) {
        Set<String> notCombinableWithSet = new HashSet<>();
        for (String s : notCombinableWith) {
            notCombinableWithSet.add(s);
        }
        return notCombinableWithSet;
    }
}