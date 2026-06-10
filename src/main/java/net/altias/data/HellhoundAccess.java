package net.altias.data;

import java.util.UUID;

public interface HellhoundAccess {
    boolean starlight$isHellhound();
    void starlight$setHellhound(boolean value);

    UUID starlight$getPackId();
    void starlight$setPackId(UUID id);

    boolean starlight$isAlpha();
    void starlight$setAlpha(boolean value);
}

