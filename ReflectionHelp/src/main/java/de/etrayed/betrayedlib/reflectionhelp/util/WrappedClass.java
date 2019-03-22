package de.etrayed.betrayedlib.reflectionhelp.util;

/**
 * (JavaDoc)
 *
 * @author Etrayed
 * Created for the project BetrayedLib on 22.03.2019
 * <p>
 * © Etrayed 2019
 */
public final class WrappedClass<T> {

    private final Class<T> instance;

    public WrappedClass(final Class<T> instance) {
        this.instance = instance;
    }

    public Class<T> getInstance() {
        return instance;
    }
}
