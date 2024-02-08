package net.fabricmc.wynnqol.mixin;
import net.fabricmc.wynnqol.Events.OnSoundPlayCallBack;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.sound.SoundCategory;
//import net.minecraft.sound.SoundEvent;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.fabricmc.wynnqol.main.mc;

//@Mixin(World.class)
//public class SoundEventMixin{
//    @Inject(at = @At("HEAD"), method = "playSound")
//    private void OnSoundPlay(PlayerEntity player, BlockPos pos, SoundEvent sound, SoundCategory category, float volume, float pitch, CallbackInfo ci){
//        String SoundName = sound.getId().toString();
//        System.out.println("QOL SOUND"+SoundName+" "+volume+" "+pitch);
//    }
//
//}

@Mixin(SoundManager.class)
public class SoundEventMixin{
    @Inject(at = @At("HEAD"), method = "play", cancellable = true)
    private void OnSoundPlay(SoundInstance sound, CallbackInfo ci){
        String SoundName = sound.getId().toString();
        SoundInstanceAccessor Accessor = (SoundInstanceAccessor) sound;
        float pitch = Accessor.getPitch();
        float volume = Accessor.getVolume();
        if (mc.player == null || mc.world == null)return;
        OnSoundPlayCallBack.EVENT.invoker().interact( sound, SoundName, pitch, volume, ci);


    }



}

