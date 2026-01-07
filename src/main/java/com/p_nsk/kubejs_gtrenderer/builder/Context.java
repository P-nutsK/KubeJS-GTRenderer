package com.p_nsk.kubejs_gtrenderer.builder;

import com.gregtechceu.gtceu.api.machine.feature.IMachineFeature;

import com.google.gson.JsonElement;

public class Context<T extends IMachineFeature> {

    public final Object data;
    public final JsonElement jsonData;

    public Context(JsonElement data, Object dataView) {
        this.data = dataView;
        this.jsonData = data;
    }
}
