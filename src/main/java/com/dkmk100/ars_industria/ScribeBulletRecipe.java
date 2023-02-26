package com.dkmk100.ars_industria;

import com.dkmk100.ars_industria.items.IESpellBulletItem;
import com.dkmk100.ars_industria.registry.IERegistry;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.common.items.SpellParchment;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;

public class ScribeBulletRecipe extends CustomRecipe {

    public ScribeBulletRecipe(ResourceLocation name){
        super(name);
        ArsIndustria.LOGGER.info("recipe constructor");
    }

    public boolean matches(CraftingContainer inv, @Nonnull Level world) {
        boolean hasBullet = false;
        boolean hasScroll = false;

        for (int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack stackInSlot = inv.getItem(i);
            if (!stackInSlot.isEmpty()) {
                if (this.isSpellBullet(stackInSlot)) {
                    if (hasBullet) {
                        return false;
                    }
                    hasBullet = true;
                } else if (stackInSlot.getItem() instanceof SpellParchment) {
                    hasScroll = true;
                } else {
                    return false;
                }
            }
        }


        return hasBullet && hasScroll;
    }

    @Nonnull
    public ItemStack assemble(CraftingContainer inv) {
        ItemStack bullet = ItemStack.EMPTY;
        Spell spell = null;

        for(int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack stackInSlot = inv.getItem(i);
            if (!stackInSlot.isEmpty()) {
                if (bullet.isEmpty() && this.isSpellBullet(stackInSlot)) {
                    bullet = stackInSlot;
                } else if (stackInSlot.getItem() instanceof SpellParchment) {
                    spell = ((SpellParchment)stackInSlot.getItem()).getSpellCaster(stackInSlot).getSpell();
                }
            }
        }

        if (!bullet.isEmpty()) {
            ItemStack newBullet = ItemHandlerHelper.copyStackWithSize(bullet, 1);

            if(newBullet.getItem() instanceof IESpellBulletItem){
                IESpellBulletItem spellBulletItem = ((IESpellBulletItem)newBullet.getItem());
                spellBulletItem.setSpell(spellBulletItem.getSpellCaster(newBullet),null,null,newBullet,spell);
            }

            return newBullet;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer pContainer) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(pContainer.getContainerSize(), ItemStack.EMPTY);

        for(int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack item = pContainer.getItem(i);
            if(item.getItem() instanceof SpellParchment){
                ItemStack stack = item.copy();
                stack.setCount(1);
                nonnulllist.set(i, stack);
                nonnulllist.set(i,stack);
            }
        }

        return nonnulllist;
    }

    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Nonnull
    public RecipeSerializer<?> getSerializer() {
        return IERegistry.SCRIBE_BULLET.get();
    }

    private boolean isSpellBullet(ItemStack stack) {
        return stack.getItem() instanceof IESpellBulletItem;
    }
}
