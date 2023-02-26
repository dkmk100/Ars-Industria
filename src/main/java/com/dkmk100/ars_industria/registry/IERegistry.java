package com.dkmk100.ars_industria.registry;

import blusunrize.immersiveengineering.api.tool.BulletHandler;
import com.dkmk100.ars_industria.ArsIndustria;
import com.dkmk100.ars_industria.ScribeBulletRecipe;
import com.dkmk100.ars_industria.bullets.IESpellBullet;
import com.dkmk100.ars_industria.items.IESpellBulletItem;
import com.dkmk100.arsomega.util.ForgeEventBusSubscriber;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public class IERegistry {
    public static DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS;
    public static DeferredRegister<RecipeType<?>> RECIPE_TYPES;
    public static RegistryObject<RecipeType<ScribeBulletRecipe>> SCRIBE_BULLET_TYPE;
    public static RegistryObject<SimpleRecipeSerializer<ScribeBulletRecipe>> SCRIBE_BULLET;
    public static RegistryObject<Item> SPELL_BULLET = null;
    private static final BulletHandler.IBullet SpellBullet = new IESpellBullet();
    public static void RegisterItems(){
        SPELL_BULLET = ModRegistry.ITEMS.register("spell_bullet", () -> new IESpellBulletItem(SpellBullet));
    }

    public static void RegisterBullets(){
        BulletHandler.registerBullet(new ResourceLocation(ArsIndustria.MODID,"spell_bullet"),SpellBullet);
    }

    private static <T extends Recipe<?>> Supplier<SimpleRecipeSerializer<T>> makeSerializer(Function<ResourceLocation, T> create) {
        return () -> {
            return new SimpleRecipeSerializer(create);
        };
    }

    static{
        RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ArsIndustria.MODID);
        RECIPE_TYPES = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, ArsIndustria.MODID);
        SCRIBE_BULLET_TYPE = RECIPE_TYPES.register("scribe_bullet", () -> new RecipeType<ScribeBulletRecipe>() {
            @Override
            public String toString() {
                return new ResourceLocation(ArsIndustria.MODID,"scribe_bullet").toString();
            }
        });
        SCRIBE_BULLET = RECIPE_SERIALIZERS.register("scribe_bullet", makeSerializer(ScribeBulletRecipe::new));
    }

    public static void RegisterRecipes(IEventBus bus){
        ArsIndustria.LOGGER.info("registering stuff");
        RECIPE_TYPES.register(bus);
        RECIPE_SERIALIZERS.register(bus);
    }
}
