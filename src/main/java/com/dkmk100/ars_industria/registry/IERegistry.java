package com.dkmk100.ars_industria.registry;

import blusunrize.immersiveengineering.api.tool.BulletHandler;
import com.dkmk100.ars_industria.ArsIndustria;
import com.dkmk100.ars_industria.bullets.IESpellBullet;
import com.dkmk100.ars_industria.items.IESpellBulletItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class IERegistry {
    public static RegistryObject<Item> SPELL_BULLET = null;
    private static final BulletHandler.IBullet SpellBullet = new IESpellBullet();
    public static void RegisterItems(){
        SPELL_BULLET = ModRegistry.ITEMS.register("spell_bullet", () -> new IESpellBulletItem(SpellBullet));
    }

    public static void RegisterBullets(){
        BulletHandler.registerBullet(new ResourceLocation(ArsIndustria.MODID,"spell_bullet"),SpellBullet);
    }
}
