package net.fabricmc.wynnqol.Publicized.Utils;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.render.OutlineVertexConsumerProvider;
import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class OutlineUtil {
    public static List<Outline> OutlineList = new ArrayList<>();
    public static class Outline{
        private final int entityId;
        private final int red;
        private final int green;
        private final int blue;
        private final int alpha;
        public boolean destroyed = false;
        private boolean shouldRender = true;
        public Outline(int entityId, int red, int green, int blue, int alpha){
            this.entityId = entityId;
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
        }

        public void destroy(){
            this.destroyed = true;
        }
        public void setShouldRender(boolean shouldRender){
            this.shouldRender = shouldRender;
        }
        public boolean idIsEqual(int id ){
            return id == this.entityId;
        }

        public boolean render(OutlineVertexConsumerProvider outlineVertexConsumers, Entity entity){
            if(entity.getId() != this.entityId  || !this.shouldRender) return false;
            outlineVertexConsumers.setColor(this.red,this.green,this.blue, this.alpha);
//            if (entity.getType() == EntityType.PLAYER) { // doesnt override player team outline
//                PlayerEntity player = (PlayerEntity) entity;
//                AbstractTeam team = player.getScoreboardTeam();
//                if (team != null && team.getColor().getColorValue() != null) {
//                    int hexColor = team.getColor().getColorValue();
//                    int blue = hexColor % 256;
//                    int green = (hexColor / 256) % 256;
//                    int red = (hexColor / 65536) % 256;
//                    outlineVertexConsumers.setColor(red, green, blue, 255);
//                }
//            }
            return true;
        }
    }

    public static void renderOutlines(OutlineVertexConsumerProvider outlineVertexConsumers, Entity entity){
        for(Outline outline : OutlineList){
            if ( outline.render(outlineVertexConsumers, entity) )return;
        }
    }

    public static void addOutline(Outline outline){
        OutlineList.add(outline);
    }


    public static void destroyById(int id){
        OutlineList.removeIf(n -> (n.entityId == id));
    }

    private static void tickUpdateOutlines(){
            OutlineList.removeIf(n -> (n.destroyed));
    }

    public static void init(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            tickUpdateOutlines();
        });
    }
}

