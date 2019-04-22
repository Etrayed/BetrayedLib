package de.etrayed.betrayedlib.commandapi.annotation;

import com.google.common.collect.Maps;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Etrayed
 */
public class CommandMethodResolver {

    private Class<?> objectClass;

    public void prepare(final Class<?> objectClass) {
        this.objectClass = objectClass;
    }

    public void prepare(final Object objectInstance) {
        this.objectClass = objectInstance.getClass();
    }

    @SuppressWarnings("ToArrayCallWithZeroLengthArrayArgument")
    public Map<Method, CommandMethod> resolveCommandMethods() {
        final Method[] methods = objectClass.getDeclaredMethods();
        final Map<Method, CommandMethod> commandMethods = Maps.newHashMap();

        for (Method method : methods) {
            final CommandMethod commandMethod = method.getDeclaredAnnotation(CommandMethod.class);

            if (commandMethod != null)
                commandMethods.put(method, commandMethod);
        }

        return commandMethods;
    }
}
