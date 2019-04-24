package io.github.betrayedlib.betterconfig;

import java.io.File;
import java.io.IOException;

/**
 * @author Etrayed
 */
@SuppressWarnings("WeakerAccess")
abstract class AbstractConfiguration<I, O> implements Configuration<I, O> {

    protected final File file;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    protected AbstractConfiguration(final File file) {
        this.file = file;

        if(!file.exists()) {
            try {
                file.mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public final File getFile() {
        return file;
    }

    protected abstract void save();
}
