package net.fabricmc.wynnqolPublicized.Events.Chat;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface OnSlotRenderPost {
    Event<OnSlotRenderPost> EVENT = EventFactory.createArrayBacked(OnSlotRenderPost.class,
            (listeners) -> ( drawContext, slot, ci) -> {
                for (OnSlotRenderPost listener : listeners) {
                    listener.interact(drawContext, slot, ci);
                }
            });

    void interact(DrawContext drawContext, Slot slot, CallbackInfo ci);
}
