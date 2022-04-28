package org.abos.fabricmc.xpmagic.entity.projectile;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.abos.fabricmc.xpmagic.Utils;
import org.abos.fabricmc.xpmagic.XpMagic;

import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public enum MagicProjectileEntities {
    FIRE_BOLT(2,
            (projectile, entity) -> {
                entity.damage(DamageSource.magic(projectile, projectile.getOwner()), 1f);
            },
            (projectile, blockPos) -> {
                Utils.setFireToBlock(projectile.getWorld(), blockPos, projectile.getOwner());
            }
    );

    //private MagicProjectileEntity entity;
    private BiConsumer<MagicProjectileEntity, Entity> effectOnEntity;
    private BiConsumer<MagicProjectileEntity, BlockPos> effectOnBlock;
    private EntityType<MagicProjectileEntity> entityType;
    private int requiredXp;

    MagicProjectileEntities(int requiredXp, BiConsumer<MagicProjectileEntity, Entity> effectOnEntity, BiConsumer<MagicProjectileEntity, BlockPos> effectOnBlock) {
        if (requiredXp < 0)
            requiredXp = 0;
        this.requiredXp = requiredXp;
        if (effectOnEntity == null)
            effectOnEntity = (projectile, entity) -> {};
        this.effectOnEntity = effectOnEntity;
        if (effectOnBlock == null)
            effectOnBlock = (projectile, blockState) -> {};
        this.effectOnBlock = effectOnBlock;
        entityType = Registry.register(Registry.ENTITY_TYPE, new Identifier(XpMagic.MOD_ID, name().toLowerCase(Locale.ROOT)),
                FabricEntityTypeBuilder.<MagicProjectileEntity>create(SpawnGroup.MISC, this::create)
                        .dimensions(EntityDimensions.fixed(0.25f, 0.25f)) // dimensions in Minecraft units of the projectile
                        .trackRangeBlocks(4).trackedUpdateRate(10) // necessary for all thrown projectiles
                        .build()
                );
    }

    private MagicProjectileEntity create(EntityType<MagicProjectileEntity> type, World world) {
        return new MagicProjectileEntity(type, world, effectOnEntity, effectOnBlock);
    }

    public MagicProjectileEntity create(World world, Entity user) {
        MagicProjectileEntity projectile = new MagicProjectileEntity(entityType, world, effectOnEntity, effectOnBlock);
        projectile.setOwner(user);
        return projectile;
    }

    public EntityType<MagicProjectileEntity> getType() {
        return entityType;
    }

    public int requiredXp() {
        return requiredXp;
    }

}
