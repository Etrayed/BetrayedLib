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
package io.github.etrayed.betrayedlib.countdownapi.countdown.configuration;

import io.github.etrayed.betrayedlib.countdownapi.countdown.configuration.action.ExecutableAction;
import io.github.etrayed.betrayedlib.countdownapi.countdown.configuration.action.ExecutableActionType;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.Map;

/**
 * @author Etrayed
 */
public interface CountdownConfiguration {

    int addFixedActionAt(final long timestamp, final ExecutableActionType executableActionType, final String value);

    int addCustomActionAt(final long timestamp, final Runnable runnable);

    boolean hasActionAt(final long timestamp);

    void removeActionsAt(final long timestamp);

    void removeActionAt(final long timestamp, final int identifier);

    Collection<ExecutableAction> collectActionsAt(final long timestamp);

    Map<Long, Collection<ExecutableAction>> listAllActions();

    Plugin getPlugin();

    long getSeconds();

    long getSpeedUpTimestamp();

    void shouldUpdateExperience(final boolean shouldUpdateExperience);

    boolean shouldUpdateExperience();
}
