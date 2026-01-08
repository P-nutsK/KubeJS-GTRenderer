package com.p_nsk.kubejs_gtrender.builder;

import com.gregtechceu.gtceu.api.machine.feature.IMachineFeature;

import dev.latvian.mods.kubejs.typings.Info;

public class Context<T extends IMachineFeature, Binding> {

    @Info("""
            The bindings object associated with this context.
            Introducing the argument system for KubeJSDynamicRender.of("ns:id", here)!
            It can accept anything that is JSON Serializable.
            By default, values are inputted as is, but you can preprocess them with builder.prepareBinding().
            Note: This value is cached. Please use pure values only.
            """)
    public final Binding binding;

    public Context(Binding binding) {
        this.binding = binding;
    }
}
