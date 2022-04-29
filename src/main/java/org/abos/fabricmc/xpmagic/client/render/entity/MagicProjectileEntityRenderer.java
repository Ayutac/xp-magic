package org.abos.fabricmc.xpmagic.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.abos.fabricmc.xpmagic.entity.projectile.MagicProjectileEntity;

@Environment(EnvType.CLIENT)
public class MagicProjectileEntityRenderer extends ProjectileEntityRenderer<MagicProjectileEntity> {

    public static final String TEXTURE_STR = "textures/entity/projectiles/%s.png";

    public MagicProjectileEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(MagicProjectileEntity entity) {
        return new Identifier(String.format(TEXTURE_STR,Registry.ENTITY_TYPE.getId(entity.getType()).getPath()));
    }

}
