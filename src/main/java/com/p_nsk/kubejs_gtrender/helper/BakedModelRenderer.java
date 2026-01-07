package com.p_nsk.kubejs_gtrender.helper;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraftforge.client.model.data.ModelData;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.latvian.mods.kubejs.typings.Info;

@SuppressWarnings("unused")
@Info("Helper class for rendering BakedModel instances.")
public class BakedModelRenderer {

    public static final RandomSource random = RandomSource.create(42);

    @Info("rgb = 1,1,1; packedLight = LightTexture.FULL_BRIGHT; packedOverlay = OverlayTexture.NO_OVERLAY; modelData = ModelData.EMPTY; renderType = null")
    public static void renderModel(PoseStack.Pose pose, VertexConsumer consumer, BakedModel model) {
        renderModel(pose, consumer, model, null);
    }

    @Info("rgb = 1,1,1; packedLight = LightTexture.FULL_BRIGHT; packedOverlay = OverlayTexture.NO_OVERLAY; modelData = ModelData.EMPTY")
    public static void renderModel(PoseStack.Pose pose, VertexConsumer consumer, BakedModel model,
                                   RenderType renderType) {
        renderModel(pose, consumer, model, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, renderType);
    }

    @Info("rgb = 1,1,1; modelData = ModelData.EMPTY")
    public static void renderModel(PoseStack.Pose pose, VertexConsumer consumer, BakedModel model, int packedLight,
                                   int packedOverlay, RenderType renderType) {
        renderModel(pose, consumer, model, packedLight, packedOverlay, ModelData.EMPTY, renderType);
    }

    @Info("rgb = 1,1,1")
    public static void renderModel(PoseStack.Pose pose, VertexConsumer consumer, BakedModel model, int packedLight,
                                   int packedOverlay, ModelData modelData, RenderType renderType) {
        renderModel(pose, consumer, model, 1, 1, 1, packedLight, packedOverlay, modelData, renderType);
    }

    public static void renderModel(PoseStack.Pose pose, VertexConsumer consumer, BakedModel model, float red,
                                   float green, float blue, int packedLight, int packedOverlay, ModelData modelData,
                                   RenderType renderType) {
        for (Direction direction : Direction.values()) {
            for (var quad : model.getQuads(null, direction, random, modelData, renderType)) {
                consumer.putBulkData(pose, quad, red, green, blue, 1, packedLight, packedOverlay, false);
            }
        }

        for (var quad : model.getQuads(null, null, random, modelData, renderType)) {
            consumer.putBulkData(pose, quad, red, green, blue, 1, packedLight, packedOverlay, false);
        }
    }
}
