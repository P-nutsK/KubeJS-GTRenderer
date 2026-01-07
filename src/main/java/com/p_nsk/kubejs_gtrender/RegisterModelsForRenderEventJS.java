package com.p_nsk.kubejs_gtrender;

import net.minecraft.resources.ResourceLocation;

import dev.latvian.mods.kubejs.event.EventJS;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class RegisterModelsForRenderEventJS extends EventJS {

    @Getter
    private final List<ResourceLocation> models = new ArrayList<>();

    public void register(ResourceLocation id) {
        models.add(id);
    }
}
