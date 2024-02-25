package net.fabricmc.wynnqolPublicized.Mixin.Outline;


import net.fabricmc.wynnqolPublicized.Features.Tna.boss.GregOutline;
import net.fabricmc.wynnqolPublicized.Features.Tna.boss.GregUtils;
import net.fabricmc.wynnqolPublicized.Utils.OutlineUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MixinHasOutline {
        @Inject(method = "hasOutline", at = @At("HEAD"), cancellable = true)
        private void outlineEntities(Entity entity, CallbackInfoReturnable<Boolean> ci) {
            int id = entity.getId();
            if( !GregUtils.IsEyeBeaming&& GregOutline.modelIdList.contains(id) ){
                ci.setReturnValue(true);
            }
            for(OutlineUtil.Outline outline : OutlineUtil.OutlineList){
                if(outline.idIsEqual(id)){
                    ci.setReturnValue(true);
                    return;
                }
            }


        }
}
