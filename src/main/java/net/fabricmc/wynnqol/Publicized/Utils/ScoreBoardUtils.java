package net.fabricmc.wynnqol.Publicized.Utils;

import static net.fabricmc.wynnqol.Publicized.main.mc;

public class ScoreBoardUtils {
    public static void getScoreboard(){
        System.out.println(mc.player.networkHandler.getWorld().getScoreboard().toString());
    }
}
