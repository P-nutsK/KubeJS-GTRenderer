// package com.p_nsk.kubejs_gtrenderer.hooks;
//
// import com.gregtechceu.gtceu.GTCEu;
// import com.gregtechceu.gtceu.client.renderer.machine.DynamicRenderManager;
//
// import net.minecraftforge.api.distmarker.Dist;
// import net.minecraftforge.eventbus.api.SubscribeEvent;
// import net.minecraftforge.fml.common.Mod;
// import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
//
// import com.p_nsk.kubejs_gtrenderer.GTRJSMod;
// import com.p_nsk.kubejs_gtrenderer.KubeJSDynamicRender;
//
// @Mod.EventBusSubscriber(modid = GTRJSMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
// public final class GTRJSModClientInit {
//
// @SubscribeEvent
// public static void onClientSetup(FMLClientSetupEvent event) {
// event.enqueueWork(() -> {
// if (!GTCEu.isDataGen()) {
// DynamicRenderManager.register(
// GTRJSMod.id("kubejs_dynamic"),
// KubeJSDynamicRender.TYPE);
// GTRJSMod.LOGGER.info("Registered KubeJS Dynamic Render");
// }
// });
// }
// }
