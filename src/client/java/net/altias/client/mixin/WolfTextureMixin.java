package net.altias.client.mixin;

import net.altias.data.HellhoundAccess;
import net.altias.data.HellhoundData;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.client.render.entity.WolfEntityRenderer;

@Mixin(WolfEntityRenderer.class)
public class WolfTextureMixin {

    @Inject(method = "getTexture", at = @At("HEAD"), cancellable = true)
    private void hellhound_textures(WolfEntity wolf,
                                    CallbackInfoReturnable<Identifier> cir) {

        if (!(wolf instanceof net.altias.entity.HellhoundEntity hound)) {
            return;
        }

        String path;

        if (hound.isAlpha()) {
            path = "textures/entity/wolf/hellhound_alpha.png";
        }
        else if (hound.isTamed()) {
            path = "textures/entity/wolf/hellhound_tame.png";
        }
        else if (hound.hasAngerTime()) {
            path = "textures/entity/wolf/hellhound_angry.png";
        }
        else {
            path = "textures/entity/wolf/hellhound.png";
        }

        cir.setReturnValue(Identifier.of("hellhounds", path));
        cir.cancel();
    }
}