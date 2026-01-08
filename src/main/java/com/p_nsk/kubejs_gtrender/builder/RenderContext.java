package com.p_nsk.kubejs_gtrender.builder;

import com.gregtechceu.gtceu.api.machine.feature.IMachineFeature;

import net.minecraft.client.renderer.MultiBufferSource;

import com.mojang.blaze3d.vertex.PoseStack;

public class RenderContext<T extends IMachineFeature, Binding> extends Context<T, Binding> {

    public final T machine;
    public final float partialTick;
    public final PoseStack poseStack;
    public final MultiBufferSource buffer;
    public final int packedLight;
    public final int packedOverlay;

    public RenderContext(Binding bindings, T machine, float partialTick, PoseStack poseStack,
                         MultiBufferSource buffer, int packedLight, int packedOverlay) {
        super(bindings);
        this.machine = machine;
        this.partialTick = partialTick;
        this.poseStack = poseStack;
        this.buffer = buffer;
        this.packedLight = packedLight;
        this.packedOverlay = packedOverlay;
    }
}
