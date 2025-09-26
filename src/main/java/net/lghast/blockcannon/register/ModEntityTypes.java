package net.lghast.blockcannon.register;

import net.lghast.blockcannon.BlockCannon;
import net.lghast.blockcannon.common.entity.BlockShotEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, BlockCannon.MOD_ID);
    
    public static final DeferredHolder<EntityType<?>, EntityType<BlockShotEntity>> BLOCK_SHOT =
            ENTITY_TYPES.register("block_shot",
                    () -> EntityType.Builder.<BlockShotEntity> of (BlockShotEntity::new, MobCategory.MISC)
                            .sized(0.2F, 0.2F)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build("block_shot"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
