package net.altias.world.gen;

import net.altias.config.HellhoundConfig;
import net.altias.entity.HellhoundEntity;
import net.altias.entity.ModEntities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;


public class ModEntitySpawns {
    public static void addSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(),
                SpawnGroup.MONSTER, ModEntities.HELLHOUND, HellhoundConfig.SPAWN_CHANCE, 1, 1);


        SpawnRestriction.register(ModEntities.HELLHOUND, SpawnLocationTypes.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HellhoundEntity::canSpawn);
    }
}
