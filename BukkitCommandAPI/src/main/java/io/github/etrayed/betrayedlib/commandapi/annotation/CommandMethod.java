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
package io.github.etrayed.betrayedlib.commandapi.annotation;

import io.github.etrayed.betrayedlib.commandapi.CommandAPI;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * You need to annotate your command method with {@link CommandMethod this annotation},
 * if you want to use the {@link CommandAPI}. <br>
 * <br>
 *     Important: <br>
 *     - You can your method like whatever you want! <br>
 *     - You can use the parameter types {@link org.bukkit.command.CommandSender CommandSender},
 *     {@link org.bukkit.command.Command Command}, {@link String} and {@link String}[] only and however you want!
 *     - The {@link String} returns the used command label.
 *
 * @author Etrayed
 *
 * @see CommandAPI
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface CommandMethod {

    /**
     *
     * The command needs a name!
     *
     * @return the name of the command.
     */
    String name();

    /**
     *
     * Permission for the command. (Not necessary)
     *
     * @return Permission for the command.
     */
    String permission() default "";

    /**
     *
     * Should the {@link CommandAPI#getNoPermissionMessage() default "NoPermission" message} be used?
     *
     * @return if it should.
     */
    boolean useDefaultNoPermissionMessage() default true;

    /**
     *
     * If {@link CommandMethod#useDefaultNoPermissionMessage()} is false,
     * you can configure a custom "NoPermission" message
     *
     * @return the custom "NoPermission" message, if configured.
     */
    String customNoPermissionMessage() default "";

    /**
     *
     * If true, the {@link org.bukkit.command.Command Command} can only be executed by a player.
     *
     * @return if it should.
     */
    boolean blockConsoleCommandSenders();
}
