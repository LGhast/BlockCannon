package net.lghast.blockcannon.register;

import net.lghast.blockcannon.BlockCannon;
import net.lghast.blockcannon.common.item.BlockCannonItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BlockCannon.MOD_ID);

    public static final DeferredItem<Item> BLOCK_CANNON = ITEMS.register("block_cannon",
            ()-> new BlockCannonItem(new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
