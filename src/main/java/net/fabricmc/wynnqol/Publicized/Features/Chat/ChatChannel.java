package net.fabricmc.wynnqol.Publicized.Features.Chat;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.wynnqol.Publicized.Events.Chat.OnReceiveMessage;
import net.fabricmc.wynnqol.Publicized.Events.Chat.OnSendMessageCallBack;
import net.fabricmc.wynnqol.Publicized.Utils.ChatUtils;

import java.util.Objects;

import static net.fabricmc.wynnqol.Publicized.main.CONFIG;

public class ChatChannel {
    public static String Prefix = "";
    public static String Channel = "All";
    public static boolean OnInput = false;
    public static boolean HideSwitchMessage = false;
    private static String latestMessage = "";
    public static void init(){

        OnSendMessageCallBack.EVENT.register((content, ci) ->{

            if(ci.isCancelled()) return;
            if(OnInput){
                OnInput = false;
                return;
            }
            if(Objects.equals(ChatChannel.Prefix, "")) return;
            if(content.startsWith("/")) return;
            ci.cancel();
            latestMessage = content;
            ChatUtils.sendCommand(ChatChannel.Prefix + content);
            ChatUtils.addToSentMessageHistory(content);
        });
        OnReceiveMessage.EVENT.register((message, ci) -> {
            //Type the amount you wish to sell or type 'cancel' to cancel:
            if(OnInput &&!Objects.equals(Channel, "All") && message.getString().contains("You can't use commands right now!")){
                ChatUtils.send(latestMessage);
            }
            if(!Objects.equals(Channel, "All") &&message.getString().contains(" or type 'cancel' to cancel:")){
                OnInput = true;
            }else if(!Objects.equals(Channel, "All") &&message.getString().contains("chat input was canceled.")){
                OnInput = false;
            }
        });
        ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
            dispatcher.register((ClientCommandManager.literal("chat")
                .executes(context -> {
                    if(!HideSwitchMessage)ChatUtils.chatWithPrefix("You are now in channel &6"+Channel);
                    return 1;
                }).then(ClientCommandManager.literal("all").executes(context -> {
                    if(!HideSwitchMessage)ChatUtils.chatWithPrefix("You are now in channel &6All");
                    HideSwitchMessage = false;
                    Prefix = "";
                    Channel = "All";
                    return 1;
                })).then(ClientCommandManager.literal("party").executes(context -> {
                    if(!HideSwitchMessage)ChatUtils.chatWithPrefix("You are now in channel &6Party");
                    HideSwitchMessage = false;
                    Prefix = "p ";
                    Channel = "Party";
                    return 1;
                })).then(ClientCommandManager.literal("guild").executes(context -> {
                    if(!HideSwitchMessage)ChatUtils.chatWithPrefix("You are now in channel &6Guild");
                    HideSwitchMessage = false;
                    Prefix = "g ";
                    Channel = "Guild";
                    return 1;
                })).then(ClientCommandManager.literal("a").executes(context -> {
                    if(!HideSwitchMessage)ChatUtils.chatWithPrefix("You are now in channel &6All");
                    HideSwitchMessage = false;
                    Prefix = "";
                    Channel = "All";
                    return 1;
                })).then(ClientCommandManager.literal("p").executes(context -> {
                    if(!HideSwitchMessage)ChatUtils.chatWithPrefix("You are now in channel &6Party");
                    HideSwitchMessage = false;
                    Prefix = "p ";
                    Channel = "Party";
                    return 1;
                })).then(ClientCommandManager.literal("g").executes(context -> {
                    if(!HideSwitchMessage)ChatUtils.chatWithPrefix("You are now in channel &6Guild");
                    HideSwitchMessage = false;
                    Prefix = "g ";
                    Channel = "Guild";
                    return 1;
                })).then(ClientCommandManager.literal("reply").executes(context -> {
                    if(!HideSwitchMessage)ChatUtils.chatWithPrefix("You are now in channel &6Reply");
                    HideSwitchMessage = false;
                    Prefix = "r ";
                    Channel = "Reply";
                    return 1;
                })).then(ClientCommandManager.literal("r").executes(context -> {
                    if(!HideSwitchMessage)ChatUtils.chatWithPrefix("You are now in channel &6Reply");
                    HideSwitchMessage = false;
                    Prefix = "r ";
                    Channel = "Reply";
                    return 1;
                }))
        ));

        }));
    }
}
