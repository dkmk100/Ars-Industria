package com.dkmk100.ars_industria.registry;

import com.dkmk100.ars_industria.blocks.BotanicSourcelink;
import com.dkmk100.ars_industria.blocks.BotanicSourcelinkTile;
import com.dkmk100.ars_industria.blocks.ManaRelay;
import com.dkmk100.ars_industria.blocks.ManaRelayBlock;
import com.hollingsworth.arsnouveau.ArsNouveau;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.RegistryObject;

public class BotaniaRegistry {
    public static RegistryObject<BlockEntityType> botanicSourcelink;
    public static RegistryObject<BlockEntityType> manaRelay;

    //100 mana : 1 source percentage wise (for normal pools and jars), but we want 1% mana to be 10% source
    //so we get 100 mana : 10 source, or 10 mana : 1 source
    public static int manaPerSource = 6;

    //I might swap this out for tiers later
    public static float efficiency = 0.85f;
    public static void RegisterItems(){
        RegistryObject<Block> BOTANIC_LINK = ModRegistry.BLOCKS.register("botanic_sourcelink",() -> new BotanicSourcelink(BlockBehaviour.Properties.of(Material.STONE)));
        botanicSourcelink = ModRegistry.TILE_TYPES.register("botanic_sourcelink_tile",() ->
                BlockEntityType.Builder.of(BotanicSourcelinkTile::new,new Block[]{BOTANIC_LINK.get()}).build(null));
        ModRegistry.ITEMS.register("botanic_sourcelink", () -> new BlockItem(BOTANIC_LINK.get(), new Item.Properties().tab(ArsNouveau.itemGroup)));

        RegistryObject<Block> MANA_RELAY = ModRegistry.BLOCKS.register("mana_relay",() -> new ManaRelayBlock(BlockBehaviour.Properties.of(Material.STONE)));
        manaRelay = ModRegistry.TILE_TYPES.register("mana_relay_tile",() ->
                BlockEntityType.Builder.of(ManaRelay::new,new Block[]{MANA_RELAY.get()}).build(null));
        ModRegistry.ITEMS.register("mana_relay", () -> new BlockItem(MANA_RELAY.get(), new Item.Properties().tab(ArsNouveau.itemGroup)));
    }
}
