package net.altias.item;

import net.altias.Hellhounds;
import net.altias.entity.ModEntities;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class HellhoundSpawnEgg {

    public static final Item HELLHOUND_SPAWN_EGG = registerItem(
            "hellhound_spawn_egg",
            new SpawnEggItem(ModEntities.HELLHOUND, 0x2b1a12, 0xff5500, new Item.Settings())
    );

    private static Item registerItem(String name, Item item) {
        return Registry.register(
                Registries.ITEM,
                Identifier.of(Hellhounds.MOD_ID, name),
                item
        );
    }

    public static void registerModItems() {
        Hellhounds.LOGGER.info("Registering Mod Items for " + Hellhounds.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
            entries.add(HELLHOUND_SPAWN_EGG);
        });
    }
}
