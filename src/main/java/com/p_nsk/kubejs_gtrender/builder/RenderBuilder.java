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
public class RenderBuilder<T extends IMachineFeature, Binding> {

    @Nullable
    Consumer<RenderContext<T, Binding>> renderHook;
    @Nullable
    Consumer<ItemRenderContext<T, Binding>> renderByItemHook;
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
    @Nullable
    Function<Object, Binding> prepareBindings;

    public RenderBuilder<T, Binding> render(Consumer<RenderContext<T, Binding>> render) {
        this.renderHook = render;
        return this;
    }

    public RenderBuilder<T, Binding> renderByItem(Consumer<ItemRenderContext<T, Binding>> renderByItem) {
        this.renderByItemHook = renderByItem;
        return this;
    }

    public RenderBuilder<T, Binding> shouldRender(BiPredicate<T, Vec3> shouldRender) {
        this.shouldRenderHook = shouldRender;
        return this;
    }

    public RenderBuilder<T, Binding> shouldRenderOffScreen(Predicate<T> shouldRenderOffScreen) {
        this.shouldRenderOffScreenHook = shouldRenderOffScreen;
        return this;
    }

    @Info("Default: 64")
    public RenderBuilder<T, Binding> viewDistance(int distance) {
        this.viewDistance = distance;
        return this;
    }

    public RenderBuilder<T, Binding> renderBoundingBox(Function<T, AABB> getRenderBoundingBox) {
        this.getRenderBoundingBoxHook = getRenderBoundingBox;
        return this;
    }

    @Info("Default: true")
    RenderBuilder<T, Binding> isBlockEntityRenderer(boolean isBlockEntityRenderer) {
        this.isBlockEntityRenderer = isBlockEntityRenderer;
        return this;
    }

    @Info("""
            With KubeJSDynamicRender.of("ns:id", here), you can pass arguments!
            It accepts anything that is JSON Serializable.
            The value passed as the second argument to KubeJSDynamicRender.of will be JSON serialized and then deserialized, and passed as is. Therefore, the input value must be JSON Serializable.
            The output accepts all values representable by JavaScript.
            This result is cached on each client reload, so it must be pure. If you want to use non-deterministic elements, you can return functions or objects containing functions.
            """)
    RenderBuilder<T, Binding> prepareBindings(Function<Object, Binding> b) {
        this.prepareBindings = b;
        return this;
    }

    public RenderHooks<T, Binding> build() {
        if (renderHook == null) {
            throw new IllegalStateException("render method is not set");
        }

        return new RenderHooksImpl<>(renderHook,
                shouldRenderHook,
                shouldRenderOffScreenHook,
                viewDistance,
                getRenderBoundingBoxHook,
                renderByItemHook,
                isBlockEntityRenderer, prepareBindings);
    }
}
