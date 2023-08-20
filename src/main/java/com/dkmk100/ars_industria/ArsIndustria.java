package com.dkmk100.ars_industria;

import com.dkmk100.ars_industria.registry.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ArsIndustria.MODID)
public class ArsIndustria
{
    public static final String MODID = "ars_industria";

    public static final Logger LOGGER = LogManager.getLogger();

    public ArsIndustria() {
        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
        ModRegistry.registerRegistries(modbus);
        if(CompatHandler.IELoaded()){
            IERegistry.RegisterItems();
            IERegistry.RegisterBullets();
            IERegistry.RegisterRecipes(modbus);
        }
        if(CompatHandler.GWRLoaded()){
            GWRRegistry.RegisterItems();
        }
        if(CompatHandler.BotaniaLoaded()){
            BotaniaRegistry.RegisterItems();
        }
        modbus.addListener(this::setup);
        modbus.addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        //ForgeRegistries.RECIPE_SERIALIZERS.getEntries().forEach(((item) -> {
        //    ArsIndustria.LOGGER.info(item.getValue().);
        //}));
    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }


}
