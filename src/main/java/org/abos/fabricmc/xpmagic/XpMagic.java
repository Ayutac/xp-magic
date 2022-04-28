package org.abos.fabricmc.xpmagic;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.abos.fabricmc.xpmagic.entity.projectile.MagicProjectileEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XpMagic implements ModInitializer {

    public static final String MOD_ID = "xp-magic";



    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        LOGGER.info("Initializing " + MOD_ID + "...");

        LOGGER.info("Initializing of " + MOD_ID + " done!");
    }
}
