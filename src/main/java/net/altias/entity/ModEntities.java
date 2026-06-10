package net.altias.entity;

import net.altias.Hellhounds;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<HellhoundEntity> HELLHOUND =
            Registry.register(
                    Registries.ENTITY_TYPE,
                    Identifier.of(Hellhounds.MOD_ID, "hellhound"),
                    EntityType.Builder.create(HellhoundEntity::new, SpawnGroup.MONSTER)
                            .dimensions(0.6f, 0.85f)
                            .build()
            );

    public static void register() {
        Hellhounds.LOGGER.info("Registering entities for " + Hellhounds.MOD_ID);
        FabricDefaultAttributeRegistry.register(
                HELLHOUND,
                HellhoundEntity.createAttributes()
        );
    }
}
