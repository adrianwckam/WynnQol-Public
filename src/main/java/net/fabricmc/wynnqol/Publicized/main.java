package net.fabricmc.wynnqol.Publicized;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.wynnqol.Publicized.Commands.Command;
import net.fabricmc.wynnqol.Publicized.Features.*;
import net.fabricmc.wynnqol.Publicized.Features.SkillPointPreset.PresetMenu;
import net.fabricmc.wynnqol.Publicized.Features.Chat.CopyChatMessage;
import net.fabricmc.wynnqol.Publicized.Features.Chat.ChatChannel;
import net.fabricmc.wynnqol.Publicized.Features.Chat.ChatTab;
import net.fabricmc.wynnqol.Publicized.Features.Chat.WynnicTranslator;
import net.fabricmc.wynnqol.Publicized.Features.Tna.LightHolderHighlight;
import net.fabricmc.wynnqol.Publicized.Features.Tna.boss.*;
import net.fabricmc.wynnqol.Publicized.Utils.*;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class main implements ModInitializer {
	public static MinecraftClient mc = MinecraftClient.getInstance();

	public static final Logger LOGGER = LoggerFactory.getLogger("WynnQOL");
	public static final net.fabricmc.wynnqol.Publicized.WynnQolPublicConfig CONFIG = net.fabricmc.wynnqol.Publicized.WynnQolPublicConfig.createAndLoad();

	@Override
	public void onInitialize() {
		System.setProperty("java.awt.headless", "false");
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("This mod only work in fabric!, but if it doesn't work u won't see this message anyway....");

		ClientCommandRegistrationCallback.EVENT.register(Command::register);
		GregUtils.init();
		DevMode.init();
        GregBiggerHealth.init();
		ChatTab.init();
		WynnicTranslator.init();
		CopyChatMessage.init();
		ChatChannel.init();
		PresetMenu.init();
		net.fabricmc.wynnqol.Publicized.Features.AbilityPointPreset.PresetMenu.init();
		Stfu.init();
		OutlineUtil.init();

		ClientTickEvents.END_CLIENT_TICK.register((End)->{
			if (mc.player == null || mc.world == null)return;
			EntityUtils.TickUpdate();
			GregBiggerHealth.SearchHealth();
			GregUtils.UpdateGreg();
			GregOutline.OnTick();
			LightHolderHighlight.onTick();
		});



	}
}
