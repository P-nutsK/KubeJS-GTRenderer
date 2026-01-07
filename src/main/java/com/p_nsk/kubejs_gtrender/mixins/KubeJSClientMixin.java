package com.p_nsk.kubejs_gtrender.mixins;

import com.p_nsk.kubejs_gtrender.GTRenderJSEvents;
import com.p_nsk.kubejs_gtrender.GTRenderJSRegistry;
import com.p_nsk.kubejs_gtrender.RegisterDynamicRenderEventJS;
import dev.latvian.mods.kubejs.client.KubeJSClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = KubeJSClient.class, remap = false)
public class KubeJSClientMixin {

    @Inject(method = "reloadClientScripts", at = @At("RETURN"))
    private static void kubejs_gtrender$onClientScriptsReloaded(CallbackInfo ci) {
        // スクリプトリロード時に古い登録をクリア
        GTRenderJSRegistry.clear();
        GTRenderJSEvents.REGISTER_RENDERING.post(dev.latvian.mods.kubejs.script.ScriptType.CLIENT,
                new RegisterDynamicRenderEventJS());
    }
}
