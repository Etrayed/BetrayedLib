package io.github.etrayed.betrayedlib.betterconfig;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Etrayed
 */
@SuppressWarnings("WeakerAccess")
public class YamlConfiguration extends AbstractConfiguration<Object, Object> {

    public static final Yaml YAML;

    private final Map<String, Object> keyValues;

    @SuppressWarnings("unchecked")
    public YamlConfiguration(final File file) {
        super(file);

        try (final InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file),
                Charset.forName("UTF-8"))) {
            this.keyValues = YAML.loadAs(inputStreamReader, Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void save() {
        try {
            final FileWriter fileWriter = new FileWriter(file);

            YAML.dump(keyValues, fileWriter);

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getValue(final String path) {
        return keyValues.get(path);
    }

    @Override
    public void setValue(final String path, Object input) {
        keyValues.put(path, input);

        this.save();
    }

    @Override
    public boolean hasValue(final String path) {
        return keyValues.containsKey(path);
    }

    @Override
    public void removeValue(final String path) {
        keyValues.remove(path);

        this.save();
    }

    @Override
    public List<String> listValues() {
        return new ArrayList<>(keyValues.keySet());
    }

    static {
        YAML = new Yaml();
    }
}
