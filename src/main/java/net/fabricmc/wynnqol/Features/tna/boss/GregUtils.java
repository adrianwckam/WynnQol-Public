package net.fabricmc.wynnqol.Features.tna.boss;


import net.fabricmc.wynnqol.Utils.ChatUtils;
import net.fabricmc.wynnqol.Utils.EntityUtils;
import net.minecraft.entity.Entity;

import static net.fabricmc.wynnqol.main.mc;

public class GregUtils {
    private static int minX = 26593 ;
    private static int minZ = -22163;
    private static int maxX = 26711 ;
    private static int maxZ = -22110;
    public static boolean FoundGreg = false;
    public static int GregId = -1;
    public static boolean IsEyeBeaming = false;
    public static long EyeBeamTimer = 0;
    private static Entity gregEntityCache;

    public static boolean IsInBossRoom(){
        return (mc.player.getX()>minX && mc.player.getX()<maxX) && (mc.player.getZ()>minZ && mc.player.getZ() < maxZ);
    }
    public static void init(){
//        HudRenderCallback.EVENT.register((drawContext, delta)->{
//
//            if(GregUtils.IsEyeBeaming){
//                drawContext.drawText(mc.textRenderer, "§d"+((Math.floor((EyeBeamTimer-System.currentTimeMillis())/100))/10)+"s"+" "+GregEyeRadiusTracker.WarningText,(mc.getWindow().getScaledWidth() /2)-mc.textRenderer.getWidth(((Math.floor((EyeBeamTimer-System.currentTimeMillis())/100))/10)+"s")/2, (mc.getWindow().getScaledHeight()/2)+mc.textRenderer.fontHeight, 0, false);
//            }
//
//
//        });
    }

    public static boolean IsInBossRoom(Entity e){
        return (e.getX()>minX && e.getX()<maxX) && (e.getZ()>minZ && e.getZ() < maxZ);
    }

    public static Entity GetGreg(boolean Refresh){
        if(!Refresh){
            Entity w = EntityUtils.getEntityById(GregId);
            if(w != null){
                gregEntityCache =w;
                return w;
            }
        }


        for(Entity e : EntityUtils.getAllEntities()){
            if(e.getCustomName()==null) continue;
            String name = ChatUtils.removeFormatting(e.getCustomName().getString());
            if(name.startsWith("The") && name.endsWith("Anomaly") ){
                GregId = e.getId();
                gregEntityCache = e;
                return e;
            }
        }
        return null;
    }

    public static Entity getGregCache(){
        return gregEntityCache;
    }

    public static boolean FindGreg(){
        for(Entity e : EntityUtils.getAllEntities()){
            if(e.getCustomName()==null) continue;
            String name = ChatUtils.removeFormatting(e.getCustomName().getString());
            if(name.startsWith("The") && name.endsWith("Anomaly") ){
                GregId = e.getId();
                FoundGreg = true;
                return true;
            }
        }
        return false;
    }


    public static boolean FindEyeBeam(){
        if(System.currentTimeMillis() > EyeBeamTimer){
            EyeBeamTimer = System.currentTimeMillis()+2000;
        }
        for(Entity e : EntityUtils.getAllEntities()){
            if(e.getCustomName()==null) continue;
            String name = ChatUtils.removeFormatting(e.getCustomName().getString());
            if(name.contains("Sightseeing Interceptor") ){
                if(!IsEyeBeaming){
                    EyeBeamTimer = System.currentTimeMillis()+2000;
                    IsEyeBeaming = true;
                    return true;
                }



                IsEyeBeaming = true;
                return true;
            }
        }
        if(IsEyeBeaming && GetGreg(false) !=null && GetGreg(false).getY()>19){
            IsEyeBeaming = false;
        }

        return false;
    }

    public static void UpdateGreg(){
        if(!IsInBossRoom()) {
            if(FoundGreg){
                GregOutline.reset();
//                ChatUtils.chatWithPrefix("Left tna boss arena");
            }
            IsEyeBeaming = false;
            FoundGreg = false;
            GregId = -1;
            return;
        }
        //IsInBossRoom
        if( !FoundGreg && FindGreg() ){
            GregOutline.OnGregSpawn();
//            ChatUtils.chatWithPrefix("Found Greg! Entity ID: "+GregId + " "+EntityUtils.getEntityById(GregId).getName().getString());
            return;
        }
        if(FoundGreg){
            FindEyeBeam();
            //FindGreg();
        }

    }
}
