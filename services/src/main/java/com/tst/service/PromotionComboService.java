package com.tst.service;

import com.tst.domain.Promotion;

import java.util.*;

public class PromotionComboService {

    public void combinablePromotions(String promotionCode) {

    }

    public void allCombinablePromotions(List<Promotion> allPromotions) {
        Map<String, Set<SortedSet<String>>> allPossibleCombinations = new HashMap<>();

        LinkedList<Promotion> promotions = new LinkedList<>();
        allPromotions.forEach(promotion -> promotions.add(promotion));

        for (int i = 0; i < promotions.size(); i++) {
            Set<SortedSet<String>> promotionalBundles = new HashSet<>();
            Promotion promotion = promotions.getFirst();

            SortedSet<String> currentPromotionBundles = null;
            Set<String> notCombinableWith = null;
            for(int k=1; k<promotions.size(); k++) {
                for (int j = 0; j < promotions.size(); j++) {
                    Promotion currentPromotion = promotions.get(j);
                    if (!isCombinable(notCombinableWith, currentPromotion.getCode())) {
                        continue;
                    }
                    if (currentPromotionBundles == null) {
                        currentPromotionBundles = new TreeSet<>();
                        notCombinableWith = new HashSet<>();
                    }
                    currentPromotionBundles.add(currentPromotion.getCode());
                    notCombinableWith.addAll(currentPromotion.getNotCombinableWith());

                    if (currentPromotionBundles.size() > 1 && !promotionalBundles.contains(currentPromotionBundles)) {
                        promotionalBundles.add(currentPromotionBundles);
                        //need to create new obj so we don't overwrite and so we don't have to loop through already iterated elements
                        SortedSet<String> temp = new TreeSet<>();
                        temp.addAll(currentPromotionBundles);
                        currentPromotionBundles = new TreeSet<>();
                        currentPromotionBundles.addAll(temp);
                    }
                }
                notCombinableWith.clear();
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
