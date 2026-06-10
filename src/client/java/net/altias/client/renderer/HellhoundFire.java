package net.altias.client.renderer;

import net.altias.data.HellhoundAccess;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.particle.ParticleTypes;

public class HellhoundFire extends FeatureRenderer<WolfEntity, WolfEntityModel<WolfEntity>> {

    public HellhoundFire(FeatureRendererContext<WolfEntity, WolfEntityModel<WolfEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers,
                       int light,
                       WolfEntity wolf,
                       float limbAngle,
                       float limbDistance,
                       float tickDelta,
                       float animationProgress,
                       float headYaw,
                       float headPitch) {

        HellhoundAccess access = (HellhoundAccess) wolf;

        if (!(access.starlight$isHellhound() && access.starlight$isAlpha())) {
            return;
        }

        if (wolf.getWorld().isClient) {

            if (wolf.age % 3 == 0) {

                wolf.getWorld().addParticle(
                        ParticleTypes.FLAME,
                        wolf.getX(),
                        wolf.getY() + 0.5,
                        wolf.getZ(),
                        (wolf.getWorld().random.nextFloat() - 0.5) * 0.02,
                        0.03,
                        (wolf.getWorld().random.nextFloat() - 0.5) * 0.02
                );
            }
        }
    }
}
