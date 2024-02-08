package net.fabricmc.wynnqol;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.wynnqol.Features.*;
import net.fabricmc.wynnqol.Features.SkillPointPreset.PresetMenu;
import net.fabricmc.wynnqol.Features.chat.CopyChatMessage;
import net.fabricmc.wynnqol.Features.chat.ChatChannel;
import net.fabricmc.wynnqol.Features.chat.ChatTab;
import net.fabricmc.wynnqol.Features.chat.WynnicTranslator;
import net.fabricmc.wynnqol.Features.tna.LightHolderHighlight;
import net.fabricmc.wynnqol.Features.tna.boss.*;
import net.fabricmc.wynnqol.Utils.*;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;

import java.awt.*;
public class main implements ModInitializer {
	public static MinecraftClient mc = MinecraftClient.getInstance();

	public static final Logger LOGGER = LoggerFactory.getLogger("WynnQOL");
	public static final net.fabricmc.wynnqol.WynnConfig CONFIG = net.fabricmc.wynnqol.WynnConfig.createAndLoad();

	@Override
	public void onInitialize() {
		System.setProperty("java.awt.headless", "false");
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("This mod only work in fabric!, but if it doesn't work u won't see this message anyway....");


		GregUtils.init();
		SoulPointWorld.init();
		DevMode.init();
        GregBiggerHealth.init();
		ChatTab.init();
		WynnicTranslator.init();
		CopyChatMessage.init();
		ChatChannel.init();
		PresetMenu.init();
		net.fabricmc.wynnqol.Features.AbilityPointPreset.PresetMenu.init();
		Stfu.init();
		OutlineUtil.init();
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("wynnqol")
					.executes(context -> {
						ConfigUtils.openUI();

						return 0;
					}));
		});

		ClientTickEvents.END_CLIENT_TICK.register((End)->{
			if (mc.player == null || mc.world == null)return;
			EntityUtils.TickUpdate();
			GregUtils.UpdateGreg();
			GregOutline.OnTick();
			LightHolderHighlight.onTick();
		});



	}
}
