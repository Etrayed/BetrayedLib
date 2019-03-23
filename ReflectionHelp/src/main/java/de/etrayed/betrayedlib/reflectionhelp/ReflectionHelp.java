package de.etrayed.betrayedlib.reflectionhelp;

import de.etrayed.betrayedlib.reflectionhelp.helper.*;

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
    private static final MethodHelper METHOD_HELPER;
    private static final FieldHelper FIELD_HELPER;

    static {
        CONSTRUCTOR_HELPER = new ConstructorHelper();
        METHOD_HELPER = new MethodHelper();
        FIELD_HELPER = new FieldHelper();
    }

    public static ConstructorHelper getConstructorHelper() {
        return CONSTRUCTOR_HELPER;
    }

    public static MethodHelper getMethodHelper() {
        return METHOD_HELPER;
    }

    public static FieldHelper getFieldHelper() {
        return FIELD_HELPER;
    }
}
