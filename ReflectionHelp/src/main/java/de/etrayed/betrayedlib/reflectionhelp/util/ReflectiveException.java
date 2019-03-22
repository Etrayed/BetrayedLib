package de.etrayed.betrayedlib.reflectionhelp.util;

/**
 * (JavaDoc)
 *
 * @author Etrayed
 * Created for the project BetrayedLib on 22.03.2019
 * <p>
 * © Etrayed 2019
 */
public class ReflectiveException extends RuntimeException {

    public ReflectiveException() {
        super();
    }

    public ReflectiveException(String message) {
        super(message);
    }

    public ReflectiveException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectiveException(Throwable cause) {
        super(cause);
    }
}
