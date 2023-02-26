package com.dkmk100.ars_industria.blocks;

import com.hollingsworth.arsnouveau.common.block.SourcelinkBlock;
import com.hollingsworth.arsnouveau.common.block.TickableModBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BotanicSourcelink extends TickableModBlock {
    public BotanicSourcelink(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BotanicSourcelinkTile(pPos,pState);
    }
}
