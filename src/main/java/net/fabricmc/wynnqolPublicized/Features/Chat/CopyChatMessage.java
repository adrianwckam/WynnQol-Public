package net.fabricmc.wynnqolPublicized.Features.Chat;

import net.fabricmc.wynnqolPublicized.Events.Chat.OnReceiveMessage;
import net.fabricmc.wynnqolPublicized.Utils.ChatUtils;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import static net.fabricmc.wynnqolPublicized.main.CONFIG;

public class CopyChatMessage {
    public static void init(){
        OnReceiveMessage.EVENT.register((message, ci) -> {
            if(!CONFIG.chat.copyMessage()) return;
            MutableText clickableComponent = Text.literal(" §b◀").styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,ChatUtils.removeFormatting( message.getString()))).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("§bClick to copy"))));
            message.getSiblings().add(clickableComponent);
        });
    }
}
