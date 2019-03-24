package com.tst.service;

import com.tst.domain.Promotion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PromotionComboService {

    public void allCombinablePromotions(List<Promotion> allPromotions) {
        Map<String, Set<List<String>>> allPossibleCombinations = new HashMap<>();

        LinkedList<Promotion> promotions = new LinkedList<>();
        allPromotions.forEach(promotion -> promotions.add(promotion));

        for (int i = 0; i < promotions.size(); i++) {
            Set<List<String>> promotionalBundles = new HashSet<>();
            Promotion promotion = promotions.getFirst();

            List<String> currentPromotionBundles = null;
            Set<String> notCombinableWith = null;
            for(int k=1; k<promotions.size(); k++) {
                for (int j = 0; j < promotions.size(); ) {
                    Promotion currentPromotion = promotions.get(j);
                    if (!isCombinable(notCombinableWith, currentPromotion.getCode())) {
                        j++;
                        continue;
                    }
                    if (currentPromotionBundles == null) {
                        currentPromotionBundles = new ArrayList<>();
                        notCombinableWith = new HashSet<>();
                    }
                    currentPromotionBundles.add(currentPromotion.getCode());
                    notCombinableWith.addAll(currentPromotion.getNotCombinableWith());

                    if (currentPromotionBundles.size() > 1 && !promotionalBundles.contains(currentPromotionBundles)) {
                        promotionalBundles.add(currentPromotionBundles);
                        currentPromotionBundles = null;
                        notCombinableWith.clear();
                        j = 0;
                        continue;
                    }
                    j++;
                }
                switchToTail(1, promotions);
                currentPromotionBundles = null;
                notCombinableWith.clear();
            }
            switchHeadToTail(promotions);
            allPossibleCombinations.put(promotion.getCode(), promotionalBundles);
        }
        System.out.println(allPossibleCombinations);
        //have a list of not possible combos
    }

    private boolean isCombinable(Set<String> notCombinableWith, String notCombinable) {
        if (notCombinableWith!=null && notCombinableWith.contains(notCombinable)) {
            return false;
        }
        return true;
    }

    private void switchToTail(int index, LinkedList<Promotion> promotions) {
        Promotion promotionToSwap = promotions.remove(index);
        promotions.addLast(promotionToSwap);
    }

    private void switchHeadToTail(LinkedList<Promotion> promotions) {
        Promotion first = promotions.getFirst();
        promotions.removeFirst();
        promotions.addLast(first);
    }

    private void addNotCombinables(Set<String> notCombinableWith, Promotion promotion) {
        notCombinableWith.addAll(promotion.getNotCombinableWith());
    }
}
