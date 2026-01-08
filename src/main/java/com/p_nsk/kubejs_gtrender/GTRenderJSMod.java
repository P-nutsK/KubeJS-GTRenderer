package com.p_nsk.kubejs_gtrender;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.client.renderer.machine.DynamicRenderManager;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(GTRenderJSMod.MOD_ID)
@SuppressWarnings("removal")
public class GTRenderJSMod {

    public static final String MOD_ID = "kubejs_gtrender";
    public static final Logger LOGGER = LogManager.getLogger();

    public GTRenderJSMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        // Most other hooks are fired on Forge's bus.
        // If we want to use annotations to register event listeners,
        // we need to register our object like this!
        MinecraftForge.EVENT_BUS.register(this);

        if (GTCEu.isClientSide()) {
            initializeDynamicRenders();
        }
    }

    private void initializeDynamicRenders() {
        DynamicRenderManager.register(
                GTRenderJSMod.id("kubejs_dynamic"),
                KubeJSDynamicRender.TYPE);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {}

    private void clientSetup(final FMLClientSetupEvent event) {}

    /**
     * Create a ResourceLocation in the format "modid:path"
     *
     * @param path
     * @return ResourceLocation with the namespace of your mod
     */
    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
