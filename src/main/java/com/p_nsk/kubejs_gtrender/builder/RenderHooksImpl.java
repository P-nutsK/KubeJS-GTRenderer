package com.p_nsk.kubejs_gtrender.builder;

import com.gregtechceu.gtceu.api.machine.feature.IMachineFeature;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import com.p_nsk.kubejs_gtrender.KubeJSDynamicRender;
import dev.latvian.mods.kubejs.util.JsonIO;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public final class RenderHooksImpl<T extends IMachineFeature, Binding> implements RenderHooks<T, Binding> {

    private final Consumer<RenderContext<T, Binding>> renderHook; // 必須

    private final @Nullable BiPredicate<T, Vec3> shouldRenderHook;
    private final @Nullable Predicate<T> offscreenHook;
    private final @Nullable Integer viewDistance;
    private final @Nullable Function<T, AABB> boundingBoxHook;
    private final @Nullable Consumer<ItemRenderContext<T, Binding>> renderByItemHook;
    private final @Nullable Boolean isBlockEntityRenderer;
    private final @NotNull Function<Object, Binding> prepareBinding;
    private final Map<JsonElement, Binding> bindingCache = new HashMap<>();

    public RenderHooksImpl(
                           Consumer<RenderContext<T, Binding>> renderHook,
                           @Nullable BiPredicate<T, Vec3> shouldRenderHook,
                           @Nullable Predicate<T> offscreenHook,
                           @Nullable Integer viewDistance,
                           @Nullable Function<T, AABB> boundingBoxHook,
                           @Nullable Consumer<ItemRenderContext<T, Binding>> renderByItemHook,
                           @Nullable Boolean isBlockEntityRenderer,
                           @Nullable Function<Object, Binding> prepareBinding) {
        this.renderHook = renderHook;
        this.shouldRenderHook = shouldRenderHook;
        this.offscreenHook = offscreenHook;
        this.viewDistance = viewDistance;
        this.boundingBoxHook = boundingBoxHook;
        this.renderByItemHook = renderByItemHook;
        this.isBlockEntityRenderer = isBlockEntityRenderer;
        // noinspection unchecked
        this.prepareBinding = Objects.requireNonNullElse(prepareBinding,
                (Function<Object, Binding>) Function.identity());
    }

    public Binding getBinding(JsonElement raw) {
        Object rawView = JsonIO.toObject(raw);
        return bindingCache.computeIfAbsent(
                raw,
                rd -> prepareBinding.apply(rawView));
    }

    @Override
    public void render(T machine, float partialTick,
                       PoseStack poseStack, MultiBufferSource buffer,
                       int packedLight, int packedOverlay,
                       KubeJSDynamicRender.Defaults<T> defaults,
                       @Nullable JsonElement rawBinding) {
        renderHook.accept(new RenderContext<>(
                getBinding(rawBinding), machine, partialTick,
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
                             KubeJSDynamicRender.Defaults<T> defaults, @Nullable JsonElement rawBinding) {
        if (renderByItemHook != null) {
            renderByItemHook
                    .accept(new ItemRenderContext<>(getBinding(rawBinding), stack, displayContext, poseStack, buffer,
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
