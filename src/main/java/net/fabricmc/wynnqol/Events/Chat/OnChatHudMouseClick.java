package net.fabricmc.wynnqol.Events.Chat;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public interface OnChatHudMouseClick {
    Event<OnChatHudMouseClick> EVENT = EventFactory.createArrayBacked(OnChatHudMouseClick.class,
            (listeners) -> (  mouseX, mouseY, cir) -> {
                for (OnChatHudMouseClick listener : listeners) {
                    listener.interact( mouseX , mouseY, cir);
                }
            });


    void interact(double mouseX, double mouseY, CallbackInfoReturnable<Boolean> cir);
}
