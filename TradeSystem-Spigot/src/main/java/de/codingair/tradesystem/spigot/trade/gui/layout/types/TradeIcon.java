package de.codingair.tradesystem.spigot.trade.gui.layout.types;

import de.codingair.codingapi.player.gui.inventory.v2.buttons.Button;
import de.codingair.tradesystem.spigot.extras.tradelog.TradeLog;
import de.codingair.tradesystem.spigot.extras.tradelog.TradeLogService;
import de.codingair.tradesystem.spigot.trade.Trade;
import de.codingair.tradesystem.spigot.trade.gui.TradingGUI;
import de.codingair.tradesystem.spigot.trade.gui.layout.types.feedback.FinishResult;
import de.codingair.tradesystem.spigot.trade.gui.layout.utils.Perspective;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Each trade GUI get its own icon instances. This means that you can store custom variables in your own class implementation.<p>
 * Use only suggested constructors! We need them for applying ItemStacks from the active layout.
 */
public interface TradeIcon {

    /**
     * @param trade       The trade instance.
     * @param perspective The perspective that should be applied to this icon.
     * @param viewer      The player that is viewing the trade GUI. This is not necessarily the trading player.
     * @return A {@link Button} to represent this trade icon in the trade GUI.
     */
    @NotNull Button getButton(@NotNull Trade trade, @NotNull Perspective perspective, @NotNull Player viewer);

    /**
     * Executed when the trade countdown reaches zero and the goods must be exchanged.
     *
     * @param trade            The trade instance.
     * @param perspective      THe perspective that finished this icon.
     * @param viewer           The player that is viewing the trade GUI. This is not necessarily the trading player.
     * @param initiationServer Important for proxy trades since we need the correct order of the trading players or sometimes need only one server to log. If true, 'player' is the inviter.
     */
    void onFinish(@NotNull Trade trade, @NotNull Perspective perspective, @NotNull Player viewer, boolean initiationServer);

    /**
     * Checks all trade icons for the last time to ensure that all data is correct.<br>
     * Example: Has the player enough money as he wants to trade in?<p>
     * <b>Do NOT exchange any goods here!</b>
     *
     * @param trade            The trade instance.
     * @param perspective      THe perspective that tries to finish this icon.
     * @param viewer           The player that is viewing the trade GUI. This is not necessarily the trading player.
     * @param initiationServer Important for proxy trades since we need the correct order of the trading players or sometimes need only one server to log. If true, 'player' is the inviter.
     * @return A finish result. Important for messages like "items were dropped". You can ignore this by using {@link FinishResult#PASS}.
     */
    @NotNull FinishResult tryFinish(@NotNull Trade trade, @NotNull Perspective perspective, @NotNull Player viewer, boolean initiationServer);

    /**
     * Important when a player needs to trade something before clicking ready.
     *
     * @return {@link Boolean#TRUE} if this icon has no data to share except of default data.
     */
    boolean isEmpty();

    /**
     * Used for proxy trading. It will serialize the data and send it to the server of the trade partner.
     *
     * @param out The {@link java.io.DataOutputStream} which collects custom data.
     */
    void serialize(@NotNull DataOutputStream out) throws IOException;

    /**
     * Used for proxy trading. It will deserialize the data which was sent by the server of the trade partner.<p>
     * If you want to update this data on another {@link TradeIcon}, then use a {@link Transition}.
     *
     * @param in The {@link java.io.DataInputStream} which will be used to restore custom data.
     */
    void deserialize(@NotNull DataInputStream in) throws IOException;

    /**
     * Refreshes the {@link ItemStack} of this {@link TradeIcon}.
     *
     * <p><strong>Overriding this method may break the trade functionality.</strong></p>
     *
     * @param trade       The trade instance.
     * @param perspective The perspective that should be applied to this icon.
     */
    default void updateItem(@NotNull Trade trade, @NotNull Perspective perspective) {
        int slot = trade.getLayout()[perspective.id()].getSlotOf(this);

        TradingGUI gui = trade.getGUIs()[perspective.id()];
        Button button = gui.getActive().getButtonAt(slot);
        gui.setItem(slot, button.buildItem());
    }

    /**
     * Refreshes the {@link Button} and its {@link ItemStack} of this {@link TradeIcon}.
     *
     * <p><strong>Overriding this method may break the trade functionality.</strong></p>
     *
     * @param trade  The trade instance.
     * @param player The trading player.
     */
    default void updateButton(@NotNull Trade trade, @NotNull Player player) {
        Perspective perspective = trade.getPerspective(player);
        int slot = trade.getLayout()[perspective.id()].getSlotOf(this);

        TradingGUI gui = trade.getGUIs()[perspective.id()];
        Button button = getButton(trade, perspective, player);
        gui.getActive().addButton(slot, button);
        gui.setItem(slot, button.buildItem());
    }

    /**
     * <p><strong>Overriding this method may break the log functionality.</strong></p>
     *
     * @param trade   The current trade instance.
     * @param message The message to log.
     * @param vars    Data to fill 'message'.
     */
    default void log(@NotNull Trade trade, @NotNull TradeLog.Message message, Object... vars) {
        if (trade.isInitiationServer())
            TradeLogService.log(trade.getNames()[0], trade.getNames()[1], message.get(vars));
        else {
            //exception -> proxy trade -> handle exchange only on one server -> switch players
            TradeLogService.log(trade.getNames()[1], trade.getNames()[0], message.get(vars));
        }
    }
}
