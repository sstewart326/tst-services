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
 *  Service class to get all possible promotion combos and all combos per promo code
 *
 * @author Shawn Stewart
 */
public class PromotionComboService {

    /**
     * Finds all promotion combos for a particular promotion code
     *
     * First calls allCombinablePromotions() to get all promo codes.
     * Then loops through promotion combos
     * Then loops through each promotion
     * For each promotion, add promotion combo to HashMap with promotion code as key
     * Lastly, lookup bundle in map by promotion code and return
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
     * Finds all promotion code combinations
     *
     * Loops through size of allPromotions
     * Before going to next iteration, switches the head element to tail since at this point we found all combos of head
     *   Loops index 1 through end of list
     *   Before going to next iteration, switches index 1 to tail since we are finding all combinations for element at index 0
     *      Loops size of allPromotions to get combinations with head of list
     * returns all possible promotion combos
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

        Set<SortedSet<String>> allPromotionCombos = new HashSet<>();
        //loop through all promotions. start at 0 index. once all combos, are found for that index, move element to tail
        for (int i = 0; i < promotions.size(); i++) {
            SortedSet<String> currentPromotionCombo = null;
            Set<String> notCombinableWith = null;
            //loop to move element at index 1 to tail so that we are able to get all possible combinations after loop below finishes
            for(int j=1; j<promotions.size(); j++) {
                //loop to get combinations with element at head of list with elements following
                for (int k = 0; k < promotions.size(); k++) {
                    Promotion currentPromotion = promotions.get(k);
                    if (currentPromotion==null || !isCombinable(notCombinableWith, currentPromotion.getCode())) {
                        continue;
                    }
                    if (currentPromotionCombo == null) {
                        currentPromotionCombo = new TreeSet<>();
                        notCombinableWith = new HashSet<>();
                    }
                    currentPromotionCombo.add(currentPromotion.getCode());
                    notCombinableWith.addAll(currentPromotion.getNotCombinableWith());

                    if (currentPromotionCombo.size() > 1 && !allPromotionCombos.contains(currentPromotionCombo)) {
                        allPromotionCombos.add(currentPromotionCombo);
                        //need to create new obj so we don't overwrite existing bundles and so we don't have to loop through already iterated elements
                        currentPromotionCombo = createNewPromotionalBundles(currentPromotionCombo);
                    }
                }
                notCombinableWith.clear();
                //switch element at index 1 to tail so we can get all combinations for element at head
                switchToTail(1, promotions);
                currentPromotionCombo = null;
                notCombinableWith.clear();
            }
            //got all combinations of head, now we switch head to tail
            switchHeadToTail(promotions);
        }
        return allPromotionCombos;        //have a list of not possible combos
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
