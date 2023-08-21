package com.dkmk100.ars_industria.items;

import com.dkmk100.ars_industria.ArsIndustria;
import com.dkmk100.ars_industria.StatsModifier;
import com.dkmk100.ars_industria.entities.ISpellBullet;
import com.hollingsworth.arsnouveau.api.item.ICasterTool;
import com.hollingsworth.arsnouveau.api.item.ISpellModifierItem;
import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import lykrast.gunswithoutroses.entity.BulletEntity;
import lykrast.gunswithoutroses.item.GunItem;
import lykrast.gunswithoutroses.item.IBullet;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class GWREnchantersGun extends GunItem implements ICasterTool, ISpellModifierItem {

    StatsModifier statsModifier;
    boolean creativeOnly = false;
    public GWREnchantersGun(Properties properties, int bonusDamage, double damageMultiplier, int fireDelay, double inaccuracy, int enchantability, float spellCostMultiplier) {
        super(properties, bonusDamage, damageMultiplier, fireDelay, inaccuracy, enchantability);
        this.statsModifier = new StatsModifier(spellCostMultiplier);
    }

    public GWREnchantersGun(Properties properties, int bonusDamage, double damageMultiplier, int fireDelay, double inaccuracy, int enchantability, StatsModifier statsModifier) {
        super(properties, bonusDamage, damageMultiplier, fireDelay, inaccuracy, enchantability);
        this.statsModifier = new StatsModifier(statsModifier);//copy it
    }

    public GWREnchantersGun setCreative(){
        creativeOnly = true;
        return this;
    }

    @Override
    protected void shoot(Level world, Player player, ItemStack gun, ItemStack ammo, IBullet bulletItem, boolean bulletFree) {

        //taken from super
        BulletEntity shot = bulletItem.createProjectile(world, ammo, player);
        shot.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, (float)this.getProjectileSpeed(gun, player), (float)this.getInaccuracy(gun, player));
        shot.setDamage((shot.getDamage() + this.getBonusDamage(gun, player)) * this.getDamageMultiplier(gun, player));
        shot.setIgnoreInvulnerability(this.ignoreInvulnerability);
        this.changeBullet(world, player, gun, shot, bulletFree);
        world.addFreshEntity(shot);

        //add spell
        //start by getting spell
        Spell spell = getSpellCaster(gun).getSpell();
        if(spell!=null && spell.recipe.size() > 0) {
            //clone stats modifier
            StatsModifier tempModifier = new StatsModifier(statsModifier);

            //apply changes from bullet, if applicable
            if (bulletItem instanceof SourceBullet) {
                ((SourceBullet) bulletItem).MergeStats(tempModifier);
            }

            //ArsIndustria.LOGGER.info("merged stats: "+tempModifier.toString());

            //apply spell change
            spell = tempModifier.ModifySpell(spell);
            //ArsIndustria.LOGGER.info("spell after modified: "+spell.serialize());

            //works because of mixin to let me save the spell on the normal bullet entity
            ISpellBullet spellBullet = (ISpellBullet) shot;
            spellBullet.setSpell(spell);
        }
    }


    @Override
    public boolean setSpell(ISpellCaster caster, Player player, InteractionHand hand, ItemStack stack, Spell spell) {
        return ICasterTool.super.setSpell(caster, player, hand, stack, spell);
    }

    @Override
    public boolean isScribedSpellValid(ISpellCaster caster, Player player, InteractionHand hand, ItemStack stack, Spell spell) {
        return spell.recipe.stream().noneMatch(s -> s instanceof AbstractCastMethod);
    }

    @Override
    public void sendInvalidMessage(Player player) {
        PortUtil.sendMessageNoSpam(player, Component.translatable("ars_nouveau.sword.invalid"));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @javax.annotation.Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if(creativeOnly){
            tooltip.add(Component.literal("Creative mode only item").withStyle(ChatFormatting.DARK_PURPLE));
        }
        if(repairMaterial!=null && repairMaterial.get() != null && repairMaterial.get().getItems().length > 0){
            tooltip.add(Component.literal("Repair with " + repairMaterial.get().getItems()[0].getDisplayName().getString()));
        }
        getInformation(stack, worldIn, tooltip, flagIn);

        super.appendHoverText(stack,worldIn,tooltip,flagIn);
    }

    @Override
    protected void addExtraStatsTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip) {
        super.addExtraStatsTooltip(stack, world, tooltip);
        statsModifier.addTooltip(tooltip);
    }
}
