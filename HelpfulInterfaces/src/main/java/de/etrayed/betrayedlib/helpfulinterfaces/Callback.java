package de.etrayed.betrayedlib.helpfulinterfaces;

/**
 * (JavaDoc)
 *
 * @author Etrayed
 * Created for the project BetrayedLib on 21.03.2019
 * <p>
 * © Etrayed 2019
 */
@HelpfulInterface
public interface Callback<T> {

    void callSuccess(final T t);

    void callFail(final Exception exception);
}
