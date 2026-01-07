package com.p_nsk.kubejs_gtrenderer.hooks;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

import com.p_nsk.kubejs_gtrenderer.GTRJSMod;

// 注: KubeJSのclientInit()でクリアと再登録を行うため、このリロードリスナーは現在使用していません
// リソースリロード時のクリアタイミングの問題を避けるため、GTRendererJSPlugin側で処理しています
@Mod.EventBusSubscriber(modid = GTRJSMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientReloadHook {
    // 現在未使用
}
