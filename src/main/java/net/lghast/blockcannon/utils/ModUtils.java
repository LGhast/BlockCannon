package net.lghast.blockcannon.utils;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

public class ModUtils {
    public static void spawnItemWithMotion(ServerLevel level, double x, double y, double z, ItemStack stack, boolean pickUpDelay) {
        if (stack.isEmpty()) return;
        ItemEntity itemEntity = new ItemEntity(level, x, y, z, stack,
                level.random.nextGaussian() * 0.05, 0.2, level.random.nextGaussian() * 0.05
        );
        if(pickUpDelay) itemEntity.setDefaultPickUpDelay();
        level.addFreshEntity(itemEntity);
    }

    public static void spawnParticlesForAll(ServerLevel serverLevel, ParticleOptions particle,
                                            double x, double y, double z,
                                            double dx, double dy, double dz,
                                            int count, double speed) {

        serverLevel.sendParticles(particle,
                x, y, z,
                count,
                dx, dy, dz,
                speed);

    }
}
