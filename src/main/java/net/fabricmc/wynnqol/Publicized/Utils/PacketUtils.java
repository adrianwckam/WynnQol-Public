package net.fabricmc.wynnqol.Publicized.Utils;

import net.minecraft.client.network.SequencedPacketCreator;
import net.minecraft.network.packet.Packet;

import java.util.Objects;

import static net.fabricmc.wynnqol.Publicized.main.mc;

public class PacketUtils {
    public static void sendPacket(Packet<?> packet) {
        Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(packet);
    }

    public static void sendSequencedPacket(SequencedPacketCreator sequencedPacketCreator){
        assert mc.interactionManager != null;
        assert mc.world != null;
        mc.interactionManager.sendSequencedPacket(mc.world, sequencedPacketCreator);
    }
}
