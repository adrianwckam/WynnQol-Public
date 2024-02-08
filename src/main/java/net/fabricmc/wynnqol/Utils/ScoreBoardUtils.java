package net.fabricmc.wynnqol.Utils;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.scoreboard.Scoreboard;

import static net.fabricmc.wynnqol.main.mc;

public class ScoreBoardUtils {
    public static void getScoreboard(){
        System.out.println(mc.player.networkHandler.getWorld().getScoreboard().toString());
    }
}
