package net.fabricmc.wynnqolPublicized.Features.Tna.boss;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.wynnqolPublicized.Utils.ChatUtils;
import net.fabricmc.wynnqolPublicized.Utils.EntityUtils;
import net.fabricmc.wynnqolPublicized.Utils.MathUtils;
import net.fabricmc.wynnqolPublicized.Utils.RenderUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

import static net.fabricmc.wynnqolPublicized.main.CONFIG;

public class GregBiggerHealth {
    private static int step = 0;
    public static int HealthBarId = -1;
    public static String HealthBar = "";
    private static BlockPos loc;
    private static final BlockPos EyeLocation = new BlockPos(26652,20,-22137);

    public static void init(){
        WorldRenderEvents.AFTER_ENTITIES.register(ctx -> {
            if(HealthBarId == -1 || loc == null)return;
            if(!CONFIG.tna.tnaBoss.gregBiggerHealth()) return;
            if(GregUtils.IsEyeBeaming){
                RenderUtils.drawString(ctx,HealthBar, EyeLocation, 0.2f);
            }else RenderUtils.drawString(ctx,HealthBar, loc ,0.2f);
        });
    }
    private static String FormatHealth(String str){
        try{
            StringBuilder newStr = new StringBuilder();
            String[] arr = str.split("]");
            //if(arr.length != 2) return "";
            for(String e : arr){
                if(e.contains("[")){
                    e = ChatUtils.removeFormatting(e);
                    int hpInt = Integer.parseInt(e.replaceAll("[^0-9]", ""));
                    e = MathUtils.convertToReadable(hpInt,1);
                    float percent = ((float) hpInt) /22000000;
                    if(percent > 0.66){
                        e = "§2" + e;
                    }else if(percent > 0.33){
                        e = "§6" + e;
                    }else{
                        e = "§c" + e;
                    }
                    e = e+" ";
                }
                newStr.append(e);
            }
            return newStr.toString();
        } catch (Exception e){
            return "";
        }

    }
    public static void SearchHealth(){
        if(!CONFIG.tna.tnaBoss.gregBiggerHealth()) return;
        if(!GregUtils.IsInBossRoom()) {
            HealthBarId = -1;
            loc = null;
            return;
        }
        step++;
        if(step!= 4) return;
        step = 0;
        Entity hpEntity = EntityUtils.getEntityById(HealthBarId);
        if(hpEntity!=null) {
            HealthBar = FormatHealth(hpEntity.getDisplayName().getString());
            loc = new BlockPos((int) hpEntity.getX(), (int) (hpEntity.getY()-7), (int) hpEntity.getZ());
            return;
        }
        Entity greg = GregUtils.GetGreg(false);
        if(greg==null) return;
        double gregX = greg.getX();
        double gregZ = greg.getZ();
        Iterable<Entity> entities = EntityUtils.getAllEntities();
        for(Entity e : entities){
            if(e.getX() == gregX && e.getZ() == gregZ  && e.getDisplayName() !=null && ChatUtils.removeFormatting(e.getDisplayName().getString()).contains("[")) {
                HealthBar = FormatHealth(e.getDisplayName().getString());
                HealthBarId = e.getId();
                loc = new BlockPos((int) e.getX(), (int) (e.getY()-7), (int) e.getZ());
                return;
            }
        }
    }

}
