package io.github.etrayed.betrayedlib.inventoryapi.anvil.factory;

import com.google.common.collect.Maps;

import io.github.etrayed.betrayedlib.inventoryapi.anvil.AnvilWindow;
import io.github.etrayed.betrayedlib.inventoryapi.anvil.util.AnvilWindowClickDispatcher;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Etrayed
 */
public class AnvilWindowFactory {

    private static final Map<Player, AnvilWindow> PLAYER_ANVIL_WINDOWS = Maps.newConcurrentMap();

    public static AnvilWindow newAnvilWindow(final Worker worker) {
        final AnvilWindow anvilWindow
                = new AnvilWindowImpl(worker.player, worker.onAnvilWindowClick, worker.onAnvilWindowClose);

        PLAYER_ANVIL_WINDOWS.remove(worker.player);
        PLAYER_ANVIL_WINDOWS.put(worker.player, anvilWindow);

        return anvilWindow;
    }

    public static AnvilWindow getAnvilWindowOf(final Player player) {
        return PLAYER_ANVIL_WINDOWS.get(player);
    }

    public static final class Worker {

        private final Player player;

        private AnvilWindowClickDispatcher onAnvilWindowClick;

        private Consumer<AnvilWindow> onAnvilWindowClose;

        public Worker(final Player player) {
            this.player = player;
        }

        public Worker onAnvilWindowClick(final AnvilWindowClickDispatcher onAnvilWindowClick) {
            this.onAnvilWindowClick = onAnvilWindowClick;

            return this;
        }

        public Worker onAnvilWindowClose(final Consumer<AnvilWindow> onAnvilWindowClose) {
            this.onAnvilWindowClose = onAnvilWindowClose;

            return this;
        }
    }
}
