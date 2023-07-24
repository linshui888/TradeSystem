package de.codingair.tradesystem.spigot.trade.gui.layout.types.impl.basic;

import de.codingair.codingapi.player.gui.inventory.v2.buttons.Button;
import de.codingair.tradesystem.spigot.trade.Trade;
import de.codingair.tradesystem.spigot.trade.gui.layout.types.TradeIcon;
import de.codingair.tradesystem.spigot.trade.gui.layout.types.feedback.FinishResult;
import de.codingair.tradesystem.spigot.trade.gui.layout.utils.Perspective;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TradeSlot implements TradeIcon {

    @Override
    public @NotNull Button getButton(@NotNull Trade trade, @NotNull Perspective perspective, @NotNull Player viewer) {
        throw new IllegalStateException("The TradeSlot.class is just a placeholder as button.");
    }

    @Override
    public @NotNull FinishResult tryFinish(@NotNull Trade trade, @NotNull Perspective perspective, @NotNull Player viewer, boolean initiationServer) {
        return FinishResult.PASS;
    }

    @Override
    public void onFinish(@NotNull Trade trade, @NotNull Perspective perspective, @NotNull Player viewer, boolean initiationServer) {
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public void serialize(@NotNull DataOutputStream out) throws IOException {
        //will be handled separately
    }

    @Override
    public void deserialize(@NotNull DataInputStream in) throws IOException {
        //will be handled separately
    }
}
