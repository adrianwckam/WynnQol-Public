package net.fabricmc.wynnqol.Publicized.Utils;

import net.fabricmc.wynnqol.Publicized.Mixin.inGameHudAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class ChatUtils {
    private static MinecraftClient mc = MinecraftClient.getInstance();
    public static void send(String str){
        str = str.replace("&","§");
        mc.getNetworkHandler().sendChatMessage(str);
    }
    public static String toMcFormatted(String str){
        return str.replace("&","§");
    }

    public static void sendCommand(String str){
        str = str.replace("&","§");
        mc.getNetworkHandler().sendChatCommand(str);
    }
    public static void chat(String str,Boolean ActionBar ){
        str = str.replace("&","§");
        mc.player.sendMessage(Text.literal(str),ActionBar);
    }
    public static void chat(String str){
        str = str.replace("&","§");
        mc.player.sendMessage(Text.literal(str), false);
        //mc.inGameHud.getChatHud().addMessage(Text.literal(str));
    }
    public static void chat(Text text){
        mc.player.sendMessage(text, false);
        //mc.inGameHud.getChatHud().addMessage(Text.literal(str));
    }
    public static void chatWithPrefix(String str){
        str = str.replace("&","§");
        mc.player.sendMessage(Text.literal("§7[§aQOL§7] §d"+str),false);
    }
    public static String colorCode(String str){
        return str.replace("&","§");
    }

    public static String removeFormatting(String str){
        return str.replaceAll("§.", "");
    }

    public static void showTitle(String str){
        str = str.replace("&","§");
        mc.inGameHud.setTitle(Text.literal(str));
    }
    public static void showSubTitle(String str){
        str = str.replace("&","§");
        mc.inGameHud.setSubtitle(Text.literal(str));
    }

    public static void setTitleTicks(int fadeInTicks,int remainTicks,int fadeOutTicks){
        mc.inGameHud.setTitleTicks( fadeInTicks, remainTicks, fadeOutTicks);
    }

    public static void addToSentMessageHistory(String text){
        mc.inGameHud.getChatHud().addToMessageHistory(text);
    }

    public static Text getTitle(){

        return ((inGameHudAccessor) mc.inGameHud).getTitle();
    }

    public static Text getSubtitle(){
        return ((inGameHudAccessor) mc.inGameHud).getSubtitle();
    }

    public static Text getActionBar(){
        return ((inGameHudAccessor) mc.inGameHud).getOverlayMessage();
    }

    public static String removeText(String text){
        return text.replaceAll("[^0-9]", "");
    }
    public static String removeText(String text, boolean preserveNegative){
        if(preserveNegative){
            String original = text;
            text = text.replaceAll("[^0-9]", "");
            String[] splinted = original.split(text);
            for(String w : splinted){
                if(w.endsWith("-")){
                    return "-"+text;
                }
            }
        }
        return text.replaceAll("[^0-9]", "");
    }
    public static void sendErrorMessage(String StuffBeingDone ,String error){

        MutableText clickableComponent = Text.literal("§7[§aQOL§7]  §4An error occured while "+StuffBeingDone+" click this message to copy the error").styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,ChatUtils.removeFormatting( error))).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("§bClick to copy"))));
        chat(clickableComponent);
    }

    public static String keepText(String text){return text.replaceAll("[^a-zA-Z]", "");}
}
