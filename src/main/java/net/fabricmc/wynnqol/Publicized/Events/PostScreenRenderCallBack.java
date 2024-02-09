package net.fabricmc.wynnqol.Publicized.Events;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface PostScreenRenderCallBack {
    Event<PostScreenRenderCallBack> EVENT = EventFactory.createArrayBacked(PostScreenRenderCallBack.class,
            (listeners) -> ( context,  mouseX,  mouseY,  delta,  ci) -> {
                for (PostScreenRenderCallBack listener : listeners) {
                    listener.interact(context,  mouseX,  mouseY,  delta,  ci);

                }
            });


    void interact(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci);
}

