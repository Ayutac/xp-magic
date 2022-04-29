package org.abos.fabricmc.xpmagic.entity.projectile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MagicProjectileEntity extends PersistentProjectileEntity {

    private BiConsumer<MagicProjectileEntity, Entity> effectOnEntity;
    private BiConsumer<MagicProjectileEntity, BlockPos> effectOnBlock;

    public MagicProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world,
                                 BiConsumer<MagicProjectileEntity, Entity> effectOnEntity, BiConsumer<MagicProjectileEntity, BlockPos> effectOnBlock) {
        super(entityType, world);
        Objects.requireNonNull(effectOnEntity);
        Objects.requireNonNull(effectOnBlock);
        this.effectOnEntity = effectOnEntity;
        this.effectOnBlock = effectOnBlock;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (!world.isClient) {
            Entity entity = entityHitResult.getEntity();
            effectOnEntity.accept(this, entity);
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!world.isClient) {
            effectOnBlock.accept(this, blockHitResult.getBlockPos());
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!world.isClient) {
            this.discard();
        }
    }

    @Override
    protected ItemStack asItemStack() {
        return ItemStack.EMPTY;
    }

}
