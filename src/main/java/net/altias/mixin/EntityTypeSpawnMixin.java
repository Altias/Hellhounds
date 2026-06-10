package net.altias.mixin;

import net.altias.HellhoundPackSpawner;
import net.altias.entity.HellhoundEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityType.class)
public class EntityTypeSpawnMixin<T extends Entity> {

    @Inject(method = "spawn*", at = @At("RETURN"))
    private void onSpawn(ServerWorld world, ItemStack stack, PlayerEntity player,
                         BlockPos pos, SpawnReason reason,
                         CallbackInfoReturnable<T> cir) {

        T entity = cir.getReturnValue();
        if (!(entity instanceof HellhoundEntity hound)) return;

        if (world.isClient) return;
        if (hound.isInPack()) return;

        HellhoundPackSpawner.handleSpawn(world, hound, reason);
    }
}
