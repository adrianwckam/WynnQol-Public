package net.fabricmc.wynnqol.Publicized.Events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public interface OnGuiKeyPressedCallBack {
    Event<OnGuiKeyPressedCallBack> EVENT = EventFactory.createArrayBacked(OnGuiKeyPressedCallBack.class,
            (listeners) -> (  keyCode,  scanCode,  modifiers,  cir) -> {
                for (OnGuiKeyPressedCallBack listener : listeners) {
                    listener.interact(  keyCode,  scanCode,  modifiers,  cir);

                }
            });


    void interact(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir);
}
