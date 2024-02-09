package net.fabricmc.wynnqol.Publicized.Commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.wynnqol.Publicized.Features.Stfu;
import net.fabricmc.wynnqol.Publicized.Utils.ConfigUtils;
import net.fabricmc.wynnqol.Publicized.Utils.DevMode;
import net.minecraft.command.CommandRegistryAccess;

public class Command {

        public static void register(CommandDispatcher<FabricClientCommandSource> fabricClientCommandSourceCommandDispatcher, CommandRegistryAccess commandRegistryAccess) {
            fabricClientCommandSourceCommandDispatcher.register(ClientCommandManager.literal("wynnqol")
                    .then(ClientCommandManager.literal("help").executes(Command::help))
                    .then(ClientCommandManager.literal("config").executes(Command::config))
                    .then(ClientCommandManager.literal("stfu").executes(Command::stfu))
                    .then(ClientCommandManager.literal("wtfisthat").executes(Command::wtfIsThat))
                    .then(ClientCommandManager.literal("devtest").executes(Command::devTest))

            );}


        private static int help(CommandContext<FabricClientCommandSource> context) {
            return 1;
        }

        private static int config(CommandContext<FabricClientCommandSource> context) {
            ConfigUtils.openUI();
            return 1;
        }

        private static int stfu(CommandContext<FabricClientCommandSource> context) {
            return Stfu.onCommand();
        }

        private static int wtfIsThat(CommandContext<FabricClientCommandSource> context) {
            DevMode.onCommandWtfIsThat();
            return 1;
        }

        private static int devTest(CommandContext<FabricClientCommandSource> context) {
            DevMode.onCommandDevTest();
            return 1;
        }

}
