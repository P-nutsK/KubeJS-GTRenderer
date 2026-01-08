package com.p_nsk.kubejs_gtrender;

import com.gregtechceu.gtceu.api.machine.feature.IMachineFeature;

import net.minecraft.resources.ResourceLocation;

import com.p_nsk.kubejs_gtrender.builder.RenderHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class GTRenderJSRegistry {

    private GTRenderJSRegistry() {}

    // ID -> Hooks
    private static final ConcurrentHashMap<ResourceLocation, RenderHooks<?, ?>> HOOKS = new ConcurrentHashMap<>();

    // 「未登録」警告を1回だけ出すための集合（任意）
    private static final Set<ResourceLocation> WARNED_MISSING = ConcurrentHashMap.newKeySet();

    /** 登録（同じIDは上書き） */
    public static void register(ResourceLocation id, RenderHooks<?, ?> hooks) {
        if (id == null) throw new IllegalArgumentException("id is null");
        if (hooks == null) throw new IllegalArgumentException("hooks is null");

        HOOKS.put(id, hooks);
        WARNED_MISSING.remove(id); // 以前missing警告してても復帰したら解除
    }

    /** 全消し（/reload時に呼ぶ想定） */
    public static void clear() {
        GTRenderJSMod.LOGGER.info("Clearing GTRenderJSRegistry...");
        HOOKS.clear();
        WARNED_MISSING.clear();
    }

    /** 取得（なければnull） */
    @SuppressWarnings("unchecked")
    public static @Nullable RenderHooks<IMachineFeature, Object> getHooks(ResourceLocation id) {
        return (RenderHooks<IMachineFeature, Object>) HOOKS.get(id);
    }

    /** 未登録のときに「一回だけ」警告したい場合に使う（任意） */
    public static boolean shouldWarnMissingOnce(ResourceLocation id) {
        return WARNED_MISSING.add(id);
    }
}
