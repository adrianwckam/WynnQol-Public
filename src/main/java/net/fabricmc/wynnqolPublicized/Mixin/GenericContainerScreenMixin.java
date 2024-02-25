package net.fabricmc.wynnqolPublicized.Mixin;

import net.fabricmc.wynnqolPublicized.Events.OnContainerScreenInitCallBack;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(GenericContainerScreen.class)
public abstract class GenericContainerScreenMixin{
    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(GenericContainerScreenHandler handler, PlayerInventory inventory, Text title, CallbackInfo cb) {
        GenericContainerScreen container = ((GenericContainerScreen) (Object) this);
        OnContainerScreenInitCallBack.EVENT.invoker().interact(container, title);
    }

}
