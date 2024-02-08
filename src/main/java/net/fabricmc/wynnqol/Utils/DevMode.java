package net.fabricmc.wynnqol.Utils;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.wynnqol.Events.OnContainerScreenInitCallBack;
import net.fabricmc.wynnqol.Features.SkillPointPreset.ParseGear;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static net.fabricmc.wynnqol.main.mc;

public class DevMode {
    private static boolean devmode = false;
    private static void test(){
        OutlineUtil.addOutline(new OutlineUtil.Outline(33,0,255,255, 255));
    }
    public static void init(){

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("devtest")
                    .executes(context -> {
                        test();
                        devmode = !devmode;
                        ChatUtils.chatWithPrefix("Devmode is now: "+Boolean.toString(devmode));
                        if(ItemUtils.getHeldItem()!= null){
                            System.out.println(LoreUtils.getLore(ItemUtils.getHeldItem()).toString());
                        }
                        return 1;
                    }));
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("wtfisthat")
                    .executes(context -> {
                        assert mc.crosshairTarget != null;
                        ChatUtils.chat(ToStringBuilder.reflectionToString(mc.crosshairTarget));
                        if(mc.crosshairTarget instanceof EntityHitResult){
                            System.out.println("Wtf is that result:");
                            System.out.println(((EntityHitResult) mc.crosshairTarget).getEntity());
                        }
                        return 0;
                    }));
        });

        OnContainerScreenInitCallBack.EVENT.register(((screen, title) -> {
            if(!devmode) return;
            if(title!=null){
                ChatUtils.chat("Gui Name: " +title);
            }else{
                ChatUtils.chat("Gui Name: title is null!");
            }

        }));
    }
}
