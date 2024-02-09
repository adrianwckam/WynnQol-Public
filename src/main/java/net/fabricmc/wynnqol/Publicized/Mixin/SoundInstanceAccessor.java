package net.fabricmc.wynnqol.Publicized.Mixin;

import net.minecraft.client.sound.AbstractSoundInstance;
import org.spongepowered.asm.mixin.Mixin;


import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractSoundInstance.class)
public interface SoundInstanceAccessor {
//    @Shadow
//    protected float pitch;
//    @Shadow
//    protected float volume;
    @Accessor
    float getPitch();
    @Accessor
    float getVolume();
}
