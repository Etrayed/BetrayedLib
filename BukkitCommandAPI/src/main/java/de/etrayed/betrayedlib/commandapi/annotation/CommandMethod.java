package de.etrayed.betrayedlib.commandapi.annotation;

import de.etrayed.betrayedlib.commandapi.CommandAPI;

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
 *     {@link org.bukkit.command.Command Command}, {@link String}[] only and however you want!
 *
 * <pre>
 *     {@code
 *     @CommandMethod(name = "myCommand")
 *     public void myCommand(final CommandSender commandSender, final String[] args) {
 *         // your code
 *     }
 *     }
 * </pre>
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
}
