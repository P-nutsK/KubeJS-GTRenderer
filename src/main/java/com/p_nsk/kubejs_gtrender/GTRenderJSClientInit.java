package com.p_nsk.kubejs_gtrender;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import dev.latvian.mods.kubejs.script.ScriptType;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = GTRenderJSMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GTRenderJSClientInit {

    private static final Logger LOGGER = GTRenderJSMod.LOGGER;

    @SubscribeEvent
    public static void onRegisterAdditional(ModelEvent.RegisterAdditional event) {
        LOGGER.info("Registering additional models from GTRenderJS scripts...");
        var jsEvent = new RegisterModelsForRenderEventJS();
        GTRenderJSEvents.REGISTER_ADDITIONAL_MODELS.post(ScriptType.STARTUP, jsEvent);
        for (ResourceLocation model : jsEvent.getModels()) {
            LOGGER.info("Registering additional model: {}", model);
            event.register(model);
        }
        LOGGER.info("Finished registering additional models.");
    }
}
