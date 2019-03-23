package de.etrayed.betrayedlib.reflectionhelp.helper;

import de.etrayed.betrayedlib.reflectionhelp.util.ReflectiveException;
import de.etrayed.betrayedlib.reflectionhelp.util.WrappedClass;

import java.lang.reflect.Field;

/**
 * (JavaDoc)
 *
 * @author Etrayed
 * Created for the project BetrayedLib on 23.03.2019
 * <p>
 * © Etrayed 2019
 */
public class FieldHelper extends AbstractHelper {

    public FieldHelper() {
        super();
    }

    public Field getField(final WrappedClass<?> wrappedClass, final String name) {
        try {
            return wrappedClass.getInstance().getField(name);
        } catch (NoSuchFieldException e) {
            throw new ReflectiveException(e);
        }
    }

    public Field getDeclaredField(final WrappedClass<?> wrappedClass, final String name) {
        try {
            return wrappedClass.getInstance().getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            throw new ReflectiveException(e);
        }
    }

    public <T> T getValue(final Field field, final Object instance, final WrappedClass<T> typeClass) {
        try {
            return typeClass.getInstance().cast(field.get(instance));
        } catch (IllegalAccessException e) {
            throw new ReflectiveException(e);
        }
    }

    public void setValue(final Field field, final Object instance, final Object value) {
        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new ReflectiveException(e);
        }
    }
}
