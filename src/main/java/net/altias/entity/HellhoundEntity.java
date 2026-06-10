package net.altias.entity;

import net.altias.HellhoundPackSpawner;
import net.altias.ai.FindAlphaTargetGoal;
import net.altias.ai.FollowAlphaGoal;
import net.altias.ai.SyncAlphaTargetGoal;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

import static net.altias.HellhoundPackSpawner.applyAlphaStats;

public class HellhoundEntity extends WolfEntity implements Angerable {


    private static final TrackedData<Boolean> ALPHA =
            DataTracker.registerData(HellhoundEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private static final TrackedData<Optional<UUID>> PACK_ID =
            DataTracker.registerData(HellhoundEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);

    public HellhoundEntity(EntityType<? extends WolfEntity> type, World world) {
        super(type, world);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
                                 SpawnReason spawnReason, @Nullable EntityData entityData) {

        super.initialize(world, difficulty, spawnReason, entityData);

        if (world.isClient()) return entityData;

        HellhoundPackSpawner.handleSpawn((ServerWorld) world, this, spawnReason);
        return entityData;
    }

    @Override
    public HellhoundEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.HELLHOUND.create(world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);

        builder.add(ALPHA, false);
        builder.add(PACK_ID, Optional.empty());
    }

    public UUID getPackId() {
        return this.dataTracker.get(PACK_ID).orElse(null);
    }

    public void setPackId(UUID id) {
        this.dataTracker.set(PACK_ID, Optional.ofNullable(id));
    }

    public boolean isAlpha() {
        return this.dataTracker.get(ALPHA);
    }

    public void setAlpha(boolean alpha) {
        this.dataTracker.set(ALPHA, alpha);
    }

    public boolean isInPack() {
        return this.dataTracker.get(PACK_ID).isPresent();
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        UUID id = getPackId();
        if (id != null) {
            nbt.putUuid("PackId", id);
        }

        nbt.putBoolean("Alpha", isAlpha());

        nbt.putInt("AngerTime", this.getAngerTime());

        if (this.getAngryAt() != null) {
            nbt.putUuid("AngryAt", this.getAngryAt());
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        setAlpha(nbt.getBoolean("Alpha"));
        if (isAlpha()) {
            applyAlphaStats(this);
        }

        if (nbt.containsUuid("PackId")) {
            setPackId(nbt.getUuid("PackId"));
        } else {
            setPackId(null);
        }

        this.setAngerTime(nbt.getInt("AngerTime"));

        if (nbt.containsUuid("AngryAt")) {
            this.setAngryAt(nbt.getUuid("AngryAt"));
        } else {
            this.setAngryAt(null);
        }
    }

    @Override
    public boolean isFireImmune() {
        return true;
    }

    @Override
    protected void initGoals() {
        super.initGoals();

        this.targetSelector.add(1, new FindAlphaTargetGoal(this));
        this.targetSelector.add(2, new SyncAlphaTargetGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.2, true));
        this.goalSelector.add(3, new FollowAlphaGoal(this, 1.2, 6.0f, 2.0f));
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return WolfEntity.createWolfAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3);
    }


    //Anger
    public void clearAnger() {
        this.setAngerTime(0);
        this.setAngryAt(null);
    }

    //Interact
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {

        if (this.isInPack()) {
            this.playAngrySound();
            this.setAngerTime(100);
            return ActionResult.FAIL;
        }

        return super.interactMob(player, hand);
    }

    //Sounds
    private void playAngrySound() {
        this.playSound(net.minecraft.sound.SoundEvents.ENTITY_WOLF_GROWL, 1.0f, 0.8f + this.random.nextFloat() * 0.4f);
    }

    public void playAlphaHowl() {
        this.playSound(SoundEvents.ENTITY_WOLF_HOWL, 1.2f, 0.6f + this.random.nextFloat() * 0.2f);
    }

    public static boolean canSpawn(
            EntityType<HellhoundEntity> type,
            ServerWorldAccess world,
            SpawnReason reason,
            BlockPos pos,
            net.minecraft.util.math.random.Random random
    ) {
        if (!(world instanceof ServerWorld serverWorld)) return false;

        if (serverWorld.getRegistryKey() != World.NETHER) return false;

        BlockPos down = pos.down();

        if (!world.getBlockState(down).isSolidBlock(world, down)) return false;

        if (world.getBlockState(pos).getFluidState().isIn(FluidTags.LAVA)) return false;

        if (world.getBlockState(pos).isOf(Blocks.BEDROCK)) return false;

        return true;
    }
}


