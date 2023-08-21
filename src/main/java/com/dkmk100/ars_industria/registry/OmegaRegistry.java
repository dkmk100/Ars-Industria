package com.dkmk100.ars_industria.registry;

import com.dkmk100.arsomega.glyphs.IIgnoreBuffs;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;

public class OmegaRegistry {
    public static boolean ShouldIgnoreBuffs(AbstractSpellPart effect){
        return effect instanceof IIgnoreBuffs;
    }
}
