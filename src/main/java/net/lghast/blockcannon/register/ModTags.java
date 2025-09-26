package net.lghast.blockcannon.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ChorusFruitItem;
import net.minecraft.world.item.Item;

public class ModTags {
    public static final TagKey<Item> UNSHOOTABLE_BLOCKS = TagKey.create(
            Registries.ITEM,
            ResourceLocation.parse("block_cannon:unshootable_blocks")
    );
    public static final TagKey<Item> FIERY_BLOCKS = TagKey.create(
            Registries.ITEM,
            ResourceLocation.parse("block_cannon:fiery_blocks")
    );
    public static final TagKey<Item> INFESTED_BLOCKS = TagKey.create(
            Registries.ITEM,
            ResourceLocation.parse("block_cannon:infested_blocks")
    );
    public static final TagKey<Item> GLOWING_BLOCKS = TagKey.create(
            Registries.ITEM,
            ResourceLocation.parse("block_cannon:glowing_blocks")
    );
    public static final TagKey<Item> ICY_BLOCKS = TagKey.create(
            Registries.ITEM,
            ResourceLocation.parse("block_cannon:icy_blocks")
    );
    public static final TagKey<Item> WEAVING_BLOCKS = TagKey.create(
            Registries.ITEM,
            ResourceLocation.parse("block_cannon:weaving_blocks")
    );
    public static final TagKey<Item> OOZING_BLOCKS = TagKey.create(
            Registries.ITEM,
            ResourceLocation.parse("block_cannon:oozing_blocks")
    );
    public static final TagKey<Item> DARK_BLOCKS = TagKey.create(
            Registries.ITEM,
            ResourceLocation.parse("block_cannon:dark_blocks")
    );
    public static final TagKey<Item> WITHER_BLOCKS = TagKey.create(
            Registries.ITEM,
            ResourceLocation.parse("block_cannon:wither_blocks")
    );
    public static final TagKey<Item> TELEPORTING_BLOCKS = TagKey.create(
            Registries.ITEM,
            ResourceLocation.parse("block_cannon:teleporting_blocks")
    );

    public static final TagKey<Item> MASSIVENESS_ENCHANTABLE = TagKey.create(
            Registries.ITEM,
            ResourceLocation.parse("block_cannon:enchantable/massiveness")
    );
    public static final TagKey<Item> AFTERSHOCK_ENCHANTABLE = TagKey.create(
            Registries.ITEM,
            ResourceLocation.parse("block_cannon:enchantable/aftershock")
    );
}
