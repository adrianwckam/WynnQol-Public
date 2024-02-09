package net.fabricmc.wynnqol.Publicized.Utils;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.render.*;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

import static net.fabricmc.wynnqol.Publicized.main.mc;

public class RenderUtils {
    public static class ui{
        public static class RectangleButton{
            public String text;
            public int x;
            public int y;
            public int color;
            private final Runnable methodOnClicked;
            public RectangleButton(String text, int x, int y, int color, Runnable MethodOnClicked){
                this.text =text;
                this.x = x;
                this.y = y;
                this.color = color;
                this.methodOnClicked = MethodOnClicked;
            }
            public boolean isInBox(int dx, int dy){
                int textWidth = mc.textRenderer.getWidth(text);
                int textHeight = mc.textRenderer.fontHeight;
                return (dx >= x && dx <=(x + textWidth + 6)  && dy>= y && dy<= (y + textHeight + 6));

            }
            public void click(int dx, int dy){
                if(isInBox(dx, dy)){
                    this.methodOnClicked.run();
                }
            }
            public void draw(DrawContext drawContext){
                int textWidth = mc.textRenderer.getWidth(text);
                int textHeight = mc.textRenderer.fontHeight;
                drawContext.fill( x,y , x + textWidth + 6, y + textHeight + 6, 0xCC000000);
                drawContext.drawText(mc.textRenderer, text,x +3, y+3,color, false );
            }

        }
    }
public static class screen{
        public static int getWidth(boolean half){
            if(half) return mc.getWindow().getScaledWidth()/2;
            return mc.getWindow().getScaledWidth();
        }

        public static int getHeight(boolean half){
            if(half) return mc.getWindow().getScaledHeight()/2;
            return mc.getWindow().getScaledHeight();
        }
    }
    public static void drawBox(WorldRenderContext context, BlockPos blockPos,float red, float green, float blue, float alpha){
        Vec3d vec3d = context.camera().getPos().negate();
        Box box = new Box(blockPos).offset(vec3d);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        DebugRenderer.drawBox(context.matrixStack(), context.consumers(),box, red, green, blue, alpha);
    }

    public static void drawBox(WorldRenderContext context, Box box,float red, float green, float blue, float alpha){
        Vec3d vec3d = context.camera().getPos().negate();
        box = box.offset(vec3d);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        DebugRenderer.drawBox(context.matrixStack(), context.consumers(),box, red, green, blue, alpha);
    }

    public static void drawSphereWithParticle(float midX,float midY,float midZ,float radius,int pointsDensity, ParticleEffect particleEffect){
        // cords here
//        int midX = 50;
//        int midY = 50;
//        int midZ = 50;
//// radius here
//        int radius = 5;
//// how many points should be used per 360 degrees
//        int pointsDensity = 360; // 0 is not allowed, division by 0

// loop through degrees of xz plane (like longitude)
        for (int xRot = 0; xRot < 180; xRot += 360/pointsDensity) {
            // loop through degrees of y plane (like latitude)
            for (int yRot = 0; yRot < 360; yRot += 360/pointsDensity) {
                // turn xRot and yRot into radians so they can be used with sine and cosine.
                double xRotRad = Math.toRadians(xRot);
                double yRotRad = Math.toRadians(yRot);
                // intialize final x, y and z variables
                double x = midX; // if something like toDouble is neccessary you do.
                double y = midY;
                double z = midZ;
                // calculate y offset.
                double yDiff = Math.sin(yRotRad) * radius;
                // calculate x and z offsets.
                double smallRadius = Math.cos(yRotRad) * radius;
                double xDiff = Math.sin(xRotRad) * smallRadius;
                double zDiff = Math.cos(xRotRad) * smallRadius;
                // add the offsets
                x += xDiff;
                y += yDiff;
                z += zDiff;
                // render the point at x, y, z
                double finalX = x;
                double finalY = y;
                double finalZ = z;
                mc.execute(() -> {
                    assert mc.player != null;
                    mc.player.getWorld().addParticle(particleEffect, finalX, finalY, finalZ, 0, 0, 0);
                });
            }
        }
    }

    public static void drawString(WorldRenderContext ctx, String string,BlockPos pos, float size) {
        drawString(ctx, string, pos.getX(), pos.getY(), pos.getZ(), size);
    }
    public static void drawString(WorldRenderContext ctx, String string,BlockPos pos, float size,int backgroundColor) {
        drawString(ctx, string, pos.getX(), pos.getY(), pos.getZ(), size,backgroundColor);
    }

    public static void drawString(WorldRenderContext ctx, String string, double x, double y, double z, float size){
        drawString(ctx, string, x, y,z, size, 0);
    }
    public static void drawString(WorldRenderContext ctx, String string, double x, double y, double z, float size,int backgroundColor) {
        MatrixStack matrices = ctx.matrixStack();
        Camera camera = ctx.camera();
        VertexConsumerProvider vertexConsumerProvider = ctx.consumers();
        if (camera == null || vertexConsumerProvider == null || mc.player == null) return;
        if (camera.isReady() && mc.getEntityRenderDispatcher().gameOptions != null) {

            TextRenderer textRenderer = mc.textRenderer;
            double titleDeltaX = x - camera.getPos().x;
            double titleDeltaY = y - camera.getPos().y;
            double titleDeltaZ = z - camera.getPos().z;


            matrices.push();
            matrices.translate(titleDeltaX, titleDeltaY, titleDeltaZ);
            matrices.multiply(camera.getRotation());
            matrices.scale(-size, -size, size);
            Matrix4f matrix4f = matrices.peek().getPositionMatrix();
            float titleX = (float) (-textRenderer.getWidth(string) / 2);
            textRenderer.draw(string, titleX, (float) (-textRenderer.fontHeight / 2), 0xffffff, false, matrix4f, ctx.consumers(), TextRenderer.TextLayerType.SEE_THROUGH, backgroundColor, 255);
            matrices.pop();
        }
    }
    public static void drawStringOnSlot(String string,MatrixStack matrixStack,Slot slot, int quadrant, float scaling){
        drawStringOnSlot(string, matrixStack, slot.x, slot.y, quadrant, scaling);
    }
    public static void drawStringOnSlot(String string,MatrixStack matrixStack,Slot slot, int quadrant){
        drawStringOnSlot(string, matrixStack, slot.x, slot.y, quadrant , 1f);
    }


    public static void drawStringOnSlot(String string,MatrixStack matrixStack, int x, int y, int quadrant, float scaling){
        OrderedText text = Text.literal(string).asOrderedText();
        Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
        matrixStack.translate(0.0F, 0.0F, 200.0F);
//        float x = slot.x;
//        float y = slot.y;
        VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
        int slotDimension = 18;
        if (quadrant == 1){
            matrix4f.translate(x + slotDimension,y,0);
        } else if (quadrant == 2) {
            matrix4f.translate(x,y,0);
        }else if (quadrant == 3){
            matrix4f.translate(x,y - slotDimension,0);
        }else if(quadrant == 4){
            matrix4f.translate(x + slotDimension,y - slotDimension,0);
        }

        matrix4f.scale(scaling, scaling, scaling);
        mc.textRenderer.drawWithOutline(text, 0, 0,0xffffff ,  0x000000, matrix4f,immediate,255);

        immediate.draw();

    }
}
