package de.etrayed.betrayedlib.reflectionhelp.helper;

/**
 * (JavaDoc)
 *
 * @author Etrayed
 * Created for the project BetrayedLib on 22.03.2019
 * <p>
 * © Etrayed 2019
 */
abstract class AbstractHelper implements Helper {

    private static boolean initialized = false;

    AbstractHelper() {
        if(!initialized)
            initialized = true;
        else
            throw new UnsupportedOperationException("Cannot initialize constructor twice!");
    }
}
