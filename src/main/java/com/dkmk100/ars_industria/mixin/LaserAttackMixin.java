/*
package com.dkmk100.ars_industria.mixin;


import com.dkmk100.ars_industria.ArsIndustria;
import com.hollingsworth.arsnouveau.common.entity.WildenGuardian;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LaserAttackGoal.class)
public class LaserAttackMixin {

    @Final
    @Shadow
    private WildenGuardian guardian;


    @Inject(at = @At("HEAD"), method = "Lcom/hollingsworth/arsnouveau/common/entity/goal/guardian/LaserAttackGoal;stop()V", cancellable = true, remap = false)
    public void stop(CallbackInfo ci) {
        this.guardian.setLaser(false);
        ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "Lcom/hollingsworth/arsnouveau/common/entity/goal/guardian/LaserAttackGoal;start()V", cancellable = true, remap = false)
    public void start(CallbackInfo ci) {
        if(this.guardian.getTarget() == null) {
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "Lcom/hollingsworth/arsnouveau/common/entity/goal/guardian/LaserAttackGoal;canContinueToUse()Z", cancellable = true, remap = false)
    public void canContinueToUse(CallbackInfoReturnable<Boolean> ci) {
        if(this.guardian.getTarget() == null) {
            ci.setReturnValue(false);
        }
    }

    @Inject(at = @At("HEAD"), method = "Lcom/hollingsworth/arsnouveau/common/entity/goal/guardian/LaserAttackGoal;tick()V", cancellable = true, remap = false)
    public void tick(CallbackInfo ci) {
        if(this.guardian.getTarget() == null) {
            ci.cancel();
        }
    }
}
 */
