package com.p_nsk.kubejs_gtrenderer;

import com.gregtechceu.gtceu.api.machine.feature.IMachineFeature;

import net.minecraft.resources.ResourceLocation;

import com.p_nsk.kubejs_gtrenderer.builder.RenderBuilder;
import dev.latvian.mods.kubejs.event.EventJS;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public class RegisterDynamicRenderEventJS extends EventJS {

    public <T extends IMachineFeature> void create(ResourceLocation id, Consumer<RenderBuilder<T>> consumer) {
        GTRJSMod.LOGGER.info("Creating renderer: {}", id);
        RenderBuilder<T> builder = new RenderBuilder<>();
        consumer.accept(builder);
        KubeJSDynamicRenderRegistry.register(id, builder.build());
        GTRJSMod.LOGGER.info("Registered renderer: {}", id);
    }
}
