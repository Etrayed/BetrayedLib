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
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.github.etrayed.betrayedlib.countdownapi.countdown.configuration.CountdownConfiguration;
import io.github.etrayed.betrayedlib.countdownapi.countdown.configuration.action.CustomExecutableAction;
import io.github.etrayed.betrayedlib.countdownapi.countdown.configuration.action.ExecutableAction;
import io.github.etrayed.betrayedlib.countdownapi.countdown.configuration.action.ExecutableActionType;
import io.github.etrayed.betrayedlib.countdownapi.countdown.configuration.action.FixedExecutableAction;

import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Etrayed
 */
class CountdownConfigurationImpl implements CountdownConfiguration {

    private final Map<Long, Collection<ExecutableAction>> timestampsWithActions = Maps.newConcurrentMap();

    private final Plugin plugin;

    private final long seconds, speedUpTimestamp;

    private boolean shouldUpdateExperience;

    CountdownConfigurationImpl(final Plugin plugin, final long seconds, final long speedUpTimestamp,
                               final Consumer<CountdownConfiguration> countdownConfigurationConsumer) {
        this.plugin = plugin;
        this.seconds = seconds;
        this.speedUpTimestamp = speedUpTimestamp;

        countdownConfigurationConsumer.accept(this);
    }

    @Override
    public int addFixedActionAt(final long timestamp,
                                final ExecutableActionType executableActionType, final String value) {
        ensureAvailability(timestamp);

        return addExecutableAction(timestamp, new FixedExecutableAction(timestampsWithActions.size() + 1,
                executableActionType, value));
    }

    @Override
    public int addCustomActionAt(final long timestamp, final Runnable runnable) {
        ensureAvailability(timestamp);

        return addExecutableAction(timestamp,
                new CustomExecutableAction(timestampsWithActions.size() + 1, runnable));
    }

    @Override
    public boolean hasActionAt(final long timestamp) {
        return timestampsWithActions.containsKey(timestamp);
    }

    @Override
    public void removeActionsAt(final long timestamp) {
        timestampsWithActions.remove(timestamp);
    }

    @Override
    public void removeActionAt(final long timestamp, final int identifier) {
        ensureAvailability(timestamp);
        ensureAccessibility(timestamp);

        final Collection<ExecutableAction> executableActions = timestampsWithActions.get(timestamp);

        executableActions.removeIf(new Predicate<ExecutableAction>() {

            @Override
            public boolean test(final ExecutableAction executableAction) {
                return executableAction.getIdentifier() == identifier;
            }
        });

        if(executableActions.isEmpty()) {
            timestampsWithActions.remove(timestamp);
        } else {
            timestampsWithActions.replace(timestamp, executableActions);
        }
    }

    @Override
    public Collection<ExecutableAction> collectActionsAt(final long timestamp) {
        ensureAvailability(timestamp);
        ensureAccessibility(timestamp);

        return timestampsWithActions.get(timestamp);
    }

    @Override
    public Map<Long, Collection<ExecutableAction>> listAllActions() {
        return Maps.newHashMap(timestampsWithActions);
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public long getSeconds() {
        return seconds;
    }

    @Override
    public long getSpeedUpTimestamp() {
        return speedUpTimestamp;
    }

    @Override
    public void shouldUpdateExperience(final boolean shouldUpdateExperience) {
        this.shouldUpdateExperience = shouldUpdateExperience;
    }

    @Override
    public boolean shouldUpdateExperience() {
        return shouldUpdateExperience;
    }

    private int addExecutableAction(final long timestamp, ExecutableAction executableAction) {
        putExecutableAction(timestamp, executableAction);

        return executableAction.getIdentifier();
    }

    private void putExecutableAction(final long timestamp, final ExecutableAction executableAction) {
        final Collection<ExecutableAction> executableActions = timestampsWithActions.computeIfAbsent(timestamp,
                new Function<Long, Collection<ExecutableAction>>() {

            @Override
            public Collection<ExecutableAction> apply(final Long k) {
                return Lists.newArrayList();
            }
        });

        executableActions.add(executableAction);

        timestampsWithActions.replace(timestamp, executableActions);
    }

    private void ensureAvailability(final long timestamp) {
        Preconditions.checkArgument(timestamp >= 0,
                "\"timestamp\"(" + timestamp + ") needs to be at least 0.");
        Preconditions.checkArgument(timestamp > seconds,
                "\"timestamp\"(" + timestamp + ") cannot be higher than \"seconds\"(" + seconds + ").");
    }

    private void ensureAccessibility(final long timestamp) {
        Preconditions.checkArgument(timestampsWithActions.containsKey(timestamp),
                "No entry found for \"timestamp\"(" + timestamp + ").");
    }
}
