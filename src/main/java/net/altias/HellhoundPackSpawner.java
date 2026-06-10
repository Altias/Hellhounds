package net.altias;

import net.altias.config.HellhoundConfig;
import net.altias.entity.HellhoundEntity;
import net.altias.entity.ModEntities;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;


public class HellhoundPackSpawner {

    public static void handleSpawn(ServerWorld world, HellhoundEntity hound, SpawnReason reason) {

        // NATURAL = ALWAYS PACK (your rule)
        if (reason == SpawnReason.NATURAL || reason == SpawnReason.SPAWN_EGG) {
            createPack(world, hound);
            return;
        }
    }

    public static void createPack(ServerWorld world, HellhoundEntity alpha) {

        UUID packId = UUID.randomUUID();

        alpha.setPackId(packId);
        alpha.setAlpha(true);

        HellhoundPackSpawner.applyAlphaStats(alpha);

        int count = world.random.nextInt(3) + 3;

        for (int i = 0; i < count; i++) {

            HellhoundEntity member = ModEntities.HELLHOUND.create(world);
            if (member == null) continue;

            BlockPos pos = alpha.getBlockPos().add(
                    world.random.nextInt(6) - 3,
                    0,
                    world.random.nextInt(6) - 3
            );

            member.refreshPositionAndAngles(pos, 0, 0);

            member.setPackId(packId);
            member.setAlpha(false);

            world.spawnEntity(member);
        }
    }

    public static void applyAlphaStats(HellhoundEntity alpha) {

        var maxHealth = alpha.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (maxHealth != null) {
            maxHealth.setBaseValue(60.0);
            alpha.setHealth(60.0f);
        }

        var damage = alpha.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (damage != null) {
            damage.setBaseValue(8.0);
        }

        var speed = alpha.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (speed != null) {
            speed.setBaseValue(0.35);
        }

        var scale = alpha.getAttributeInstance(EntityAttributes.GENERIC_SCALE);
        if (scale != null) {
            scale.setBaseValue(1.5);
        }

        alpha.calculateDimensions();
    }
}
