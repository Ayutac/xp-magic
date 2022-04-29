package org.abos.fabricmc.xpmagic.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import org.abos.fabricmc.xpmagic.client.render.entity.MagicProjectileEntityRenderer;
import org.abos.fabricmc.xpmagic.entity.projectile.MagicProjectileEntities;

@Environment(EnvType.CLIENT)
public class XpMagicClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(MagicProjectileEntities.FIRE_BOLT.getType(), MagicProjectileEntityRenderer::new);


    }
}
