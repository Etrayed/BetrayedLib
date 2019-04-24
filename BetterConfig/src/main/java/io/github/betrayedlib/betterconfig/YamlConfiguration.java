package io.github.betrayedlib.betterconfig;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
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

    public YamlConfiguration(final File file) {
        super(file);

        try {
            final FileReader fileReader = new FileReader(file);

            keyValues = YAML.load(fileReader);

            fileReader.close();
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
    public Object getValue(String path) {
        return keyValues.get(path);
    }

    @Override
    public void setValue(String path, Object input) {
        keyValues.put(path, input);

        this.save();
    }

    @Override
    public boolean hasValue(String path) {
        return keyValues.containsKey(path);
    }

    @Override
    public void removeValue(String path) {
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
