package de.etrayed.betrayedlib.reflectionhelp.helper;

import de.etrayed.betrayedlib.reflectionhelp.util.ReflectiveException;
import de.etrayed.betrayedlib.reflectionhelp.util.WrappedClass;

import java.lang.reflect.Constructor;

/**
 * (JavaDoc)
 *
 * @author Etrayed
 * Created for the project BetrayedLib on 22.03.2019
 * <p>
 * © Etrayed 2019
 */
@SuppressWarnings("unchecked")
public class ConstructorHelper extends AbstractHelper {

    public ConstructorHelper() {
        super();
    }

    public Constructor<?> getConstructor(final WrappedClass<?> wrappedClass, final Class<?>... parameters) {
        try {
            return wrappedClass.getInstance().getConstructor(parameters);
        } catch (ReflectiveOperationException e) {
            throw new ReflectiveException(e);
        }
    }

    public Constructor<?> getDeclaredConstructor(final WrappedClass<?> wrappedClass, final Class<?>... parameters) {
        try {
            return wrappedClass.getInstance().getConstructor(parameters);
        } catch (ReflectiveOperationException e) {
            throw new ReflectiveException(e);
        }
    }

    public <T> T newInstance(final Constructor<T> constructor, final Object... parameters) {
        try {
            return constructor.newInstance(parameters);
        } catch (ReflectiveOperationException e) {
            throw new ReflectiveException(e);
        }
    }
}
