/*
MIT License

Copyright (c) 2019 Miklas

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
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
