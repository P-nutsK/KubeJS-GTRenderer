package com.p_nsk.kubejs_gtrender;

import com.p_nsk.kubejs_gtrender.helper.BakedModelRenderer;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;

public class GTRenderJSPlugin extends KubeJSPlugin {

    @Override
    public void registerEvents() {
        GTRenderJSEvents.register();
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("KubeJSDynamicRender", KubeJSDynamicRender.class);
        event.add("BakedModelRenderer", BakedModelRenderer.class);
    }
}
