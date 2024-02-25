package net.fabricmc.wynnqolPublicized.Utils;

import static net.fabricmc.wynnqolPublicized.main.mc;

public class ScoreBoardUtils {
    public static void getScoreboard(){
        System.out.println(mc.player.networkHandler.getWorld().getScoreboard().toString());
    }
}
