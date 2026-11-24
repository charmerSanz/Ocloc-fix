package dev.locatorfix.mixin;

import dev.locatorfix.FrameState;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.bar.LocatorBar;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LocatorBar.class, priority = 900)
abstract class LocatorBarMixin {
    // Only count a completed vanilla marker-render call.
    @Inject(method = "renderAddons", at = @At("RETURN"))
    private void locatorfix$record(DrawContext context, RenderTickCounter ticks, CallbackInfo ci) {
        FrameState.markRendered();
    }
}
