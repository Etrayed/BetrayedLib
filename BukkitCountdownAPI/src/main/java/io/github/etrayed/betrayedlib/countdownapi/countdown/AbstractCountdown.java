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
package io.github.etrayed.betrayedlib.countdownapi.countdown;

import io.github.etrayed.betrayedlib.countdownapi.event.CountdownSpedUpEvent;
import io.github.etrayed.betrayedlib.countdownapi.event.CountdownStartedEvent;
import io.github.etrayed.betrayedlib.countdownapi.event.CountdownStoppedEvent;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

/**
 * @author Etrayed
 */
public abstract class AbstractCountdown implements Countdown {

    private final Consumer<Long> runAction;

    private final long speedUpTimestamp;

    private long seconds;

    private boolean active = false;

    final Plugin plugin;

    AbstractCountdown(final Plugin plugin, final long seconds, final long speedUpTimestamp,
                      final Consumer<Long> runAction) {
        this.plugin = plugin;
        this.speedUpTimestamp = speedUpTimestamp;
        this.seconds = seconds;
        this.runAction = runAction;
    }

    @Override
    public void run() {
        active = true;

        run0(new Runnable() {

            @Override
            public void run() {
                runAction.accept(seconds--);

                if(seconds <= 0) {
                    stop();
                }
            }
        });

        Bukkit.getPluginManager().callEvent(new CountdownStartedEvent(this));
    }

    abstract void run0(final Runnable runAction);

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public boolean canSpeedUp() {
        return active && (seconds - speedUpTimestamp) > 2;
    }

    @Override
    public void speedUp() {
        seconds = speedUpTimestamp;

        Bukkit.getPluginManager().callEvent(new CountdownSpedUpEvent(this));
    }

    void stop0() {
        active = false;

        Bukkit.getPluginManager().callEvent(new CountdownStoppedEvent(this));
    }
}
