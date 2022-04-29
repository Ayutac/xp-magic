package org.abos.fabricmc.xpmagic.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.abos.fabricmc.xpmagic.XpMagic;
import org.abos.fabricmc.xpmagic.entity.projectile.MagicProjectileEntities;
import org.abos.fabricmc.xpmagic.entity.projectile.MagicProjectileEntity;

import java.util.Locale;

public class ProjectileSpellItem extends SpellItem {

    public ProjectileSpellItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand); // creates a new ItemStack instance of the user's itemStack in-hand
        MagicProjectileEntities projectile;
        try {
            String id = Registry.ITEM.getId(this).getPath();
            projectile = MagicProjectileEntities.valueOf(id.substring(0,id.lastIndexOf('_')).toUpperCase(Locale.ROOT));
        }
        catch (IllegalArgumentException ex) {
            XpMagic.LOGGER.warn("Couldn't find entity for projectile spell!", ex);
            return TypedActionResult.pass(itemStack);
        }
        if (!user.getAbilities().creativeMode && user.experienceLevel < projectile.requiredXp()) {
            // TODO change to user level
            return TypedActionResult.fail(itemStack);
        }
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 1F); // plays a globalSoundEvent
		if (!world.isClient) {
            MagicProjectileEntity projectileEntity = projectile.create(world, user);
            projectileEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 0F);
                        /*
                        snowballEntity.setProperties(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
                        In 1.17,we will use setProperties instead of setVelocity.
                        */
            world.spawnEntity(projectileEntity); // spawns entity
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            // TODO reduce XP
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
