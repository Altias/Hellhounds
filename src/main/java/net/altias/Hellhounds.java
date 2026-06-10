package net.altias;

import net.altias.config.HellhoundConfig;
import net.altias.entity.HellhoundEntity;
import net.altias.entity.ModEntities;
import net.altias.item.HellhoundSpawnEgg;
import net.altias.world.gen.ModEntitySpawns;
import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.SpawnLocation;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hellhounds implements ModInitializer {
	public static final String MOD_ID = "hellhounds";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		HellhoundPackSystem.register();
		HellhoundSpawnEgg.registerModItems();

		HellhoundConfig.registerConfigs();
		ModEntities.register();

		ModEntitySpawns.addSpawns();

		LOGGER.info("Hellhounds initialized");
	}
}