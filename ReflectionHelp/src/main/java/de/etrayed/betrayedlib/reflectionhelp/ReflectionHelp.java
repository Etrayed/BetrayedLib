package de.etrayed.betrayedlib.reflectionhelp;

import de.etrayed.betrayedlib.reflectionhelp.helper.ConstructorHelper;

/**
 * (JavaDoc)
 *
 * @author Etrayed
 * Created for the project BetrayedLib on 22.03.2019
 * <p>
 * © Etrayed 2019
 */
public class ReflectionHelp {

    private static final ConstructorHelper CONSTRUCTOR_HELPER;

    static {
        CONSTRUCTOR_HELPER = new ConstructorHelper();
    }

    public static ConstructorHelper getConstructorHelper() {
        return CONSTRUCTOR_HELPER;
    }
}
