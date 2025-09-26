package net.lghast.blockcannon.register;

import net.lghast.blockcannon.BlockCannon;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModEnchantments {
    public static final ResourceKey<Enchantment> MASSIVENESS = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(BlockCannon.MOD_ID, "massiveness"));

    public static final ResourceKey<Enchantment> AFTERSHOCK = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(BlockCannon.MOD_ID, "aftershock"));

    public static void bootstrap(BootstrapContext<Enchantment> context){
        var enchantments = context.lookup(Registries.ENCHANTMENT);
        var item = context.lookup(Registries.ITEM);
        register(context, MASSIVENESS, Enchantment.enchantment(Enchantment.definition(
                item.getOrThrow(ModTags.MASSIVENESS_ENCHANTABLE),
                6,
                3,
                Enchantment.dynamicCost(16, 10),
                Enchantment.dynamicCost(60, 10),
                4,
                EquipmentSlotGroup.MAINHAND
        )));

        register(context, AFTERSHOCK, Enchantment.enchantment(Enchantment.definition(
                item.getOrThrow(ModTags.AFTERSHOCK_ENCHANTABLE),
                6,
                3,
                Enchantment.dynamicCost(16, 10),
                Enchantment.dynamicCost(60, 10),
                4,
                EquipmentSlotGroup.MAINHAND
        )));
    }


    private static void register(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key, Enchantment.Builder builder){
        registry.register(key, builder.build(key.location()));
    }
}
