package net.fabricmc.wynnqol.mixin;

import net.fabricmc.wynnqol.Events.Chat.OnSlotRenderPost;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V", shift = At.Shift.BEFORE), method = "drawSlot", cancellable = true)
    public void OnDrawSlot(DrawContext context, Slot slot, CallbackInfo ci) {
        OnSlotRenderPost.EVENT.invoker().interact(context,slot,ci);
//        RenderUtils.drawStringOnSlot("ewe", matrices, slot.x, slot.y, 2);

    }
}
