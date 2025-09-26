package net.lghast.blockcannon.common.entity;

import net.lghast.blockcannon.register.ModEntityTypes;
import net.lghast.blockcannon.register.ModTags;
import net.lghast.blockcannon.utils.ModUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;

import java.util.Objects;

public class BlockShotEntity extends ThrowableItemProjectile {
    private final float explosion_radius;
    private final float damage;

    public BlockShotEntity(EntityType<? extends BlockShotEntity> type, Level level) {
        super(type, level);
        this.explosion_radius = 1.8f;
        this.damage = 3f;
    }

    public BlockShotEntity(Level level, LivingEntity shooter, float explosion_radius, float damage) {
        super(ModEntityTypes.BLOCK_SHOT.get(), shooter, level);
        this.explosion_radius = explosion_radius;
        this.damage = damage;
    }

    private float getRadius() {
        return explosion_radius;
    }

    private float getDamage() {
        if(getItem().getItem() instanceof BlockItem blockItem){
            float destroyTime = blockItem.getBlock().defaultDestroyTime();
            return damage * getMultiplier(destroyTime);
        }
        return damage;
    }

    private float getMultiplier(float destroyTime){
        if(destroyTime < 0 ) {
            return 20f;
        }
        double multiplier;
        if(destroyTime <= 10) {
            multiplier = -0.0174*destroyTime*destroyTime+0.534*destroyTime+0.4;
        }else{
            multiplier = 1.864 * Math.log(destroyTime) - 0.29;
        }
        return (float) Math.round(multiplier);
    }

    @Override
    protected Item getDefaultItem() {
        return Blocks.STONE.asItem();
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);

        if (!this.level().isClientSide) {
            shotExplosionEffect(result.getLocation());
            this.discard();
        }
    }

    private void shotExplosionEffect(Vec3 explosionPos) {
        AABB area = new AABB(
                explosionPos.x - getRadius(), explosionPos.y - getRadius(), explosionPos.z - getRadius(),
                explosionPos.x + getRadius(), explosionPos.y + getRadius(), explosionPos.z + getRadius()
        );
        playExplosionEffects(explosionPos);

        for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (entity != this.getOwner()) {
                double distance = entity.distanceToSqr(explosionPos);
                double maxDistanceSqr = getRadius() * getRadius();

                if (distance <= maxDistanceSqr) {
                    entity.hurt(this.damageSources().thrown(this, this.getOwner()), getDamage());
                    shotKnockback(entity, explosionPos, distance, maxDistanceSqr);
                    extraEffects(entity);
                }
            }
        }
    }

    private void shotKnockback(LivingEntity entity, Vec3 explosionPos, double distanceSqr, double maxDistanceSqr) {
        Vec3 direction = entity.position().subtract(explosionPos).normalize();
        float distanceFactor = 1.0f - (float)(distanceSqr / maxDistanceSqr);
        float knockbackStrength = 2.0f * distanceFactor;

        entity.setDeltaMovement(
                entity.getDeltaMovement().add(
                        direction.x * knockbackStrength,
                        direction.y * knockbackStrength,
                        direction.z * knockbackStrength
                )
        );
        entity.hurtMarked = true;
    }

    private void playExplosionEffects(Vec3 pos) {
        this.level().playSound(null, pos.x, pos.y, pos.z,
                SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1.0f, 1.0f);
        if (this.level() instanceof ServerLevel serverLevel) {
            ModUtils.spawnParticlesForAll(serverLevel, ParticleTypes.EXPLOSION, pos.x, pos.y, pos.z,
                    getRadius()/2, getRadius()/2, getRadius()/2, 3, 0.03);
            if(getItem().is(ModTags.FIERY_BLOCKS)){
                ModUtils.spawnParticlesForAll(serverLevel, ParticleTypes.FLAME, pos.x, pos.y, pos.z,
                        getRadius()/2, getRadius()/2, getRadius()/2, 5, 0.03);
            }
            if(getItem().is(ModTags.ICY_BLOCKS)){
                ModUtils.spawnParticlesForAll(serverLevel, ParticleTypes.SNOWFLAKE, pos.x, pos.y, pos.z,
                        getRadius()/2, getRadius()/2, getRadius()/2, 5, 0.03);
            }
        }
        spawnParticles();
    }

    private void extraEffects(LivingEntity entity){
        if(getItem().is(ModTags.FIERY_BLOCKS)){
            entity.igniteForSeconds(3);
        }
        if(getItem().is(ModTags.TELEPORTING_BLOCKS)){
            teleport(entity);
        }
        if(getItem().is(ModTags.INFESTED_BLOCKS)){
            Holder<MobEffect> infestedHolder = level().registryAccess().registryOrThrow(Registries.MOB_EFFECT)
                    .getHolderOrThrow(Objects.requireNonNull(MobEffects.INFESTED.getKey()));
            entity.addEffect(new MobEffectInstance(infestedHolder, 1200, 0));
        }
        if(getItem().is(ModTags.GLOWING_BLOCKS)){
            Holder<MobEffect> glowingHolder = level().registryAccess().registryOrThrow(Registries.MOB_EFFECT)
                    .getHolderOrThrow(Objects.requireNonNull(MobEffects.GLOWING.getKey()));
            entity.addEffect(new MobEffectInstance(glowingHolder, 1200, 0));
        }
        if(getItem().is(ModTags.ICY_BLOCKS)){
            Holder<MobEffect> glowingHolder = level().registryAccess().registryOrThrow(Registries.MOB_EFFECT)
                    .getHolderOrThrow(Objects.requireNonNull(MobEffects.MOVEMENT_SLOWDOWN.getKey()));
            entity.addEffect(new MobEffectInstance(glowingHolder, 300, 0));
        }
        if(getItem().is(ModTags.WEAVING_BLOCKS)){
            Holder<MobEffect> weavingHolder = level().registryAccess().registryOrThrow(Registries.MOB_EFFECT)
                    .getHolderOrThrow(Objects.requireNonNull(MobEffects.WEAVING.getKey()));
            entity.addEffect(new MobEffectInstance(weavingHolder, 1200, 0));
        }
        if(getItem().is(ModTags.OOZING_BLOCKS)){
            Holder<MobEffect> oozingHolder = level().registryAccess().registryOrThrow(Registries.MOB_EFFECT)
                    .getHolderOrThrow(Objects.requireNonNull(MobEffects.OOZING.getKey()));
            entity.addEffect(new MobEffectInstance(oozingHolder, 1200, 0));
        }
        if(getItem().is(ModTags.DARK_BLOCKS)){
            Holder<MobEffect> darkHolder = level().registryAccess().registryOrThrow(Registries.MOB_EFFECT)
                    .getHolderOrThrow(Objects.requireNonNull(MobEffects.DARKNESS.getKey()));
            entity.addEffect(new MobEffectInstance(darkHolder, 1200, 0));
        }
        if(getItem().is(ModTags.WITHER_BLOCKS)){
            Holder<MobEffect> witherHolder = level().registryAccess().registryOrThrow(Registries.MOB_EFFECT)
                    .getHolderOrThrow(Objects.requireNonNull(MobEffects.WITHER.getKey()));
            entity.addEffect(new MobEffectInstance(witherHolder, 60, 0));
        }
    }

    private void teleport(LivingEntity entity){
        Level level = level();
        if (!level().isClientSide) {
            for (int i = 0; i < 16; ++i) {
                double d0 = entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 16.0;
                double d1 = Mth.clamp(entity.getY() + (double) (entity.getRandom().nextInt(16) - 8),
                        level.getMinBuildHeight(), level.getMinBuildHeight() + ((ServerLevel) level).getLogicalHeight() - 1);
                double d2 = entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 16.0;
                if (entity.isPassenger()) {
                    entity.stopRiding();
                }

                Vec3 vec3 = entity.position();
                EntityTeleportEvent.ChorusFruit event = EventHooks.onChorusFruitTeleport(entity, d0, d1, d2);

                if (entity.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true)) {
                    level.gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(entity));
                    SoundSource soundsource;
                    SoundEvent soundevent;
                    if (entity instanceof Fox) {
                        soundevent = SoundEvents.FOX_TELEPORT;
                        soundsource = SoundSource.NEUTRAL;
                    } else {
                        soundevent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                        soundsource = SoundSource.PLAYERS;
                    }

                    level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), soundevent, soundsource);
                    entity.resetFallDistance();
                    break;
                }
            }
        }
    }

    private void spawnParticles() {
        if (this.level() instanceof ServerLevel serverLevel) {
            ParticleOptions particleOptions;
            Item item = getItem().getItem();

            if (item instanceof BlockItem blockItem) {
                Block block = blockItem.getBlock();
                particleOptions = new BlockParticleOption(ParticleTypes.BLOCK, block.defaultBlockState());
            } else {
                particleOptions = new ItemParticleOption(ParticleTypes.ITEM, getItem());
            }

            for (int i = 0; i < 25; ++i) {
                serverLevel.sendParticles(particleOptions,
                        this.getX(), this.getY(), this.getZ(),
                        1,
                        this.random.nextGaussian() * 0.5,
                        this.random.nextGaussian() * 0.5,
                        this.random.nextGaussian() * 0.5,
                        0.04);
            }
        }
    }
}
