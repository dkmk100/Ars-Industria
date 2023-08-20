package com.dkmk100.ars_industria.blocks;

import com.dkmk100.ars_industria.registry.BotaniaRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import vazkii.botania.api.mana.ManaReceiver;

public class CustomManaReceiver implements ManaReceiver {

    BotanicSourcelinkTile tile;
    float efficiency;
    int neededValue;

    public CustomManaReceiver(BotanicSourcelinkTile tile, float efficiency){
        this.tile = tile;
        this.efficiency = efficiency;
        neededValue = Math.round(BotaniaRegistry.manaPerSource * (1/efficiency));
    }

    @Override
    public Level getManaReceiverLevel() {
        return tile.getLevel();
    }

    @Override
    public BlockPos getManaReceiverPos() {
        return tile.getBlockPos();
    }

    @Override
    public int getCurrentMana() {
        return tile.getSource()*BotaniaRegistry.manaPerSource;
    }

    @Override
    public boolean isFull() {
        return !tile.canAcceptSource();
    }

    @Override
    public void receiveMana(int mana) {
        mana+=tile.tempMana;
        tile.tempMana = mana % neededValue;
        tile.addSource(mana/neededValue);

    }

    @Override
    public boolean canReceiveManaFromBursts() {
        return tile.canAcceptSource();
    }
}
