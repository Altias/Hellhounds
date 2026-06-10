package net.altias.client.renderer;

import net.altias.data.HellhoundAccess;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.Identifier;

public class HellhoundEmissive extends FeatureRenderer<WolfEntity, WolfEntityModel<WolfEntity>> {

    public HellhoundEmissive(FeatureRendererContext<WolfEntity, WolfEntityModel<WolfEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                       int light, WolfEntity wolf,
                       float limbAngle, float limbDistance,
                       float tickDelta, float animationProgress,
                       float headYaw, float headPitch) {

        HellhoundAccess access = (HellhoundAccess) wolf;

        String tex;

        if (wolf.isTamed()) {
            tex = "hellhound_tame_e.png";
        }
        else if (wolf.getAngerTime() > 0) {
            tex = "hellhound_angry_e.png";
        }
        else {
            tex = "hellhound_e.png";
        }


        RenderLayer layer = RenderLayer.getEyes(
                Identifier.of("hellhounds", "textures/entity/wolf/" + tex)
        );

        VertexConsumer vc = vertexConsumers.getBuffer(layer);

        this.getContextModel().render(
                matrices,
                vc,
                0xF000F0,
                OverlayTexture.DEFAULT_UV
        );
    }
}
