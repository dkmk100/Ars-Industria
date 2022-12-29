package com.dkmk100.ars_industria.registry;

import net.minecraftforge.fml.ModList;

public class CompatHandler {
    public static boolean IELoaded(){
        return ModList.get().isLoaded("immersiveengineering");
    }
    public static boolean GWRLoaded(){
        return ModList.get().isLoaded("gunswithoutroses");
    }

    public static boolean OmegaLoaded(){
        return ModList.get().isLoaded("arsomega");
    }
}
