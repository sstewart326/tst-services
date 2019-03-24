package com.tst.service;

import com.tst.domain.Promotion;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 */
public class PromotionComboService {

    /**
     *
     *
     * @param promotions
     * @param promotionCode
     * @return
     */
    public Set<SortedSet<String>> combinablePromotions(List<Promotion> promotions, String promotionCode) {
        if(CollectionUtils.isEmpty(promotions) || promotionCode == null) {
            return Collections.emptySet();
        }

        Map<String, Set<SortedSet<String>>> combinationsPerPromoCode = new HashMap<>();
        Set<SortedSet<String>> allPromotions = allCombinablePromotions(promotions);
        allPromotions.forEach(bundle -> {
            bundle.forEach(promotion -> {
                if (!combinationsPerPromoCode.containsKey(promotion)) {
                    Set<SortedSet<String>> bundles = new HashSet<>();
                    bundles.add(bundle);
                    combinationsPerPromoCode.put(promotion, bundles);
                } else {
                    Set<SortedSet<String>> bundles = combinationsPerPromoCode.get(promotion);
                    if(!bundles.contains(bundle)) {
                        bundles.add(bundle);
                    }
                }
            });
        });
        return combinationsPerPromoCode.get(promotionCode);
    }

    /**
     *
     *
     * @param allPromotions
     * @return
     */
    public Set<SortedSet<String>> allCombinablePromotions(List<Promotion> allPromotions) {
        if(CollectionUtils.isEmpty(allPromotions)) {
            return Collections.emptySet();
        }

        //convert list to linkedlist for higher efficiency
        LinkedList<Promotion> promotions = new LinkedList<>();
        allPromotions.forEach(promotion -> promotions.add(promotion));

        Set<SortedSet<String>> promotionalBundles = new HashSet<>();
        for (int i = 0; i < promotions.size(); i++) {
            SortedSet<String> currentPromotionBundles = null;
            Set<String> notCombinableWith = null;
            for(int k=1; k<promotions.size(); k++) {
                for (int j = 0; j < promotions.size(); j++) {
                    Promotion currentPromotion = promotions.get(j);
                    if (currentPromotion==null || !isCombinable(notCombinableWith, currentPromotion.getCode())) {
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
                        //need to create new obj so we don't overwrite existing bundles and so we don't have to loop through already iterated elements
                        currentPromotionBundles = createNewPromotionalBundles(currentPromotionBundles);
                    }
                }
                notCombinableWith.clear();
                //switch index 1 to tail so we can get all combinations for element at head
                switchToTail(1, promotions);
                currentPromotionBundles = null;
                notCombinableWith.clear();
            }
            //got all combinations of head, now we switch head to tail
            switchHeadToTail(promotions);
        }
        return promotionalBundles;        //have a list of not possible combos
    }

    private SortedSet<String> createNewPromotionalBundles(SortedSet<String> currentPromotionBundles) {
        SortedSet<String> temp = new TreeSet<>();
        temp.addAll(currentPromotionBundles);
        currentPromotionBundles = new TreeSet<>();
        currentPromotionBundles.addAll(temp);
        return currentPromotionBundles;
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

}
