package com.dkmk100.ars_industria;

import com.dkmk100.arsomega.glyphs.IIgnoreBuffs;
import com.hollingsworth.arsnouveau.api.spell.*;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class StatsModifier{
    List<AugmentModification> endModifications = new ArrayList<>();
    List<InnerAugmentModification> innerModifications = new ArrayList<>();
    float costMultiplier = 1f;
    int costIncrease = 0;

    public StatsModifier(){

    }
    public StatsModifier(float costMult){
        costMultiplier = costMult;
    }

    public StatsModifier(float costMult, int costInc){
        costMultiplier = costMult;
        costIncrease = costInc;
    }

    public StatsModifier withCost(float costMultiplier, int costIncrease){
        this.costMultiplier = costMultiplier;
        this.costIncrease = costIncrease;
        return this;
    }

    public StatsModifier withAugment(AbstractAugment augment, int count, boolean hasCost){
        this.endModifications.add(new AugmentModification(augment,hasCost,count));
        return this;
    }

    public StatsModifier withInnerAugment(AbstractAugment augment, int countEach, int maxTotal, boolean hasCost){
        this.innerModifications.add(new InnerAugmentModification(augment,hasCost,countEach,maxTotal));
        return this;
    }

    @Override
    public String toString() {
        String str = "";
        str += "cost mult: "+costMultiplier + "\n";
        str += "cost addition "+costIncrease + "\n";
        for(AugmentModification augment : endModifications){
            str += augment.toString() + "\n";
        }
        for(InnerAugmentModification augment : innerModifications){
            str += augment.toString() + "\n";
        }
        return str;
    }

    //copy constructor
    public StatsModifier(StatsModifier mod){

        //needs to copy modifiers
        for(AugmentModification augment : mod.endModifications){
            endModifications.add(new AugmentModification(augment));//copy item with copy constructor
        }
        for(InnerAugmentModification augment : mod.innerModifications){
            innerModifications.add(new InnerAugmentModification(augment));//copy item with copy constructor
        }

        this.costMultiplier = mod.costMultiplier;
        this.costIncrease = mod.costIncrease;
    }

    public void addTooltip(List<Component> tooltip){
        for(AugmentModification augment : endModifications){
            if(augment.affectCost) {
                tooltip.add(Component.m_237113_("Adds " + augment.count + " " + augment.augment.getLocaleName() + " to the end of the spell on cast, increasing cost accordingly"));
            }
            else{
                tooltip.add(Component.m_237113_("Adds " + augment.count + " free " + augment.augment.getLocaleName() + " to the end of the spell on cast"));
            }
        }
        for(InnerAugmentModification augment : innerModifications){
            if(augment.affectCost) {
                tooltip.add(Component.m_237113_("Adds " + augment.countEach + " " + augment.augment.getLocaleName() + " to up to " + augment.totalEffects + " compatible effects on cast, increasing cost accordingly"));
            }
            else{
                tooltip.add(Component.m_237113_("Adds " + augment.countEach + " free " + augment.augment.getLocaleName() + " to up to " + augment.totalEffects + " compatible effects on cast"));
            }
        }

        float discount = Math.round((1f - costMultiplier)*1000)/10f;

        if(discount>0) {
            tooltip.add(Component.m_237113_("Spell discount: " + discount + "%").withStyle(ChatFormatting.DARK_GREEN));
        }
        else if(discount!=0){
            tooltip.add(Component.m_237113_("Spell cost increase: " + (-1 * discount) + "%").withStyle(ChatFormatting.DARK_RED));
        }
        if(costIncrease > 0){
            tooltip.add(Component.m_237113_("Spell flat cost increase: " + costIncrease + " mana").withStyle(ChatFormatting.DARK_RED));
        }
        else if(costIncrease < 0){
            tooltip.add(Component.m_237113_("Spell flat discount: " + (-1 * costIncrease) + " mana").withStyle(ChatFormatting.DARK_GREEN));
        }

    }

    //default merge so you can chain changes easily
    public StatsModifier Merge(StatsModifier other){
        return Merge(other,true);
    }

    public StatsModifier Merge(StatsModifier other, boolean overrideSelf){
        StatsModifier mod = this;
        if(!overrideSelf){
            mod = new StatsModifier(this);
        }

        for(AugmentModification augment : other.endModifications){
            mod.endModifications.add(new AugmentModification(augment));//copy item with copy constructor
        }
        for(InnerAugmentModification augment : other.innerModifications){
            mod.innerModifications.add(new InnerAugmentModification(augment));//copy item with copy constructor
        }

        mod.costMultiplier *= other.costMultiplier;
        mod.costIncrease += other.costIncrease;

        return mod;
    }

    public Spell ModifySpell(Spell spell){
        for(AugmentModification mod : endModifications){
            //adding in this way does not augment cost
            spell.add(mod.augment, mod.count);
            if(mod.affectCost){
                spell.addDiscount(mod.augment.getCastingCost());
            }
        }

        //handle all inner modifications
        int[] augsLeft = new int[innerModifications.size()];

        int i = 0;
        for(i =0;i<innerModifications.size();i++){
            augsLeft[i] = innerModifications.get(i).totalEffects;
        }

        ArrayList<AbstractSpellPart> recipe = new ArrayList();

        i = 0;
        for(Iterator var8 = spell.recipe.iterator(); var8.hasNext(); ++i) {
            AbstractSpellPart part = (AbstractSpellPart)var8.next();
            recipe.add(part);
            for(int i4=0;i4<innerModifications.size();i4++) {
                InnerAugmentModification augment = innerModifications.get(i4);
                if (part instanceof AbstractEffect && part.compatibleAugments.contains(augment.augment)) {
                    boolean valid = true;
                    if (part instanceof IIgnoreBuffs) {
                        valid = false;
                    } else if (i + 1 < spell.recipe.size()) {
                        AbstractSpellPart part2 = (AbstractSpellPart) spell.recipe.get(i + 1);

                        for (int i2 = i + 1; valid && i2 < spell.recipe.size() && part2 instanceof AbstractAugment; ++i2) {
                            part2 = (AbstractSpellPart) spell.recipe.get(i2);
                            if (part2 == AugmentDampen.INSTANCE) {
                                valid = false;
                            }
                        }
                    }

                    if (valid && augsLeft[i4] > 0) {
                        augsLeft[i4] -= 1;

                        for (int i3 = 0; i3 < augment.countEach; ++i3) {
                            recipe.add(augment.augment);
                            if(augment.affectCost){
                                spell.addDiscount(augment.augment.getCastingCost());
                            }
                        }
                    }
                }
            }
        }

        spell.recipe = recipe;

        spell.addDiscount(Math.round(spell.getDiscountedCost() * (1f - costMultiplier)));
        return spell;
    }

    public SpellStats ModifyStats(SpellStats stats){
        return stats;
    }

    //both of these need both normal and copy constructors
    static class AugmentModification{
        AbstractAugment augment;
        boolean affectCost;
        int count;

        @Override
        public String toString() {
            return "AugmentModification{" +
                    "augment=" + augment +
                    ", affectCost=" + affectCost +
                    ", count=" + count +
                    '}';
        }

        public AugmentModification(AbstractAugment aug, boolean costs, int amount){
            augment = aug;
            affectCost = costs;
            count = amount;
        }
        public AugmentModification(AugmentModification mod){
            augment = mod.augment;
            affectCost = mod.affectCost;
            count = mod.count;
        }
    }
    static class InnerAugmentModification{
        AbstractAugment augment;
        boolean affectCost;
        int countEach;
        int totalEffects;

        @Override
        public String toString() {
            return "InnerAugmentModification{" +
                    "augment=" + augment +
                    ", affectCost=" + affectCost +
                    ", countEach=" + countEach +
                    ", totalEffects=" + totalEffects +
                    '}';
        }

        public InnerAugmentModification(AbstractAugment aug, boolean costs, int amountEach, int amountTotal){
            augment = aug;
            affectCost = costs;
            countEach = amountEach;
            totalEffects = amountTotal;
        }
        public InnerAugmentModification(InnerAugmentModification mod){
            augment = mod.augment;
            affectCost = mod.affectCost;
            countEach = mod.countEach;
            totalEffects = mod.totalEffects;
        }
    }
}
