package de.etrayed.betrayedlib.reflectionhelp.helper;

import de.etrayed.betrayedlib.reflectionhelp.util.ReflectiveException;
import de.etrayed.betrayedlib.reflectionhelp.util.WrappedClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * (JavaDoc)
 *
 * @author Etrayed
 * Created for the project BetrayedLib on 22.03.2019
 * <p>
 * © Etrayed 2019
 */
public final class MethodHelper extends AbstractHelper {

    public Method getMethod(final WrappedClass<?> wrappedClass, final String name, final Class<?>... parameters) {
        try {
            return wrappedClass.getInstance().getMethod(name, parameters);
        } catch (NoSuchMethodException e) {
            throw new ReflectiveException(e);
        }
    }

    public Method getDeclaredMethod(final WrappedClass<?> wrappedClass, final String name, final Class<?>... parameters) {
        try {
            return wrappedClass.getInstance().getDeclaredMethod(name, parameters);
        } catch (NoSuchMethodException e) {
            throw new ReflectiveException(e);
        }
    }

    public <T> T executeMethod(final Method method, final Object instance, final Class<T> returnType, final Object... parameters) {
        try {
            return returnType.cast(method.invoke(instance, parameters));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ReflectiveException(e);
        }
    }
}
