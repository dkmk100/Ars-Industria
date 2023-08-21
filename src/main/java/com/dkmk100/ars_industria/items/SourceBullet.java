package com.dkmk100.ars_industria.items;

import com.dkmk100.ars_industria.ArsIndustria;
import com.dkmk100.ars_industria.StatsModifier;
import com.dkmk100.ars_industria.entities.ISpellBullet;
import com.hollingsworth.arsnouveau.api.spell.*;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.IWrappedCaster;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.PlayerCaster;
import lykrast.gunswithoutroses.entity.BulletEntity;
import lykrast.gunswithoutroses.item.BulletItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SourceBullet extends BulletItem {

    private boolean creativeItem;
    private StatsModifier statsModifier;

    public SourceBullet(Properties pProperties, int damage) {
        super(pProperties, damage);
    }
    public SourceBullet(Properties pProperties, int damage, float costMultiplier) {
        super(pProperties, damage);
        statsModifier = new StatsModifier(costMultiplier);
    }

    public SourceBullet setCreative(){
        creativeItem = true;
        return this;
    }

    public void MergeStats(StatsModifier other){
        other.Merge(statsModifier);
    }

    public SourceBullet(Properties pProperties, int damage, float costMultiplier, AbstractAugment augment, int augmentCount) {
        super(pProperties, damage);
        statsModifier = new StatsModifier(costMultiplier).withAugment(augment,augmentCount,false);
    }

    public StatsModifier getModifier(){
        return statsModifier;
    }

    public static void castOnEntity(BulletEntity projectile, LivingEntity target, @Nullable Entity shooter, Level world) {
        //if(projectile instanceof ISpellBullet){
            if(!world.isClientSide && shooter instanceof Player) {
                ISpellBullet spellBullet = (ISpellBullet) projectile;
                Spell spell = spellBullet.getSpell();
                Player player = (Player) shooter;
                ArsIndustria.LOGGER.info("trying to cast spell on entity");
                SpellContext context = new SpellContext(world, spell, (Player) shooter, new PlayerCaster((Player)shooter));
                SpellResolver resolver = new SpellResolver(context);
                StatsModifier.CastWithoutLimitErrors(resolver, new EntityHitResult(target));
            }
        //}
    }

    public static void castOnBlock(BulletEntity projectile, BlockHitResult result, @Nullable Entity shooter, Level world){
        //if(projectile instanceof GWRSpellBulletEntity) {
            if (!world.isClientSide && shooter instanceof Player) {
                ISpellBullet spellBullet = (ISpellBullet) projectile;
                Spell spell = spellBullet.getSpell();
                Player player = (Player) shooter;
                SpellContext context = new SpellContext(world, spell, (Player) shooter, new PlayerCaster((Player)shooter));
                SpellResolver resolver = new SpellResolver(context);
                StatsModifier.CastWithoutLimitErrors(resolver, result);
            }
        //}
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @javax.annotation.Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if(creativeItem){
            tooltip.add(Component.literal("Creative mode only item").withStyle(ChatFormatting.DARK_PURPLE));
        }
        super.appendHoverText(stack,worldIn,tooltip,flagIn);

        statsModifier.addTooltip(tooltip);
    }


}
