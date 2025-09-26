package net.lghast.blockcannon.client;

import net.lghast.blockcannon.register.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ModItemProperties {
    private static void registerShootingProperty() {
        ResourceLocation propertyIdUsing = ResourceLocation.parse("block_cannon:shooting");
        ItemProperties.register(ModItems.BLOCK_CANNON.get(),
                propertyIdUsing,
                (stack, level, entity, seed) ->
                        entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
        );
    }

    public static void register() {
        registerShootingProperty();
    }
}
