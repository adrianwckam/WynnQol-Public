package net.fabricmc.wynnqol.mixin;

import net.minecraft.client.sound.AbstractSoundInstance;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;


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
