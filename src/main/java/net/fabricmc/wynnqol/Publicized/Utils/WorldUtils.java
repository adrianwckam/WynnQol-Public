package net.fabricmc.wynnqol.Publicized.Utils;

import net.minecraft.client.network.PlayerListEntry;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Collection;
import java.util.List;

import static net.fabricmc.wynnqol.Publicized.main.mc;


public class WorldUtils {

    public static List<String> getTabNameList(){
        List<String> names = new ArrayList<>();
        Collection<PlayerListEntry> players = mc.player.networkHandler.getPlayerList();
        for (PlayerListEntry player : players) {
            names.add(mc.inGameHud.getPlayerListHud().getPlayerName(player).getString());

        }
        return names;
    }


    public static class WynnCraft{
        private static final Pattern WorldPattern = Pattern.compile("Global \\[WC(.*)\\]", Pattern.CASE_INSENSITIVE);
        public static String GetCurrentWorld(){
            List<String> names = getTabNameList();
            for(String e: names){
                Matcher match = WorldPattern.matcher(ChatUtils.removeFormatting(e)); // match.group cannot be called without match.find
                if(match.find()) return (match.group(1));
            }
            return null;
        }

        public static boolean isInWorld(){
            return WynnCraft.GetCurrentWorld() != null;
        }
    }
}
