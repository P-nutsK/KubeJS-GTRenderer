package com.p_nsk.kubejs_gtrenderer.builder;

import com.gregtechceu.gtceu.api.machine.feature.IMachineFeature;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import com.p_nsk.kubejs_gtrenderer.KubeJSDynamicRender.Defaults;
import org.jetbrains.annotations.Nullable;

public interface RenderHooks<T extends IMachineFeature> {

    void render(T machine, float partialTick,
                PoseStack poseStack, MultiBufferSource buffer,
                int packedLight, int packedOverlay,
                Defaults<T> defaults,
                @Nullable JsonElement data,
                Object dataView);

    void renderByItem(ItemStack stack, ItemDisplayContext displayContext,
                      PoseStack poseStack, MultiBufferSource buffer,
                      int packedLight, int packedOverlay,
                      Defaults<T> defaults,
                      @Nullable JsonElement data,
                      Object dataView);

    boolean shouldRender(T machine, Vec3 cameraPos, Defaults<T> defaults);

    boolean shouldRenderOffScreen(T machine, Defaults<T> defaults);

    int getViewDistance(Defaults<T> defaults);

    AABB getRenderBoundingBox(T machine, Defaults<T> defaults);

    boolean isBlockEntityRenderer(Defaults<T> defaults);

    static <T extends IMachineFeature> RenderHooks<T> noop() {
        @SuppressWarnings("unchecked")
        RenderHooks<T> cast = (RenderHooks<T>) NoopHooks.INSTANCE;
        return cast;
    }

    enum NoopHooks implements RenderHooks<IMachineFeature> {

        INSTANCE;

        @Override
        public void render(IMachineFeature machine, float partialTick, PoseStack poseStack, MultiBufferSource buffer,
                           int packedLight, int packedOverlay, Defaults<IMachineFeature> defaults,
                           @Nullable JsonElement data, Object dataView) {}

        @Override
        public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack,
                                 MultiBufferSource buffer, int packedLight, int packedOverlay,
                                 Defaults<IMachineFeature> defaults, @Nullable JsonElement data, Object dataView) {}

        @Override
        public boolean shouldRender(IMachineFeature machine, Vec3 cameraPos, Defaults<IMachineFeature> defaults) {
            return false;
        }

        @Override
        public boolean shouldRenderOffScreen(IMachineFeature machine, Defaults<IMachineFeature> defaults) {
            return false;
        }

        @Override
        public int getViewDistance(Defaults<IMachineFeature> defaults) {
            return defaults.getViewDistance();
        }

        @Override
        public AABB getRenderBoundingBox(IMachineFeature machine, Defaults<IMachineFeature> defaults) {
            return defaults.getRenderBoundingBox(machine);
        }

        @Override
        public boolean isBlockEntityRenderer(Defaults<IMachineFeature> defaults) {
            return defaults.isBlockEntityRenderer();
        }
    }
}
