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

import com.google.common.base.Preconditions;

import io.github.etrayed.betrayedlib.countdownapi.countdown.configuration.CountdownConfiguration;
import io.github.etrayed.betrayedlib.countdownapi.countdown.configuration.action.ExecutableAction;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

/**
 * @author Etrayed
 */
public class CountdownFactory {

    private CountdownFactory() {}

    public static Countdown createCountdown(final CountdownConfiguration countdownConfiguration, final boolean async) {
        final Countdown countdown;

        Preconditions.checkNotNull(countdownConfiguration, "\"countdownConfiguration\" cannot be null.");

        if(async) {
            countdown = new SyncCountdown(countdownConfiguration.getPlugin(), countdownConfiguration.getSeconds(),
                    countdownConfiguration.getSpeedUpTimestamp(),
                    countdownConfigurationConsumer(countdownConfiguration));
        } else {
            countdown = new AsyncCountdown(countdownConfiguration.getPlugin(), countdownConfiguration.getSeconds(),
                    countdownConfiguration.getSpeedUpTimestamp(),
                    countdownConfigurationConsumer(countdownConfiguration));
        }

        return countdown;
    }

    private static Consumer<Long> countdownConfigurationConsumer(final CountdownConfiguration countdownConfiguration) {
        return new Consumer<Long>() {

            @Override
            public void accept(final Long l) {
                if(countdownConfiguration.hasActionAt(l)) {
                    for (final ExecutableAction executableAction : countdownConfiguration.collectActionsAt(l)) {
                        executableAction.execute(countdownConfiguration.getPlugin());
                    }
                }

                if(countdownConfiguration.shouldUpdateExperience()) {
                    for (final Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        onlinePlayer.setLevel(Math.toIntExact(l));
                        onlinePlayer.setExp((float) ((0.99D / countdownConfiguration.getSeconds()) * l));
                    }
                }
            }
        };
    }
}
