package net.altias.config;

import com.mojang.datafixers.util.Pair;
import net.altias.Hellhounds;

public class HellhoundConfig {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    public static int SPAWN_CHANCE;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(Hellhounds.MOD_ID + "config").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("hellhound.spawn.chance", 30), "Higher value = more common.");
    }

    private static void assignConfigs() {
        SPAWN_CHANCE = CONFIG.getOrDefault("hellhound.spawn.chance", 30);

        System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}
