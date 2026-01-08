package com.p_nsk.kubejs_gtrender.builder;

import com.gregtechceu.gtceu.api.machine.feature.IMachineFeature;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import com.mojang.blaze3d.vertex.PoseStack;

public class ItemRenderContext<T extends IMachineFeature, Binding> extends Context<T, Binding> {

    public final ItemStack stack;
    public final ItemDisplayContext displayContext;
    public final PoseStack poseStack;
    public final MultiBufferSource buffer;
    public final int packedLight;
    public final int packedOverlay;

    public ItemRenderContext(Binding binding, ItemStack stack, ItemDisplayContext displayContext,
                             PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        super(binding);
        this.stack = stack;
        this.displayContext = displayContext;
        this.poseStack = poseStack;
        this.buffer = buffer;
        this.packedLight = packedLight;
        this.packedOverlay = packedOverlay;
    }
}
