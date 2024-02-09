package net.fabricmc.wynnqol.Publicized.Events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.sound.SoundInstance;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface OnSoundPlayCallBack {
    Event<OnSoundPlayCallBack> EVENT = EventFactory.createArrayBacked(OnSoundPlayCallBack.class,
            (listeners) -> ( sound, SoundName, pitch, volume, ci) -> {
                for (OnSoundPlayCallBack listener : listeners) {
                    listener.interact( sound, SoundName, pitch, volume, ci);
                }
            });

    void interact(SoundInstance sound,String SoundName,float pitch,float volume, CallbackInfo ci);
}
