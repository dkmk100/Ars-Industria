package com.dkmk100.ars_industria.datagen;

import com.dkmk100.ars_industria.ArsIndustria;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArsIndustria.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Setup {

    //use runData configuration to generate stuff
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        var providers = gen.getProviders();

        /*
        providers.add(new ArsProviders.ImbuementProvider(gen));
        providers.add(new ArsProviders.GlyphProvider(gen));
        providers.add(new ArsProviders.EnchantingAppProvider(gen));
        providers.add(new ArsProviders.PatchouliProvider(gen));
         */
    }

}
