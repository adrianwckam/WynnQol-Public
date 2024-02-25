package net.fabricmc.wynnqolPublicized.Commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.wynnqolPublicized.Features.Stfu;
import net.fabricmc.wynnqolPublicized.Utils.ConfigUtils;
import net.fabricmc.wynnqolPublicized.Utils.DevMode;

public class Command {
        public static LiteralCommandNode<FabricClientCommandSource> mainCommand;
        public static void registerCommand() {

            ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
                mainCommand = dispatcher.register(ClientCommandManager.literal("wynnqol")
                        .then(ClientCommandManager.literal("help").executes(Command::help))
                        .then(ClientCommandManager.literal("config").executes(Command::config))
                        .then(ClientCommandManager.literal("stfu").executes(Command::stfu))
                        .then(ClientCommandManager.literal("wtfisthat").executes(Command::wtfIsThat))
                        .then(ClientCommandManager.literal("devtest").executes(Command::devTest))

                );
            });


            ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
                dispatcher.register(ClientCommandManager.literal("wq").redirect(mainCommand).executes(context -> {
                            // default
                            return 0;
                        })
                );
            });
        }


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
