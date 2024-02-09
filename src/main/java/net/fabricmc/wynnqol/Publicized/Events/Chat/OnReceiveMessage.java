package net.fabricmc.wynnqol.Publicized.Events.Chat;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface OnReceiveMessage {
    Event<OnReceiveMessage> EVENT = EventFactory.createArrayBacked(OnReceiveMessage.class,
            (listeners) -> (  message, ci) -> {
                for (OnReceiveMessage listener : listeners) {
                    listener.interact( message , ci);
                }
            });


    void interact(Text message, CallbackInfo ci);
}
