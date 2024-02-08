package net.fabricmc.wynnqol.Events.Chat;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public interface OnRenderChatHud {
    Event<OnRenderChatHud> EVENT = EventFactory.createArrayBacked(OnRenderChatHud.class,
            (listeners) -> (  context, currentTick, mouseX,mouseY, ci) -> {
                for (OnRenderChatHud listener : listeners) {
                    listener.interact( context, currentTick, mouseX,mouseY, ci);
                }
            });


    void interact(DrawContext context, int currentTick, int mouseX, int mouseY, CallbackInfo ci);
}
