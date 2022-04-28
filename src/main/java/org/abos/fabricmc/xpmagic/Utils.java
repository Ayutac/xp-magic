package org.abos.fabricmc.xpmagic;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class Utils {

    public static void setFireToBlock(World world, BlockPos blockPos, Entity cause) {
        BlockState blockState = world.getBlockState(blockPos);
        if (!CampfireBlock.canBeLit(blockState) && !CandleBlock.canBeLit(blockState) && !CandleCakeBlock.canBeLit(blockState)) {
            BlockPos blockPos2 = blockPos.offset(Direction.UP);
            if (AbstractFireBlock.canPlaceAt(world, blockPos2, Direction.UP)) {
                BlockState blockState2 = AbstractFireBlock.getState(world, blockPos2);
                world.setBlockState(blockPos2, blockState2, 11);
                if (cause instanceof PlayerEntity player) {
                    world.playSound(player, blockPos2, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
                    world.emitGameEvent(player, GameEvent.BLOCK_PLACE, blockPos);
                }
            }
        } else {
            world.setBlockState(blockPos, blockState.with(Properties.LIT, true), 11);
            if (cause instanceof PlayerEntity player) {

                world.playSound(player, blockPos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);

                world.emitGameEvent(player, GameEvent.BLOCK_PLACE, blockPos);
            }
        }

    }

    private Utils() {/* No instantiation. */}
}
