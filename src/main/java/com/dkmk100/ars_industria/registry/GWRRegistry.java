package com.dkmk100.ars_industria.registry;

import com.dkmk100.ars_industria.StatsModifier;
import com.dkmk100.ars_industria.items.*;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import lykrast.gunswithoutroses.registry.ItemGroupGunsWithoutRoses;
import lykrast.gunswithoutroses.registry.ModSounds;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

public class GWRRegistry {
    public static RegistryObject<Item> ENCHANTERS_GUN = null;
    public static RegistryObject<Item> SOURCE_BULLET = null;



    public static void RegisterItems(){
        Item.Properties def = new Item.Properties().tab(ItemGroupGunsWithoutRoses.INSTANCE);
        ENCHANTERS_GUN = ModRegistry.ITEMS.register("enchanters_gun", () -> new
                GWREnchantersGun(ModRegistry.basicProperties().tab(ItemGroupGunsWithoutRoses.INSTANCE).durability(1025), 0, 1, 16, 1.5, 22,1f).repair(() -> Ingredient.of(ItemsRegistry.SOURCE_GEM)));
        SOURCE_BULLET = ModRegistry.ITEMS.register("source_bullet", () -> new SourceBullet(def,0,0.80f));

        RegistryObject<Item> dumbBullet = ModRegistry.ITEMS.register("dumb_bullet", () -> new SourceBullet(ModRegistry.basicProperties(),0,1.5f,  AugmentAmplify.INSTANCE,5).setCreative());
        StatsModifier mod1 = new StatsModifier(1.5f).withAugment(AugmentAmplify.INSTANCE,3,false);
        RegistryObject<Item> dumbGun = ModRegistry.ITEMS.register("dumb_gun", () -> new
                GWREnchantersGun(ModRegistry.basicProperties().durability(4096), 0, 1, 16, 1.5, 22, mod1).setCreative().repair(() -> Ingredient.of(Items.DIAMOND)).projectileSpeed(4f));


        if(CompatHandler.OmegaLoaded()) {
            //note: still can't reference omega directly because classloading shenanigans

            StatsModifier sniperMod = new StatsModifier(1.35f).withInnerAugment(AugmentAmplify.INSTANCE,4, 4,false);
            StatsModifier gatlingMod = new StatsModifier(0.75f);
            StatsModifier shotgunMod = new StatsModifier(0.85f);
            StatsModifier advancedPistolMod = new StatsModifier(1.0f).withInnerAugment(AugmentAmplify.INSTANCE,1, 2,false);

            RegistryObject<Item> enchantersSniper = ModRegistry.ITEMS.register("enchanters_sniper", () -> new
                    GWREnchantersGun(ModRegistry.basicProperties().tab(ItemGroupGunsWithoutRoses.INSTANCE).durability(4096), 0, 1, 22, 0, 30, sniperMod).fireSound(ModSounds.sniper).repair(() -> Ingredient.of(Items.DIAMOND)).projectileSpeed(4f));
            RegistryObject<Item> enchantersGatling= ModRegistry.ITEMS.register("enchanters_gatling", () -> new
                    EnchantersGatling(ModRegistry.basicProperties().tab(ItemGroupGunsWithoutRoses.INSTANCE).durability(4096), 0, 1, 4, 4.0, 30, gatlingMod).ignoreInvulnerability(true).repair(() -> Ingredient.of(Items.DIAMOND)));
            RegistryObject<Item> enchantersShotgun= ModRegistry.ITEMS.register("enchanters_shotgun", () -> new
                    EnchantersShotgun(ModRegistry.basicProperties().tab(ItemGroupGunsWithoutRoses.INSTANCE).durability(4096), 0, 1, 16, 6.0, 30, 5, shotgunMod).ignoreInvulnerability(true).fireSound(ModSounds.shotgun).repair(() -> Ingredient.of(Items.DIAMOND)));

            RegistryObject<Item> enchantersPistolAdvanced = ModRegistry.ITEMS.register("enchanters_gun_advanced", () -> new
                    GWREnchantersGun(ModRegistry.basicProperties().tab(ItemGroupGunsWithoutRoses.INSTANCE).durability(4096), 0, 1, 12, 1.5, 35, advancedPistolMod).repair(() -> Ingredient.of(Items.DIAMOND)).projectileSpeed(3.5f));

            RegistryObject<Item> infusedBullet = ModRegistry.ITEMS.register("infused_source_bullet", () -> new SourceBullet(def, 0, 0.60f));
            RegistryObject<Item> arcaneBullet = ModRegistry.ITEMS.register("arcane_source_bullet", () -> new SourceBullet(def, 0, 0.40f));
        }

    }
}
