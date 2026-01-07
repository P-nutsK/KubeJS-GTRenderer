package com.p_nsk.kubejs_gtrenderer;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface GTClientRenderEvents {

    EventGroup GROUP = EventGroup.of("GTClientRenderEvents");
    EventHandler REGISTER_RENDERING = GROUP.client("registerDynamicRender", () -> RegisterDynamicRenderEventJS.class);

    static void register() {
        GROUP.register();
    }
}
