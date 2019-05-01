package io.github.etrayed.betrayedlib.betterconfig;

import java.io.File;
import java.util.List;

/**
 * @author Etrayed
 */
public interface Configuration<I, O> {

    O getValue(final String path);

    void setValue(final String path, final I input);

    boolean hasValue(final String path);

    void removeValue(final String path);

    List<String> listValues();

    File getFile();
}
