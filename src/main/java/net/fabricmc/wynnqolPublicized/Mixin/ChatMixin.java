package net.fabricmc.wynnqolPublicized.Mixin;

import net.fabricmc.wynnqolPublicized.Events.Chat.OnChatHudMouseClick;
import net.fabricmc.wynnqolPublicized.Events.Chat.OnReceiveMessage;
import net.fabricmc.wynnqolPublicized.Events.Chat.OnRenderChatHud;
import net.fabricmc.wynnqolPublicized.Utils.InventoryUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import static net.fabricmc.wynnqolPublicized.main.mc;
@Mixin(ChatHud.class)
public class ChatMixin {
    @Inject(method = "addMessage(Lnet/minecraft/text/Text;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHud;addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V"),
            cancellable = true)
    private void onMessage(Text message, CallbackInfo ci) {
        OnReceiveMessage.EVENT.invoker().interact(message, ci);
    }

    @Inject(method = "mouseClicked", at = @At(value = "HEAD"), cancellable = true)
    private void OnMouseClicked(double mouseX, double mouseY, CallbackInfoReturnable<Boolean> cir){
        OnChatHudMouseClick.EVENT.invoker().interact(mouseX, mouseY, cir);
    }

    @Inject(method = "render", at=@At(value = "HEAD"))
    private void OnRender(DrawContext context, int currentTick, int mouseX, int mouseY, CallbackInfo ci){
        if(InventoryUtils.IsInGui() && mc.currentScreen instanceof ChatScreen){
            OnRenderChatHud.EVENT.invoker().interact(context, currentTick, mouseX, mouseY, ci);
        }

    }
}
