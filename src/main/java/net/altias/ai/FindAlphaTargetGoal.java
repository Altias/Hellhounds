package net.altias.ai;

import net.altias.entity.HellhoundEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;

import java.util.EnumSet;

public class FindAlphaTargetGoal extends ActiveTargetGoal<LivingEntity> {

    private final HellhoundEntity mob;

    public FindAlphaTargetGoal(HellhoundEntity mob) {
        super(mob,
                LivingEntity.class,
                10,
                true,
                false,
                entity -> isValid(mob, entity));
        this.mob = mob;
    }

    private static boolean isValid(HellhoundEntity mob, LivingEntity e) {

        if (e == mob) return false;
        if (!e.isAlive()) return false;

        if (e instanceof PlayerEntity p) {
            return !p.isCreative() && !p.isSpectator();
        }

        return e.getType() == EntityType.PIGLIN
                || e.getType() == EntityType.PIGLIN_BRUTE
                || e.getType() == EntityType.HOGLIN
                || e.getType() == EntityType.SKELETON
                || e.getType() == EntityType.WITHER_SKELETON;
    }

    @Override
    public boolean canStart() {
        return mob.isAlpha() && super.canStart();
    }

    @Override
    public void start() {
        super.start();
        mob.playAlphaHowl();
    }
}