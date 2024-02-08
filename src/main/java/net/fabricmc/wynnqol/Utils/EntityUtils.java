package net.fabricmc.wynnqol.Utils;


import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class EntityUtils {
    private static Iterable<Entity> EntityList = new ArrayList<>();
    private static List<PlayerEntity> PlayerList = new ArrayList<>();
    private static MinecraftClient mc = MinecraftClient.getInstance();
    public static void TickUpdate(){
        EntityList = mc.world.getEntities();
        PlayerList = (List<PlayerEntity>) mc.player.getWorld().getPlayers();
    }

    public static Iterable<Entity> getAllEntities(){
        return EntityList;
    }
    public static List<PlayerEntity> getPlayerList(){
        return PlayerList;
    }

    public static Entity getEntityById(int id){
        return mc.world.getEntityById(id);
    }
}
