package net.fabricmc.wynnqolPublicized.Mixin;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

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
    @Accessor("width")
    int getWidth();

    @Accessor("height")
    int getHeight();

}
