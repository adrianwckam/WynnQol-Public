package net.fabricmc.wynnqol.mixin;

import net.fabricmc.wynnqol.Events.OnGuiKeyPressedCallBack;
import net.fabricmc.wynnqol.Events.PostScreenRenderCallBack;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Inject(method = "keyPressed(III)Z", at = @At("HEAD"), cancellable = true)
    private void onKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        OnGuiKeyPressedCallBack.EVENT.invoker().interact(  keyCode,  scanCode,  modifiers,  cir);
    }

    @Inject(method = "render", at = @At("TAIL"), cancellable = true)
    private void onRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci){
        PostScreenRenderCallBack.EVENT.invoker().interact(context,  mouseX,  mouseY,  delta,  ci);
    }


}
