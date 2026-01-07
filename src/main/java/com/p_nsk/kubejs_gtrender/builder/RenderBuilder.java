package com.p_nsk.kubejs_gtrender.builder;

import com.gregtechceu.gtceu.api.machine.feature.IMachineFeature;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import dev.latvian.mods.kubejs.typings.Info;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public class RenderBuilder<T extends IMachineFeature> {

    @Nullable
    Consumer<RenderContext<T>> renderHook;
    @Nullable
    Consumer<ItemRenderContext<T>> renderByItemHook;
    @Nullable
    BiPredicate<T, Vec3> shouldRenderHook;
    @Nullable
    Predicate<T> shouldRenderOffScreenHook;
    @Nullable
    Integer viewDistance;
    @Nullable
    Function<T, AABB> getRenderBoundingBoxHook;
    @Nullable
    Boolean isBlockEntityRenderer;

    public RenderBuilder<T> render(Consumer<RenderContext<T>> b) {
        this.renderHook = b;
        return this;
    }

    public RenderBuilder<T> renderByItem(Consumer<ItemRenderContext<T>> b) {
        this.renderByItemHook = b;
        return this;
    }

    public RenderBuilder<T> shouldRender(BiPredicate<T, Vec3> b) {
        this.shouldRenderHook = b;
        return this;
    }

    public RenderBuilder<T> shouldRenderOffScreen(Predicate<T> b) {
        this.shouldRenderOffScreenHook = b;
        return this;
    }

    @Info("Default: 64")
    public RenderBuilder<T> viewDistance(int distance) {
        this.viewDistance = distance;
        return this;
    }

    public RenderBuilder<T> renderBoundingBox(Function<T, AABB> b) {
        this.getRenderBoundingBoxHook = b;
        return this;
    }

    @Info("Default: true")
    RenderBuilder<T> isBlockEntityRenderer(boolean b) {
        this.isBlockEntityRenderer = b;
        return this;
    }

    public RenderHooks<T> build() {
        if (renderHook == null) {
            throw new IllegalStateException("render method is not set");
        }

        return new RenderHooksImpl<>(renderHook,
                shouldRenderHook,
                shouldRenderOffScreenHook,
                viewDistance,
                getRenderBoundingBoxHook,
                renderByItemHook,
                isBlockEntityRenderer);
    }
}
