package com.dkmk100.ars_industria.mixin;

import com.dkmk100.ars_industria.ArsIndustria;
import com.dkmk100.ars_industria.entities.ISpellBullet;
import com.dkmk100.ars_industria.items.SourceBullet;
import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.method.MethodTouch;
import lykrast.gunswithoutroses.entity.BulletEntity;
import lykrast.gunswithoutroses.item.IBullet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(BulletEntity.class)
public class GWRBulletEntityMixin extends Fireball implements ISpellBullet {
    Spell mySpell = null;

    //dummy stuff
    public GWRBulletEntityMixin(EntityType<? extends Fireball> p_37006_, Level p_37007_) {
        super(p_37006_, p_37007_);
    }

    @Override
    public void setSpell(Spell spell){
        if(spell.recipe.get(0) instanceof AbstractCastMethod){

        }
        else {
            ArrayList<AbstractSpellPart> recipe = new ArrayList<>();
            recipe.add(MethodTouch.INSTANCE);
            recipe.addAll(spell.recipe);
            spell.recipe = recipe;
        }
        mySpell = spell;
    }

    @Override
    public Spell getSpell(){
        return mySpell;
    }

    @Inject(at = @At("RETURN"), method = "Llykrast/gunswithoutroses/entity/BulletEntity;addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V", cancellable = false, remap = false)
    public void addAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        if(mySpell!=null) {
            compound.putString("an_spell", mySpell.serialize());
        }
    }

    @Inject(at = @At("RETURN"), method = "Llykrast/gunswithoutroses/entity/BulletEntity;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V", cancellable = false, remap = false)
    public void readAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        if(compound.contains("an_spell")){
            mySpell = Spell.deserialize(compound.getString("an_spell"));
        }
    }

    @Inject(at = @At("RETURN"), method = "Llykrast/gunswithoutroses/entity/BulletEntity;onHitEntity(Lnet/minecraft/world/phys/EntityHitResult;)V", cancellable = false, remap = false)
    public void onHitEntity(EntityHitResult raytrace, CallbackInfo ci) {
        if (!this.level.isClientSide && raytrace.getEntity() instanceof LivingEntity) {
            Entity shooter = this.getOwner();
            if(mySpell!=null && mySpell.recipe.size() > 0){
                //ArsIndustria.LOGGER.info("spell that hit: "+mySpell.serialize());
                SourceBullet.castOnEntity((BulletEntity) (Object) this, (LivingEntity) raytrace.getEntity(), shooter,level);
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult raytrace) {
        if (!this.level.isClientSide) {
            Entity shooter = this.getOwner();
            if(mySpell!=null && mySpell.recipe.size() > 0){
                //ArsIndustria.LOGGER.info("spell that hit: "+mySpell.serialize());
                SourceBullet.castOnBlock((BulletEntity) (Object) this,raytrace,shooter,level);
            }
        }
    }

}
