package net.fabricmc.wynnqol.mixin.Outline;


import net.fabricmc.wynnqol.Features.tna.boss.GregOutline;
import net.fabricmc.wynnqol.Features.tna.boss.GregUtils;
import net.fabricmc.wynnqol.Utils.OutlineUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.fabricmc.wynnqol.Utils.OutlineUtil.OutlineList;

@Mixin(MinecraftClient.class)
public class MixinHasOutline {
        @Inject(method = "hasOutline", at = @At("HEAD"), cancellable = true)
        private void outlineEntities(Entity entity, CallbackInfoReturnable<Boolean> ci) {
            int id = entity.getId();
            if( !GregUtils.IsEyeBeaming&& GregOutline.modelIdList.contains(id) ){
                ci.setReturnValue(true);
            }
            for(OutlineUtil.Outline outline : OutlineList){
                if(outline.idIsEqual(id)){
                    ci.setReturnValue(true);
                    return;
                }
            }


        }
}
