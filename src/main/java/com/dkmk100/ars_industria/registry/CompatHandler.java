package com.dkmk100.ars_industria.registry;

import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import net.minecraftforge.fml.ModList;

public class CompatHandler {
    public static boolean IELoaded(){
        return ModList.get().isLoaded("immersiveengineering");
    }
    public static boolean GWRLoaded(){
        return ModList.get().isLoaded("gunswithoutroses");
    }
    public static boolean BotaniaLoaded()
    {
        return ModList.get().isLoaded("botania");
    }


    public static boolean OmegaLoaded(){
        return ModList.get().isLoaded("arsomega");
    }

    public static boolean shouldIgnoreBuffs(AbstractSpellPart effect){
        if(OmegaLoaded()){
            return OmegaRegistry.ShouldIgnoreBuffs(effect);
        }
        return false;
    }

}
