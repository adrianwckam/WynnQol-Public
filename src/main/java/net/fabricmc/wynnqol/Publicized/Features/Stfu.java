package net.fabricmc.wynnqol.Publicized.Features;

import net.fabricmc.wynnqol.Publicized.Events.OnSoundPlayCallBack;
import net.fabricmc.wynnqol.Publicized.Utils.ChatUtils;
public class Stfu {
    private static boolean toggle = false;
    public static void init(){
        OnSoundPlayCallBack.EVENT.register((sound, SoundName, pitch, volume, ci) -> {
            if(toggle) ci.cancel();
        });
    }

    public static int onCommand(){
        toggle = !toggle;
        if(toggle){
            ChatUtils.chatWithPrefix("&cMuted minecraft");
        }else{
            ChatUtils.chatWithPrefix("&cUnmuted minecraft");
        }
        return 1;
    }

}
