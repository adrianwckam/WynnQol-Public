package net.fabricmc.wynnqol.mixin;

import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.client.MinecraftClient;

import javax.annotation.Nullable;

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
