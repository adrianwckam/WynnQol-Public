package net.fabricmc.wynnqol.Features.chat;

import net.fabricmc.wynnqol.Events.Chat.OnChatHudMouseClick;
import net.fabricmc.wynnqol.Events.Chat.OnRenderChatHud;
import net.fabricmc.wynnqol.Utils.ChatUtils;
import net.fabricmc.wynnqol.Utils.RenderUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Objects;

import static net.fabricmc.wynnqol.Features.chat.ChatChannel.HideSwitchMessage;
import static net.fabricmc.wynnqol.main.mc;

public class ChatTab {
    private static final RenderUtils.ui.RectangleButton All = new RenderUtils.ui.RectangleButton("All",-100,-100, 0x00000000, ()->{
        HideSwitchMessage = true;
        ChatUtils.sendCommand("chat a");
    });
    private static final RenderUtils.ui.RectangleButton Party = new RenderUtils.ui.RectangleButton("Party",-100,-100, 0x00000000, ()->{
        HideSwitchMessage = true;
        ChatUtils.sendCommand("chat p");
    });
    private static final RenderUtils.ui.RectangleButton Guild = new RenderUtils.ui.RectangleButton("Guild",-100,-100, 0x00000000, ()->{
        HideSwitchMessage = true;
        ChatUtils.sendCommand("chat g");
    });
    private static final RenderUtils.ui.RectangleButton Reply = new RenderUtils.ui.RectangleButton("Reply",-100,-100, 0x00000000, ()->{
        HideSwitchMessage = true;
        ChatUtils.sendCommand("chat r");
    });

    private static final RenderUtils.ui.RectangleButton Wynnic = new RenderUtils.ui.RectangleButton("Wynnic",-100,-100, 0x00000000, ()->{
        WynnicTranslator.speakWynnic = !WynnicTranslator.speakWynnic;
    });
    private static void HandleDraw(RenderUtils.ui.RectangleButton btn, DrawContext drawContext, int ChangeInX){
        btn.x = ChangeInX;
        btn.y = RenderUtils.screen.getHeight(false)-20 - mc.textRenderer.fontHeight;
        btn.draw(drawContext);
    }
    public static void init(){
        OnRenderChatHud.EVENT.register((drawContext, currentTick, mouseX, mouseY, ci) -> {
            int ChangeInX = 6;
            if(true) {
                if(Objects.equals(All.text, ChatChannel.Channel)){
                    All.color = 0xFF00FF00;
                }else{
                    All.color = 0xFFFFFFFF;
                }
                HandleDraw(All, drawContext, ChangeInX);
                ChangeInX += 6+ mc.textRenderer.getWidth(All.text)+2 ;
            }

            if(true) {
                if(Objects.equals(Party.text, ChatChannel.Channel)){
                    Party.color = 0xFF00FF00;
                }else{
                    Party.color = 0xFFFFFFFF;
                }
                HandleDraw(Party, drawContext, ChangeInX);
                ChangeInX += 6+ mc.textRenderer.getWidth(Party.text)+2  ;
            }
            if(true) {
                if(Objects.equals(Guild.text, ChatChannel.Channel)){
                    Guild.color = 0xFF00FF00;
                }else{
                    Guild.color = 0xFFFFFFFF;
                }
                HandleDraw(Guild, drawContext, ChangeInX);
                ChangeInX += 6+ mc.textRenderer.getWidth(Guild.text)+2  ;
            }
            if(true) {
                if(Objects.equals(Reply.text, ChatChannel.Channel)){
                    Reply.color = 0xFF00FF00;
                }else{
                    Reply.color = 0xFFFFFFFF;
                }
                HandleDraw(Reply, drawContext, ChangeInX);
                ChangeInX += 6+ mc.textRenderer.getWidth(Reply.text)+2  ;
            }

            if(true) {
                if(WynnicTranslator.speakWynnic){
                    Wynnic.color = 0xFF00FF00;
                }else{
                    Wynnic.color = 0xFFFFFFFF;
                }
                HandleDraw(Wynnic, drawContext, ChangeInX);
                ChangeInX += 6+ mc.textRenderer.getWidth(Wynnic.text)+2  ;
            }

        });

        OnChatHudMouseClick.EVENT.register((mouseX, mouseY, cir) -> {
            if(true) All.click((int) mouseX, (int) mouseY);
            if(true) Party.click((int) mouseX, (int) mouseY);
            if(true) Guild.click((int) mouseX, (int) mouseY);
            if(true) Reply.click((int) mouseX, (int) mouseY);
            if(true) Wynnic.click((int) mouseX, (int) mouseY);
        });
    }
}
