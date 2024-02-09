package net.fabricmc.wynnqol.Publicized.Mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Screen.class)
public interface ScreenAccessor {
    //NOTE this method is currently broken due to a bug to mixin

//    @Invoker("addDrawableChild")
//    <T extends Element & Drawable & Selectable> T callAddDrawableChild(T drawableElement);
//
//    @Invoker("addDrawable")
//    <T extends Drawable> T invokeAddDrawable(T drawable);

    @Accessor
    Text getTitle();

}
