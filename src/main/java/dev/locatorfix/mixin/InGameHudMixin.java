package dev.locatorfix.mixin;

import dev.locatorfix.FrameState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.hud.bar.LocatorBar;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = InGameHud.class, priority = 900)
abstract class InGameHudMixin {
    @Unique private LocatorBar locatorfix$fallback;
    @Unique private boolean locatorfix$disabled;

    @Inject(method = "render", at = @At("HEAD"))
    private void locatorfix$start(DrawContext context, RenderTickCounter ticks, CallbackInfo ci) {
        FrameState.begin();
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void locatorfix$restore(DrawContext context, RenderTickCounter ticks, CallbackInfo ci) {
        if (locatorfix$disabled || FrameState.rendered()) return;
        try {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client == null || client.world == null || client.player == null
                    || client.options.hudHidden || client.player.isSpectator()
                    || client.player.hasVehicle() || client.getCameraEntity() != client.player) return;
            ClientPlayNetworkHandler network = client.getNetworkHandler();
            if (network == null || !network.getWaypointHandler().hasWaypoint()) return;
            if (locatorfix$fallback == null) locatorfix$fallback = new LocatorBar(client);
            // Never redraw the bar background, XP, level, or other HUD layers.
            // The original renderer uses only server-provided waypoint data.
            locatorfix$fallback.renderAddons(context, ticks);
        } catch (RuntimeException | LinkageError failure) {
            locatorfix$disabled = true;
            System.getLogger("LocatorBarFix").log(System.Logger.Level.WARNING,
                    "Locator fallback disabled for this HUD after a compatibility error", failure);
        }
    }
}
