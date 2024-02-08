package net.fabricmc.wynnqol.mixin;

import net.fabricmc.wynnqol.Events.Chat.OnSendMessageCallBack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.ClientPlayNetworkHandler;

@Mixin(ClientPlayNetworkHandler.class)
public class SendMessageMixin {
    @Inject(at = @At("HEAD"), method = "sendChatMessage", cancellable = true)
    private void OnSendMessage(String content, CallbackInfo ci){
        OnSendMessageCallBack.EVENT.invoker().interact(content, ci);
    }
}
