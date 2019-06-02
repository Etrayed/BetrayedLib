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

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author Etrayed
 */
public class AsyncCountdown extends AbstractCountdown {

    private ScheduledExecutorService scheduledExecutorService;

    AsyncCountdown(final Plugin plugin, final long seconds, final long speedUpTimestamp,
                   final Consumer<Long> runAction) {
        super(plugin, seconds, speedUpTimestamp, runAction);
    }

    @Override
    void run0(final Runnable runAction) {
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        this.scheduledExecutorService.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                if(!Bukkit.getPluginManager().isPluginEnabled(plugin)) {
                    stop();

                    return;
                }

                runAction.run();
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    @Override
    public void stop() {
        if(scheduledExecutorService == null) {
            return;
        }

        this.scheduledExecutorService.shutdown();

        stop0();
    }
}
