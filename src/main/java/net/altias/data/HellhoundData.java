package net.altias.data;

import net.minecraft.entity.passive.WolfEntity;

public class HellhoundData {

    public static boolean isHellhound(WolfEntity wolf) {
        return ((HellhoundAccess) wolf).starlight$isHellhound();
    }

    public static void setHellhound(WolfEntity wolf) {
        ((HellhoundAccess) wolf).starlight$setHellhound(true);
    }
}
