package com.dkmk100.ars_industria;

import com.google.common.collect.ImmutableMap;
import net.minecraftforge.fml.loading.LoadingModList;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class IndustriaMixinPlugin implements IMixinConfigPlugin {
    private static final Supplier<Boolean> TRUE = () -> true;

    private static final Map<String, Supplier<Boolean>> CONDITIONS = ImmutableMap.of(
            "com.dkmk100.ars_industria.mixin.GWRBulletEntityMixin", () -> LoadingModList.get().getModFileById("gunswithoutroses") != null,
            "com.dkmk100.ars_industria.mixin.SpellDiscountFixMixin", () -> LoadingModList.get().getModFileById("arsomega") == null
    );

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        boolean value = CONDITIONS.getOrDefault(mixinClassName, TRUE).get();
        Logger logger = LoggerContext.getContext().getLogger("Ars Industria Mixin Plugin");
        logger.info(mixinClassName + " mixing? "+value);
        return value;
    }

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}