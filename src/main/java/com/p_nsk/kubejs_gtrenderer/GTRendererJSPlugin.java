package com.p_nsk.kubejs_gtrenderer;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;

public class GTRendererJSPlugin extends KubeJSPlugin {

    @Override
    public void registerEvents() {
        GTClientRenderEvents.register();
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("KubeJSDynamicRender", KubeJSDynamicRender.class);
    }
}
