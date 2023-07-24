package de.codingair.tradesystem.spigot.events;

import de.codingair.tradesystem.spigot.events.utils.TradeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Called when a request expired.
 */
public class TradeRequestExpireEvent extends TradeEvent {
    private static final HandlerList handlerList = new HandlerList();
    private final String sender;
    private final UUID senderId;
    private final Player sendingPlayer;
    private final String receiver;
    private final UUID receiverId;
    private final Player receivingPlayer;

    /**
     * @param sender          The name of the player who sends the request.
     * @param senderId        The {@link UUID} of the player who sends the request.
     * @param sendingPlayer   The {@link Player} who sends the request.
     * @param receiver        The name of the player who receives the request.
     * @param receiverId      The {@link UUID} of the player who receives the request.
     * @param receivingPlayer The {@link Player} who receives the request.
     */
    public TradeRequestExpireEvent(@NotNull String sender, @NotNull UUID senderId, @Nullable Player sendingPlayer, @NotNull String receiver, @NotNull UUID receiverId, @Nullable Player receivingPlayer) {
        this.sender = sender;
        this.senderId = senderId;
        this.sendingPlayer = sendingPlayer;
        this.receiver = receiver;
        this.receiverId = receiverId;
        this.receivingPlayer = receivingPlayer;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return getHandlerList();
    }

    /**
     * @return The name of the player who sends the request.
     */
    @NotNull
    public String getSender() {
        return this.sender;
    }

    /**
     * @return The {@link UUID} of the player who sends the request.
     */
    @NotNull
    public UUID getSenderId() {
        return senderId;
    }

    /**
     * @return The {@link Player} who sends the request. Is null if this is a proxy trade and the sender is on another server.
     */
    @Nullable
    public Player getSendingPlayer() {
        return this.sendingPlayer;
    }

    /**
     * @return The name of the player who receives the request.
     */
    @NotNull
    public String getReceiver() {
        return this.receiver;
    }

    /**
     * @return The {@link UUID} of the player who receives the request.
     */
    @NotNull
    public UUID getReceiverId() {
        return receiverId;
    }

    /**
     * @return The {@link Player} who receives the request. Is null if this is a proxy trade and the receiver is on another server.
     */
    @Nullable
    public Player getReceivingPlayer() {
        return this.receivingPlayer;
    }

    /**
     * @return {@link Boolean#TRUE} if one of both traders is on another server.
     */
    public boolean isProxyTrade() {
        return sendingPlayer == null || receivingPlayer == null;
    }
}
