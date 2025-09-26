package net.lghast.blockcannon.client;

import net.lghast.blockcannon.register.ModEntityTypes;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class ModRenderers {
    private static void registerEntityRenders(){
        EntityRenderers.register(
                ModEntityTypes.BLOCK_SHOT.get(),
                BlockShotRenderer::new
        );
    }

    public static void register(){
        registerEntityRenders();
    }
}
