package net.fabricmc.wynnqolPublicized.Mixin;

import net.fabricmc.wynnqolPublicized.Events.Chat.OnSlotRenderPost;
import net.fabricmc.wynnqolPublicized.Events.OnContainerScreenInitCallBack;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin {
    @Shadow @Final protected Text playerInventoryTitle;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V", shift = At.Shift.BEFORE), method = "drawSlot", cancellable = true)
    public void OnDrawSlot(DrawContext context, Slot slot, CallbackInfo ci) {
        OnSlotRenderPost.EVENT.invoker().interact(context,slot,ci);
//        RenderUtils.drawStringOnSlot("ewe", matrices, slot.x, slot.y, 2);

    }


}
