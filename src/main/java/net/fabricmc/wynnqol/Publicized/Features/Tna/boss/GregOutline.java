package net.fabricmc.wynnqol.Publicized.Features.Tna.boss;

import net.fabricmc.wynnqol.Publicized.Utils.EntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static net.fabricmc.wynnqol.Publicized.main.mc;

public class GregOutline {
    public static List<Integer> modelIdList = new ArrayList<Integer>();
    private static int tickCount = -1;

    public static void OnGregSpawn(){
        tickCount = 0;
    }
    private static void findGregModel(){
        if(tickCount == -1) return;
        tickCount++;
        if(tickCount > 100 && GregUtils.GetGreg(false) != null){
            Entity greg = GregUtils.GetGreg(false);
            for(Entity e : mc.world.getEntities()){
                if(e.getType() == EntityType.ARMOR_STAND && e.getY()>20 &&( Math.sqrt(greg.squaredDistanceTo(e.getX(),greg.getY(), e.getZ()))<5)){
                    modelIdList.add(e.getId());
                    tickCount = -1;
                }
            }
        }
    }
    private static void CheckIsGregModel(){

        if(GregUtils.IsEyeBeaming) return;
        Entity greg = GregUtils.GetGreg(false);
        if(greg==null || greg.getY()<20) return;
        Predicate<Integer> pr= a->(EntityUtils.getEntityById(a) != null &&Math.sqrt(EntityUtils.getEntityById(a).squaredDistanceTo(greg.getX(), EntityUtils.getEntityById(a).getY(), greg.getZ())) > 6);
        modelIdList.removeIf(pr);
    }
    public static void OnTick(){
        if(!GregUtils.IsInBossRoom()) return;
        findGregModel();
        if (modelIdList.size() > 4){
            CheckIsGregModel();
        }
    }

    public static void reset(){
        modelIdList.clear();
        tickCount = -1;
    }
}
