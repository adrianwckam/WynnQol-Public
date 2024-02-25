package net.fabricmc.wynnqolPublicized;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.wynnqolPublicized.Commands.Command;
import net.fabricmc.wynnqolPublicized.Features.SkillPointPreset.PresetMenu;
import net.fabricmc.wynnqolPublicized.Features.Chat.CopyChatMessage;
import net.fabricmc.wynnqolPublicized.Features.Chat.ChatChannel;
import net.fabricmc.wynnqolPublicized.Features.Chat.ChatTab;
import net.fabricmc.wynnqolPublicized.Features.Chat.WynnicTranslator;
import net.fabricmc.wynnqolPublicized.Features.Tna.LightHolderHighlight;
import net.fabricmc.wynnqolPublicized.Features.Stfu;
import net.fabricmc.wynnqolPublicized.Features.Tna.boss.GregBiggerHealth;
import net.fabricmc.wynnqolPublicized.Features.Tna.boss.GregOutline;
import net.fabricmc.wynnqolPublicized.Features.Tna.boss.GregUtils;
import net.fabricmc.wynnqolPublicized.Utils.DevMode;
import net.fabricmc.wynnqolPublicized.Utils.EntityUtils;
import net.fabricmc.wynnqolPublicized.Utils.OutlineUtil;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class main implements ModInitializer {
	public static MinecraftClient mc = MinecraftClient.getInstance();

	public static final Logger LOGGER = LoggerFactory.getLogger("WynnQOL");
	public static final net.fabricmc.wynnqolPublicized.WynnQolPublicConfig CONFIG = net.fabricmc.wynnqolPublicized.WynnQolPublicConfig.createAndLoad();

	@Override
	public void onInitialize() {
		System.setProperty("java.awt.headless", "false");
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("This mod only work in fabric!, but if it doesn't work u won't see this message anyway....");

		Command.registerCommand();
		GregUtils.init();
		DevMode.init();
        GregBiggerHealth.init();
		ChatTab.init();
		WynnicTranslator.init();
		CopyChatMessage.init();
		ChatChannel.init();
		PresetMenu.init();
		net.fabricmc.wynnqolPublicized.Features.AbilityPointPreset.PresetMenu.init();
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
