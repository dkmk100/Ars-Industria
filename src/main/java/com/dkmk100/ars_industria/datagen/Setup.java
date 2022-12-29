package com.dkmk100.ars_industria.datagen;

import com.dkmk100.ars_industria.ArsIndustria;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = ArsIndustria.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Setup {

    //use runData configuration to generate stuff
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();

        gen.addProvider(new ArsProviders.ImbuementProvider(gen));
        gen.addProvider(new ArsProviders.GlyphProvider(gen));
        gen.addProvider(new ArsProviders.EnchantingAppProvider(gen));

        gen.addProvider(new ArsProviders.PatchouliProvider(gen));
    }

}
