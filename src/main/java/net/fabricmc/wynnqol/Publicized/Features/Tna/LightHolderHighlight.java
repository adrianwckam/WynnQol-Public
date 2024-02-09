package net.fabricmc.wynnqol.Publicized.Features.Tna;

import net.fabricmc.wynnqol.Publicized.Utils.ChatUtils;
import net.fabricmc.wynnqol.Publicized.Utils.EntityUtils;
import net.fabricmc.wynnqol.Publicized.Utils.OutlineUtil;
import net.fabricmc.wynnqol.Publicized.Utils.TextUtils;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

import static net.fabricmc.wynnqol.Publicized.main.CONFIG;
import static net.fabricmc.wynnqol.Publicized.main.mc;

public class LightHolderHighlight {
    //The Void has been opened, you may leave the cave
    private static final int pedalX = -12743;
    private static final int pedalZ = 8380;

    private static final int firstRoomX = -12804;
    private static final int firstRoomZ = 8353;

    private static final int secondRoomX = -12853;
    private static final int secondRoomZ = 8379;

    private static final int thirdRoomX = -12901;
    private static final int thirdRoomZ = 8373;
    private static int lightHolderId = -1;
    public static void onTick(){
        if(!CONFIG.tna.highlightLightHolder()) return;
        if(lightHolderId != -1 && !isInLanternRoom()){
            OutlineUtil.destroyById(lightHolderId);
            lightHolderId = -1;
            return;
        }
        if(Math.abs(mc.player.getX() - pedalX) > 40 || Math.abs(mc.player.getZ() - pedalZ) > 40 ) return;
        List<PlayerEntity> playerList = EntityUtils.getPlayerList();
        for (PlayerEntity player: playerList){
            // found lantern holder
            if(player.getScoreboardTeam() == null || player.getScoreboardTeam().getColor() ==null || player.getName().getString().startsWith("[")) continue;
            if(Math.floor(player.getX()) != pedalX || Math.floor(player.getZ()) != pedalZ) continue;
            int id = player.getId();
            if (lightHolderId == id) continue;
            ChatUtils.chatWithPrefix("Lantern holder found:"+ TextUtils.getCoded(player.getDisplayName()));
            OutlineUtil.destroyById(lightHolderId);
            lightHolderId = id;
            OutlineUtil.addOutline(new OutlineUtil.Outline(lightHolderId,0,255,255, 255));
            break;
        }


    }

    private static boolean isInLanternRoom(){
        return ((Math.abs(mc.player.getX() - pedalX) <= 40 && Math.abs(mc.player.getZ() - pedalZ) <= 40 )
         || (Math.abs(mc.player.getX() - firstRoomX) <= 60 && Math.abs(mc.player.getZ() - firstRoomZ) <= 60 )
        || (Math.abs(mc.player.getX() - secondRoomX) <= 60 && Math.abs(mc.player.getZ() - secondRoomZ) <= 60 )
        || (Math.abs(mc.player.getX() - thirdRoomX) <= 60 && Math.abs(mc.player.getZ() - thirdRoomZ) <= 60 ));
    }
}
