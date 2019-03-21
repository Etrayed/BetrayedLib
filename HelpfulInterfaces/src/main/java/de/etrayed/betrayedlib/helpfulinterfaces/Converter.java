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
public interface Converter<I, O> {

    O convert(final I i);
}
