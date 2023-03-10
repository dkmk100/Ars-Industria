package com.dkmk100.ars_industria.glyphs;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.Set;

public class TestEffect extends AbstractEffect {

    public static TestEffect INSTANCE = new TestEffect("test", "Test");

    public TestEffect(String tag, String description) {
        super(tag, description);
    }

    @Override
    public int getDefaultManaCost() {
        return 100;
    }

    @Override
    public void onResolve(HitResult rayTraceResult, Level world, @Nullable LivingEntity shooter, SpellStats spellStats, SpellContext spellContext) {
        super.onResolve(rayTraceResult, world, shooter, spellStats, spellContext);

        System.out.println("Hello from my resolve!");
    }


    @Nonnull
    @Override
    public Set<AbstractAugment> getCompatibleAugments() {
        return augmentSetOf(AugmentAmplify.INSTANCE);
    }
}
