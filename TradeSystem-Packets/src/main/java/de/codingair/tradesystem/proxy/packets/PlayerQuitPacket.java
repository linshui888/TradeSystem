package de.codingair.tradesystem.proxy.packets;

import de.codingair.packetmanagement.packets.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerQuitPacket implements Packet {
    private String player;

    public PlayerQuitPacket() {
    }

    public PlayerQuitPacket(String player) {
        this.player = player;
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeUTF(this.player);
    }

    @Override
    public void read(DataInputStream in) throws IOException {
        this.player = in.readUTF();
    }

    public String getPlayer() {
        return player;
    }
}
