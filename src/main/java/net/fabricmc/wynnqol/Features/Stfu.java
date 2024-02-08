package net.fabricmc.wynnqol.Features;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.wynnqol.Events.OnSoundPlayCallBack;
import net.fabricmc.wynnqol.Utils.ChatUtils;
public class Stfu {
    private static boolean toggle = false;
    public static void init(){

        ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("stfu")
                    .executes(context -> {
                        toggle = !toggle;
                        if(toggle){
                            ChatUtils.chatWithPrefix("&cMuted minecraft");
                        }else{
                            ChatUtils.chatWithPrefix("&cUnmuted minecraft");
                        }
                        return 1;
                    }));
        }));

        OnSoundPlayCallBack.EVENT.register((sound, SoundName, pitch, volume, ci) -> {
            if(toggle) ci.cancel();
        });
    }

}
