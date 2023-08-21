package com.dkmk100.ars_industria.blocks;

import com.dkmk100.ars_industria.registry.BotaniaRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import vazkii.botania.api.mana.ManaReceiver;

public class CustomManaReceiver implements ManaReceiver {

    BotanicSourcelinkTile tile;
    double efficiency;
    int neededValue;

    public CustomManaReceiver(BotanicSourcelinkTile tile, double efficiency){
        this.tile = tile;
        this.efficiency = efficiency;
        neededValue = (int)Math.round(BotaniaRegistry.getManaPerSource() * (1/efficiency));
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
        return tile.getSource()*BotaniaRegistry.getManaPerSource();
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
