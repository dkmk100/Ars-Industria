package com.dkmk100.ars_industria.mixin;

import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.hollingsworth.arsnouveau.api.util.ManaUtil.getPlayerDiscounts;

@Mixin(value = SpellResolver.class, remap = false)
public class SpellDiscountFixMixin {

    @Shadow
    public Spell spell;
    @Shadow
    public SpellContext spellContext;

    //fixes an Ars bug lol.
    @Redirect(method = "enoughMana", at = @At(value = "INVOKE", target = "Lcom/hollingsworth/arsnouveau/api/spell/SpellResolver;getResolveCost()I"))
    int FixEnoughMana(SpellResolver resolver){
        int cost = spellContext.getSpell().getDiscountedCost() - getPlayerDiscounts(spellContext.getUnwrappedCaster(), spell);
        return Math.max(cost, 0);
    }
}
