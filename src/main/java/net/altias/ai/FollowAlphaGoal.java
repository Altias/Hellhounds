package net.altias.ai;

import net.altias.entity.HellhoundEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.UUID;

public class FollowAlphaGoal extends Goal {

    private final HellhoundEntity mob;
    private HellhoundEntity alpha;

    private final double speed;
    private final float maxDistance;
    private final float minDistance;

    public FollowAlphaGoal(HellhoundEntity mob, double speed, float maxDistance, float minDistance) {
        this.mob = mob;
        this.speed = speed;
        this.maxDistance = maxDistance;
        this.minDistance = minDistance;

        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if (mob.isAlpha()) return false;
        if (!mob.isInPack()) return false;

        this.alpha = findAlpha();
        return alpha != null;
    }

    @Override
    public boolean shouldContinue() {
        return alpha != null && alpha.isAlive() && !mob.isAlpha();
    }

    @Override
    public void tick() {

        if (alpha == null) return;

        double distSq = mob.squaredDistanceTo(alpha);

        if (distSq > maxDistance * maxDistance) {
            mob.getNavigation().startMovingTo(alpha, speed);
        }

        if (mob.getTarget() != null && mob.getTarget().isAlive()) {

            double distSqToTarget = mob.squaredDistanceTo(mob.getTarget());

            // IMPORTANT: stop following if close enough to fight
            if (distSqToTarget < 9.0) return;
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
