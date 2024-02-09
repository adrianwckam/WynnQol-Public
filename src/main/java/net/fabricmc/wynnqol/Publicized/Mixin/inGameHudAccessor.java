package net.fabricmc.wynnqol.Publicized.Mixin;

import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(InGameHud.class)
public interface inGameHudAccessor {
    //@Mutable
    @Accessor
    Text getTitle();

    //@Mutable
    @Accessor
    Text getSubtitle();

    @Accessor
    Text getOverlayMessage();
}
