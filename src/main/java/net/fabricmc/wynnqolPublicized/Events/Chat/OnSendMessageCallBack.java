package net.fabricmc.wynnqolPublicized.Events.Chat;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface OnSendMessageCallBack {

    Event<OnSendMessageCallBack> EVENT = EventFactory.createArrayBacked(OnSendMessageCallBack.class,
            (listeners) -> (content,ci) -> {
                for (OnSendMessageCallBack listener : listeners) {
                    listener.interact(content, ci);
//                    ActionResult result = listener.interact(player, sheep);
//
//                    if(result != ActionResult.PASS) {
//                        return result;
//                    }
                }

                //return ActionResult.PASS;
            });

    void interact(String content, CallbackInfo ci);
}
