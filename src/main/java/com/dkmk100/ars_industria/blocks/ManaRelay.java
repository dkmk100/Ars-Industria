package com.dkmk100.ars_industria.blocks;

import com.dkmk100.ars_industria.registry.BotaniaRegistry;
import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import com.hollingsworth.arsnouveau.api.util.SourceUtil;
import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.common.block.tile.ModdedTile;
import com.hollingsworth.arsnouveau.common.block.tile.SourceJarTile;
import com.hollingsworth.arsnouveau.common.entity.EntityFollowProjectile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.api.block.IWandBindable;
import vazkii.botania.api.mana.IManaCollector;
import vazkii.botania.api.subtile.TileEntityGeneratingFlower;
import vazkii.botania.common.helper.MathHelper;

import java.util.Objects;
import java.util.Optional;

public class ManaRelay extends ModdedTile implements ITickable, IWandBindable {

    BlockPos bindingPos = null;

    public ManaRelay(BlockPos pos, BlockState state) {
        this(BotaniaRegistry.manaRelay.get(), pos, state);
    }

    public ManaRelay(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    @Override
    public void tick(Level level, BlockState state, BlockPos pos) {
        transferMana();
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if(bindingPos!=null) {
            NBTUtil.storeBlockPos(tag, "bound", bindingPos);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if(NBTUtil.hasBlockPos(tag,"bound")){
            bindingPos = NBTUtil.getBlockPos(tag,"bound");
        }
    }

    @Nullable
    SourceJarTile getJar(int range, Level world, int minSource) {
        Optional<BlockPos> loc = BlockPos.findClosestMatch(getBlockPos(), range, range, (b) -> world.getBlockEntity(b) instanceof SourceJarTile && ((SourceJarTile) world.getBlockEntity(b)).getSource() >= minSource);
        if (!loc.isPresent())
            return null;
        SourceJarTile tile = (SourceJarTile) world.getBlockEntity(loc.get());
        return tile;
    }

    void MakeTakeParticles(BlockPos result){
        EntityFollowProjectile aoeProjectile = new EntityFollowProjectile(level, result, this.worldPosition);
        level.addFreshEntity(aoeProjectile);
    }

    public void transferMana() {
        IManaCollector collector = findBindCandidateAt(bindingPos);
        if (collector != null && level.getGameTime() % 20 == 0)
            if (!collector.isFull()) {
                SourceJarTile tile = getJar(5, getLevel(), 100);
                if(tile==null){
                    //no source jars nearby
                    return;
                }

                float rawManaAvailable = tile.getSource() * BotaniaRegistry.manaPerSource;

                float rawMana = Math.min(rawManaAvailable, collector.getMaxMana() - collector.getCurrentMana());

                int sourceTaken = Math.round(rawMana / BotaniaRegistry.manaPerSource);

                int manaTransfered = Math.round(rawMana * BotaniaRegistry.efficiency);

                tile.addSource(-1 * sourceTaken);

                collector.receiveMana(manaTransfered);

                MakeTakeParticles(tile.getBlockPos());

                updateBlock();
            }
    }


    @Override
    public boolean canSelect(Player player, ItemStack wand, BlockPos pos, Direction side) {
        return true;
    }

    @Override
    public boolean bindTo(Player player, ItemStack wand, BlockPos pos, Direction side) {
        if (wouldBeValidBinding(pos)) {
            setBindingPos(pos);
            return true;
        }

        return false;
    }

    public void setBindingPos(@javax.annotation.Nullable BlockPos bindingPos) {
        boolean changed = !Objects.equals(this.bindingPos, bindingPos);

        this.bindingPos = bindingPos;

        if (changed) {
            updateBlock();
        }
    }

    public boolean wouldBeValidBinding(@javax.annotation.Nullable BlockPos pos) {
        if (level == null || pos == null || !level.isLoaded(pos) || MathHelper.distSqr(getBlockPos(), pos) > (long) getBindingRadius() * getBindingRadius()) {
            return false;
        } else {
            return findBindCandidateAt(pos) != null;
        }
    }

    public int getBindingRadius() {
        return TileEntityGeneratingFlower.LINK_RANGE;
    }


    public @javax.annotation.Nullable IManaCollector findBindCandidateAt(BlockPos pos) {
        if (level == null || pos == null) {
            return null;
        }

        BlockEntity be = level.getBlockEntity(pos);
        return be != null && IManaCollector.class.isAssignableFrom(be.getClass()) ? (IManaCollector) be : null;
    }


    @Nullable
    @Override
    public BlockPos getBinding() {
        return bindingPos;
    }
}
