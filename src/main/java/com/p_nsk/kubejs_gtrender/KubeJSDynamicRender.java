package com.p_nsk.kubejs_gtrender;

import com.gregtechceu.gtceu.api.machine.feature.IMachineFeature;
import com.gregtechceu.gtceu.client.renderer.machine.DynamicRender;
import com.gregtechceu.gtceu.client.renderer.machine.DynamicRenderType;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.p_nsk.kubejs_gtrender.builder.RenderHooks;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@SuppressWarnings({ "NullableProblems", "unused" })
public final class KubeJSDynamicRender extends DynamicRender<IMachineFeature, KubeJSDynamicRender> {

    @Getter
    private final ResourceLocation renderId;
    private final @Nullable JsonElement rawBinding;

    private static final Codec<JsonElement> JSON_CODEC = Codec.PASSTHROUGH.xmap(
            dyn -> dyn.convert(JsonOps.INSTANCE).getValue(),
            json -> new Dynamic<>(JsonOps.INSTANCE, json));

    public static final Codec<KubeJSDynamicRender> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ResourceLocation.CODEC.fieldOf("id")
                    .forGetter(r -> r.renderId),

            JSON_CODEC.optionalFieldOf("rawBinding")
                    .forGetter(r -> Optional.ofNullable(r.rawBinding)))
            .apply(inst, (id, dataOpt) -> new KubeJSDynamicRender(id, dataOpt.orElse(null))));
    public static final DynamicRenderType<IMachineFeature, KubeJSDynamicRender> TYPE = new DynamicRenderType<>(CODEC);

    ////////////////////////////////////////////
    /// インスタンス部分
    ///////////////////////////////////////////

    public KubeJSDynamicRender(ResourceLocation renderId) {
        this.renderId = renderId;
        this.rawBinding = new JsonObject();
    }

    public KubeJSDynamicRender(ResourceLocation renderId, @Nullable JsonElement rawBinding) {
        this.renderId = renderId;
        this.rawBinding = rawBinding;
    }

    public static KubeJSDynamicRender of(ResourceLocation renderId) {
        return new KubeJSDynamicRender(renderId);
    }

    public static KubeJSDynamicRender of(ResourceLocation renderId, @Nullable JsonElement rawBinding) {
        return new KubeJSDynamicRender(renderId, rawBinding);
    }

    private RenderHooks<IMachineFeature, Object> hooks() {
        RenderHooks<IMachineFeature, Object> h = GTRenderJSRegistry.getHooks(renderId);
        return (h != null) ? h : RenderHooks.noop();
    }

    /////////////////////////////////////////////////
    /// DynamicRender 実装部分
    /////////////////////////////////////////////////

    @Override
    public @NotNull DynamicRenderType<IMachineFeature, KubeJSDynamicRender> getType() {
        return TYPE;
    }

    @Override
    public void render(IMachineFeature machine, float partialTick,
                       PoseStack poseStack, MultiBufferSource buffer,
                       int packedLight, int packedOverlay) {
        hooks().render(machine, partialTick, poseStack, buffer, packedLight, packedOverlay, defaults, rawBinding);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack,
                             MultiBufferSource buffer, int packedLight, int packedOverlay) {
        hooks().renderByItem(stack, displayContext, poseStack, buffer, packedLight, packedOverlay, defaults,
                rawBinding);
    }

    @Override
    public boolean shouldRender(IMachineFeature machine, Vec3 cameraPos) {
        return hooks().shouldRender(machine, cameraPos, defaults);
    }

    @Override
    public boolean shouldRenderOffScreen(IMachineFeature machine) {
        return hooks().shouldRenderOffScreen(machine, defaults);
    }

    @Override
    public int getViewDistance() {
        return hooks().getViewDistance(defaults);
    }

    @Override
    public AABB getRenderBoundingBox(IMachineFeature machine) {
        return hooks().getRenderBoundingBox(machine, defaults);
    }

    @Override
    public boolean isBlockEntityRenderer() {
        return hooks().isBlockEntityRenderer(defaults);
    }
    // Defaults は「GTCEuのデフォルト実装（super）」へアクセスするためのブリッジ
    /*
     * -----------------------------
     * Hooks / Defaults 定義
     * -----------------------------
     */

    public interface Defaults<T extends IMachineFeature> {

        void renderByItem(ItemStack stack, ItemDisplayContext displayContext,
                          PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay);

        boolean shouldRender(T machine, Vec3 cameraPos);

        boolean shouldRenderOffScreen(T machine);

        int getViewDistance();

        AABB getRenderBoundingBox(T machine);

        boolean isBlockEntityRenderer();
    }

    private final Defaults<IMachineFeature> defaults = new Defaults<>() {

        @Override
        public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack,
                                 MultiBufferSource buffer, int packedLight, int packedOverlay) {
            KubeJSDynamicRender.super.renderByItem(stack, displayContext, poseStack, buffer, packedLight,
                    packedOverlay);
        }

        @Override
        public boolean shouldRender(IMachineFeature m, Vec3 cam) {
            return KubeJSDynamicRender.super.shouldRender(m, cam);
        }

        @Override
        public boolean shouldRenderOffScreen(IMachineFeature m) {
            return KubeJSDynamicRender.super.shouldRenderOffScreen(m);
        }

        @Override
        public int getViewDistance() {
            return KubeJSDynamicRender.super.getViewDistance();
        }

        @Override
        public AABB getRenderBoundingBox(IMachineFeature m) {
            return KubeJSDynamicRender.super.getRenderBoundingBox(m);
        }

        @Override
        public boolean isBlockEntityRenderer() {
            return KubeJSDynamicRender.super.isBlockEntityRenderer();
        }
    };
}
