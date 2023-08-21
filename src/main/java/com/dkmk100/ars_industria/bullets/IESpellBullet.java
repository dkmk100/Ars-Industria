package com.dkmk100.ars_industria.bullets;

import blusunrize.immersiveengineering.api.tool.BulletHandler;
import blusunrize.immersiveengineering.common.entities.RevolvershotEntity;
import com.dkmk100.ars_industria.ArsIndustria;
import com.dkmk100.ars_industria.StatsModifier;
import com.dkmk100.ars_industria.items.IESpellBulletItem;
import com.hollingsworth.arsnouveau.api.spell.*;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.LivingCaster;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.PlayerCaster;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.util.UUID;

public class IESpellBullet implements BulletHandler.IBullet {

    @Override
    public boolean isProperCartridge() {
        return false;
    }

    public Entity getProjectile(Player shooter, ItemStack cartridge, Entity projectile, boolean electro) {
        ((RevolvershotEntity)projectile).bulletPotion = cartridge;
        return projectile;
    }

    public void onHitTarget(Level world, HitResult rtr, @Nullable UUID shooterUUID, Entity projectile, boolean headshot) {
        Entity shooter = null;
        if (shooterUUID != null) {
            shooter = world.getPlayerByUUID(shooterUUID);
        }
        if(!world.isClientSide && shooter instanceof Player) {
            Player player = (Player) shooter;
            ItemStack stack = ((RevolvershotEntity) projectile).bulletPotion;

            ISpellCaster caster = new SpellCaster(stack);
            SpellContext context = new SpellContext(world, caster.getSpell(), (LivingEntity) shooter, PlayerCaster.from((LivingEntity) shooter), stack);
            SpellResolver resolver = new SpellResolver(context);
            if(stack.getItem() instanceof IESpellBulletItem bulletItem){
                StatsModifier mod = bulletItem.getTempModifier();
                mod.ModifySpell(resolver.spell);
            }
            else{
                ArsIndustria.LOGGER.warn("ie spell bullet not instance of...");
            }
            StatsModifier.CastWithoutLimitErrors(resolver,rtr);
        }
    }

    public ItemStack getCasing(ItemStack stack) {
        return BulletHandler.emptyCasing.asItem().getDefaultInstance();
    }

    public ResourceLocation[] getTextures() {
        return new ResourceLocation[]{new ResourceLocation("immersiveengineering:item/bullet_casull")};
    }

    public int getColour(ItemStack stack, int layer) {
        return -1;
    }

    public boolean isValidForTurret() {
        return false;
    }
}
