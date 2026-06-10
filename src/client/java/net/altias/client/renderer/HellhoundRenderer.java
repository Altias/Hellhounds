package net.altias.client.renderer;

import net.altias.entity.HellhoundEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.WolfEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.Identifier;

public class HellhoundRenderer extends WolfEntityRenderer {

    private static final Identifier NORMAL =
            Identifier.of("hellhounds", "textures/entity/wolf/hellhound.png");

    private static final Identifier TAMED =
            Identifier.of("hellhounds", "textures/entity/wolf/hellhound_tame.png");

    private static final Identifier ANGRY =
            Identifier.of("hellhounds", "textures/entity/wolf/hellhound_angry.png");

    public HellhoundRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(WolfEntity entity) {

        if (entity.isTamed()) {
            return TAMED;
        }

        if (entity.hasAngerTime() || entity.getTarget() != null) {
            return ANGRY;
        }

        return NORMAL;
    }
}
