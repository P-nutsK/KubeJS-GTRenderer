package com.p_nsk.kubejs_gtrender;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface GTRenderJSEvents {

    EventGroup GROUP = EventGroup.of("GTRenderJSEvents");
    EventHandler REGISTER_RENDERING = GROUP.client("registerDynamicRender", () -> RegisterDynamicRenderEventJS.class);
    EventHandler REGISTER_ADDITIONAL_MODELS = GROUP.startup("registerModelsForRender",
            () -> RegisterModelsForRenderEventJS.class);

    static void register() {
        GROUP.register();
    }
}
