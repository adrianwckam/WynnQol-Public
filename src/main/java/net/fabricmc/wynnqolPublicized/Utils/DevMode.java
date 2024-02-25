package net.fabricmc.wynnqolPublicized.Utils;

import net.fabricmc.wynnqolPublicized.Events.OnContainerScreenInitCallBack;
import net.minecraft.util.hit.EntityHitResult;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static net.fabricmc.wynnqolPublicized.main.mc;

public class DevMode {
    private static boolean devmode = false;
    private static void test(){

    }
    public static void init(){


        OnContainerScreenInitCallBack.EVENT.register(((screen, title) -> {
            if(!devmode) return;
            if(title!=null){
                ChatUtils.chat("Gui Name: " +title);
            }else{
                ChatUtils.chat("Gui Name: title is null!");
            }

        }));
    }

    public static void onCommandWtfIsThat(){
        assert mc.crosshairTarget != null;
        ChatUtils.chat(ToStringBuilder.reflectionToString(mc.crosshairTarget));
        if(mc.crosshairTarget instanceof EntityHitResult){
            System.out.println("Wtf is that result:");
            System.out.println(((EntityHitResult) mc.crosshairTarget).getEntity());
        }
    }

    public static void onCommandDevTest(){

        test();
        devmode = !devmode;
        ChatUtils.chatWithPrefix("Devmode is now: "+Boolean.toString(devmode));
        if(ItemUtils.getHeldItem()!= null){
            System.out.println(LoreUtils.getLore(ItemUtils.getHeldItem()).toString());
        }
    }
}
