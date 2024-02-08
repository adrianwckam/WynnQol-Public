package net.fabricmc.wynnqol.mixin.Outline;

import net.fabricmc.wynnqol.Features.tna.boss.GregOutline;
import net.fabricmc.wynnqol.Features.tna.boss.GregUtils;
import net.fabricmc.wynnqol.Utils.OutlineUtil;
import net.minecraft.client.render.OutlineVertexConsumerProvider;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.AbstractTeam;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class RenderEntityMixin {
    @Inject(method = "renderEntity", at = @At("HEAD"))
    private void renderEntity(Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci) {
        if (vertexConsumers instanceof OutlineVertexConsumerProvider outlineVertexConsumers) {

            if( !GregUtils.IsEyeBeaming&& GregOutline.modelIdList.contains(entity.getId())){
                outlineVertexConsumers.setColor(29,203,250, 255);
                if (entity.getType() == EntityType.PLAYER) {
                    PlayerEntity player = (PlayerEntity) entity;
                    AbstractTeam team = player.getScoreboardTeam();
                    if (team != null && team.getColor().getColorValue() != null) {
                        int hexColor = team.getColor().getColorValue();
                        int blue = hexColor % 256;
                        int green = (hexColor / 256) % 256;
                        int red = (hexColor / 65536) % 256;
                        outlineVertexConsumers.setColor(red, green, blue, 255);
                    }
                }
            }
            OutlineUtil.renderOutlines(outlineVertexConsumers,entity );

        }
    }
}
