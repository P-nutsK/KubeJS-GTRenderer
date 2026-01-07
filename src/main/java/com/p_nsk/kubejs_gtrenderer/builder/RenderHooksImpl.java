package com.p_nsk.kubejs_gtrenderer.builder;

import com.gregtechceu.gtceu.api.machine.feature.IMachineFeature;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import com.p_nsk.kubejs_gtrenderer.KubeJSDynamicRender;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public final class RenderHooksImpl<T extends IMachineFeature> implements RenderHooks<T> {

    private final Consumer<RenderContext<T>> renderHook; // 必須

    private final @Nullable BiPredicate<T, Vec3> shouldRenderHook;
    private final @Nullable Predicate<T> offscreenHook;
    private final @Nullable Integer viewDistance;
    private final @Nullable Function<T, AABB> boundingBoxHook;
    private final @Nullable Consumer<ItemRenderContext<T>> renderByItemHook;
    private final @Nullable Boolean isBlockEntityRenderer;

    public RenderHooksImpl(
                           Consumer<RenderContext<T>> renderHook,
                           @Nullable BiPredicate<T, Vec3> shouldRenderHook,
                           @Nullable Predicate<T> offscreenHook,
                           @Nullable Integer viewDistance,
                           @Nullable Function<T, AABB> boundingBoxHook,
                           @Nullable Consumer<ItemRenderContext<T>> renderByItemHook,
                           @Nullable Boolean isBlockEntityRenderer) {
        this.renderHook = renderHook;
        this.shouldRenderHook = shouldRenderHook;
        this.offscreenHook = offscreenHook;
        this.viewDistance = viewDistance;
        this.boundingBoxHook = boundingBoxHook;
        this.renderByItemHook = renderByItemHook;
        this.isBlockEntityRenderer = isBlockEntityRenderer;
    }

    @Override
    public void render(T machine, float partialTick,
                       PoseStack poseStack, MultiBufferSource buffer,
                       int packedLight, int packedOverlay,
                       KubeJSDynamicRender.Defaults<T> defaults,
                       @Nullable JsonElement data, Object dataView) {
        renderHook.accept(new RenderContext<>(
                data, dataView, machine, partialTick,
                poseStack, buffer, packedLight, packedOverlay));
    }

    @Override
    public boolean shouldRender(T machine, Vec3 cameraPos,
                                KubeJSDynamicRender.Defaults<T> defaults) {
        if (shouldRenderHook != null) return shouldRenderHook.test(machine, cameraPos);
        return defaults.shouldRender(machine, cameraPos);
    }

    @Override
    public boolean shouldRenderOffScreen(T machine,
                                         KubeJSDynamicRender.Defaults<T> defaults) {
        if (offscreenHook != null) return offscreenHook.test(machine);
        return defaults.shouldRenderOffScreen(machine);
    }

    @Override
    public int getViewDistance(KubeJSDynamicRender.Defaults<T> defaults) {
        if (viewDistance != null) return viewDistance;
        return defaults.getViewDistance();
    }

    @Override
    public AABB getRenderBoundingBox(T machine,
                                     KubeJSDynamicRender.Defaults<T> defaults) {
        if (boundingBoxHook != null) return boundingBoxHook.apply(machine);
        return defaults.getRenderBoundingBox(machine);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack,
                             MultiBufferSource buffer, int packedLight, int packedOverlay,
                             KubeJSDynamicRender.Defaults<T> defaults, @Nullable JsonElement data, Object dataView) {
        if (renderByItemHook != null) {
            renderByItemHook.accept(new ItemRenderContext<>(data, dataView, stack, displayContext, poseStack, buffer,
                    packedLight, packedOverlay));
            return;
        }
        defaults.renderByItem(stack, displayContext, poseStack, buffer, packedLight, packedOverlay);
    }

    @Override
    public boolean isBlockEntityRenderer(KubeJSDynamicRender.Defaults<T> defaults) {
        if (isBlockEntityRenderer != null) return isBlockEntityRenderer;
        return defaults.isBlockEntityRenderer();
    }
}
