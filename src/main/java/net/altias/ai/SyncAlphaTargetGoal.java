package net.altias.ai;

import net.altias.entity.HellhoundEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.UUID;

public class SyncAlphaTargetGoal extends Goal {

    private final HellhoundEntity mob;
    private HellhoundEntity alpha;

    public SyncAlphaTargetGoal(HellhoundEntity mob) {
        this.mob = mob;
        this.setControls(EnumSet.of(Goal.Control.TARGET));
    }

    @Override
    public boolean canStart() {
        return !mob.isAlpha() && mob.isInPack();
    }

    @Override
    public void tick() {

        if (alpha == null || !alpha.isAlive()) {
            alpha = findAlpha();
        }

        if (alpha == null) return;

        LivingEntity target = alpha.getTarget();

        if (target != null && target.isAlive()) {
            mob.setTarget(target);
        }
    }

    private HellhoundEntity findAlpha() {

        UUID packId = mob.getPackId();
        if (packId == null) return null;

        return mob.getWorld()
                .getEntitiesByClass(HellhoundEntity.class, mob.getBoundingBox().expand(32), e ->
                        e.isAlpha() && packId.equals(e.getPackId())
                )
                .stream()
                .findFirst()
                .orElse(null);
    }
}
