package net.fabricmc.wynnqolPublicized.Mixin;

import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyBinding.class)
public abstract class KeyBindingMixin {
    @Inject(method = "isPressed", at = @At("HEAD"), cancellable = true)
    private void OnIsPressed(CallbackInfoReturnable<Boolean> cir){
        KeyBinding key = ((KeyBinding)(Object)this);
    }

    @Inject(method = "wasPressed", at = @At("HEAD"), cancellable = true)
    private void OnWasPressed(CallbackInfoReturnable<Boolean> cir){
        KeyBinding key = ((KeyBinding)(Object)this);
    }
}
