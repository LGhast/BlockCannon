package net.lghast.blockcannon;

import net.lghast.blockcannon.client.ModItemProperties;
import net.lghast.blockcannon.client.ModRenderers;
import net.lghast.blockcannon.config.ServerConfig;
import net.lghast.blockcannon.register.ModEnchantments;
import net.lghast.blockcannon.register.ModEntityTypes;
import net.lghast.blockcannon.register.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;


@Mod(BlockCannon.MOD_ID)
public class BlockCannon {
    public static final String MOD_ID = "block_cannon";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BlockCannon(IEventBus modEventBus, ModContainer modContainer) {
        ModItems.register(modEventBus);
        ModEntityTypes.register(modEventBus);

        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
    }

    public void onClientSetup(FMLClientSetupEvent event){
        event.enqueueWork(()->{
            ModRenderers.register();
            ModItemProperties.register();
        });
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if(event.getTabKey() == CreativeModeTabs.COMBAT){
            event.accept(ModItems.BLOCK_CANNON);
        }
    }
}
