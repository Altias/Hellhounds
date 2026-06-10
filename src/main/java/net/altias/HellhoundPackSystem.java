package net.altias;

import net.altias.entity.HellhoundEntity;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.UUID;

public class HellhoundPackSystem {

    public static void register() {

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity instanceof HellhoundEntity dead)) return;
            if (!dead.isInPack()) return;

            UUID packId = dead.getPackId();
            boolean wasAlpha = dead.isAlpha();

            // alpha death clears pack
            if (wasAlpha) {
                clearPack(dead.getWorld(), packId);
            }
        });
    }

    private static void clearPack(World world, UUID packId) {

        ServerWorld serverWorld = (ServerWorld) world;

        for (HellhoundEntity hound : serverWorld.getEntitiesByClass(
                HellhoundEntity.class,
                new Box(-30000, -30000, -30000, 30000, 30000, 30000),
                h -> packId.equals(h.getPackId())
        )) {

            hound.setPackId(null);
            hound.setAlpha(false);

            // reset combat state
            hound.setTarget(null);
            hound.setAttacking(null);

            if (hound instanceof net.minecraft.entity.mob.MobEntity mob) {
                mob.setAttacker(null);
            }
        }
    }
}