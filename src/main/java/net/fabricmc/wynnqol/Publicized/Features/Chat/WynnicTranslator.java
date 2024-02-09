package net.fabricmc.wynnqol.Publicized.Features.Chat;

import net.fabricmc.wynnqol.Publicized.Events.Chat.OnReceiveMessage;
import net.fabricmc.wynnqol.Publicized.Events.Chat.OnSendMessageCallBack;
import net.fabricmc.wynnqol.Publicized.Utils.ChatUtils;

import java.util.Arrays;

import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;

public class WynnicTranslator {
    static String alphabet = "⑴⑵⑶⑷⑸⑹⑺⑻⑼⑽⑾⑿⒬⒲⒠⒭⒯⒴⒰⒤⒪⒫⒜⒮⒟⒡⒢⒣⒥⒦⒧⒵⒳⒞⒱⒝⒩⒨０１２";
    static String[] alphabetkey = {"1","2","3","4","5","6","7","8","9","10","50","100","q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m",".","!","?"};
    public static String TextToWynnic(String text){
        text = text.toLowerCase();
        String result = "";
        for(int i=0; i<text.length(); i++) {
            String character = String.valueOf(text.charAt(i));
            int index = Arrays.asList(alphabetkey).indexOf(character);
            if(index != -1) {
                result += alphabet.charAt(index);
            } else {
                result += character;
            }
        }
        return result;
    }

    public static String WynnicToText(String text){
        String alphabet = "⑴⑵⑶⑷⑸⑹⑺⑻⑼⑽⑾⑿⒬⒲⒠⒭⒯⒴⒰⒤⒪⒫⒜⒮⒟⒡⒢⒣⒥⒦⒧⒵⒳⒞⒱⒝⒩⒨０１２";
        String[] alphabetkey = {"1","2","3","4","5","6","7","8","9","10","50","100","q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m",".","!","?"};
        String result = "";
        for(int i=0; i<text.length(); i++) {
            String character = String.valueOf(text.charAt(i));
            int index = alphabet.indexOf(character);
            if(index != -1) {
                result += alphabetkey[index];
            } else {
                result += character;
            }
        }
        return result;
    }

    public static boolean containsWynnic(String text){
        for (int i = 0; i < alphabet.length(); i++) {
            if (text.contains(Character.toString(alphabet.charAt(i)))) {
                return true;
            }
        }
        return false;
    }
    public static boolean speakWynnic = false;
    public static boolean translateWynnic = true;
    public static void init(){
        OnReceiveMessage.EVENT.register((message, ci) -> {
            if(translateWynnic && containsWynnic(message.getString())) ChatUtils.chatWithPrefix("Wynnic translated: &f"+WynnicToText(message.getString()));
        });
        OnSendMessageCallBack.EVENT.register((content, ci) -> {
            if(ChatChannel.OnInput || !speakWynnic || content.startsWith("/")) return;
            if(!containsWynnic(content)){
                ci.cancel();
                ChatUtils.send(TextToWynnic(content));
            }


        });
    }
}
