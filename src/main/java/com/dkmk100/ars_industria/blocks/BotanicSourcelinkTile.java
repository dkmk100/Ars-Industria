package com.dkmk100.ars_industria.blocks;

import com.dkmk100.ars_industria.ArsIndustria;
import com.dkmk100.ars_industria.registry.BotaniaRegistry;
import com.hollingsworth.arsnouveau.common.block.tile.SourcelinkTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import vazkii.botania.api.BotaniaForgeCapabilities;
import vazkii.botania.api.mana.ManaReceiver;

public class BotanicSourcelinkTile extends SourcelinkTile implements ICapabilityProvider {

    LazyOptional<ManaReceiver> manaReceiverLazyOptional;

    public BotanicSourcelinkTile(BlockPos pos, BlockState state){
        this(BotaniaRegistry.botanicSourcelink.get(),pos,state);
        manaReceiverLazyOptional = makeReceiver();
    }

    public BotanicSourcelinkTile(BlockEntityType<?> sourceLinkTile, BlockPos pos, BlockState state) {
        super(sourceLinkTile, pos, state);
        manaReceiverLazyOptional = makeReceiver();
    }

    @Override
    public int getMaxSource() {
        return 3000;//3x the default sourcelink amount, botania can be beefy and this is a converter
    }

    @Override
    public int getTransferRate() {
        return this.getMaxSource();
    }

    private LazyOptional<ManaReceiver> makeReceiver(){
        return LazyOptional.of(() -> new CustomManaReceiver(this,BotaniaRegistry.getEfficiency()));
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == BotaniaForgeCapabilities.MANA_RECEIVER) {
            return manaReceiverLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        manaReceiverLazyOptional.invalidate();
    }


    public int tempMana = 0;


    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("extraMana",tempMana);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if(tag.contains("extraMana")) {
            tempMana = tag.getInt("extraMana");
        }
        else{
            ArsIndustria.LOGGER.warn("missing extra mana tag...");
        }
    }

}
