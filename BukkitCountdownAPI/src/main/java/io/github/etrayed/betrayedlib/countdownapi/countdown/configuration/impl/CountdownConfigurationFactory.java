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
package io.github.etrayed.betrayedlib.countdownapi.countdown.configuration.impl;

import com.google.common.base.Preconditions;

import io.github.etrayed.betrayedlib.countdownapi.countdown.configuration.CountdownConfiguration;

import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

/**
 * @author Etrayed
 */
public class CountdownConfigurationFactory {

    private CountdownConfigurationFactory() {}

    public static CountdownConfigurationFactoryWorker newCountdownConfigurationFactoryWorker(final Plugin plugin,
                                                                                             final long seconds) {
        return new CountdownConfigurationFactoryWorker(plugin, seconds);
    }

    public static final class CountdownConfigurationFactoryWorker {

        private final Plugin plugin;

        private final long seconds;

        private long speedUpTimestamp;

        private Consumer<CountdownConfiguration> countdownConfigurationConsumer;

        private CountdownConfigurationFactoryWorker(final Plugin plugin, final long seconds) {
            this.plugin = plugin;
            this.seconds = seconds;
            this.speedUpTimestamp = 0;

            this.countdownConfigurationConsumer = new Consumer<CountdownConfiguration>() {
                @Override
                public void accept(final CountdownConfiguration countdownConfiguration) {}
            };
        }

        public CountdownConfigurationFactoryWorker withSpeedUpTimestamp(final long speedUpTimestamp) {
            Preconditions.checkArgument(speedUpTimestamp < seconds,
                    "\"speedUpTimestamp\"(" + speedUpTimestamp
                            + ") must be at lower than \"seconds\"(" + seconds + ").");

            this.speedUpTimestamp = speedUpTimestamp;

            return this;
        }

        public CountdownConfigurationFactoryWorker withAdditionalConfigurations
                (final Consumer<CountdownConfiguration> countdownConfigurationConsumer) {
            this.countdownConfigurationConsumer = Preconditions.checkNotNull(countdownConfigurationConsumer,
                    "\"countdownConfigurationConsumer\" cannot be null.");

            return this;
        }

        public CountdownConfiguration completeWork() {
            return new CountdownConfigurationImpl(plugin, seconds, speedUpTimestamp, countdownConfigurationConsumer);
        }
    }
}