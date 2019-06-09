package io.github.etrayed.betrayedlib.betterconfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Etrayed
 */
@SuppressWarnings("WeakerAccess")
public class JsonConfiguration extends AbstractConfiguration<JsonElement, JsonElement> {

    public static final Gson GSON;

    private final JsonObject jsonObject;

    public JsonConfiguration(final File file) {
        super(file);

        try (final InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file),
                Charset.forName("UTF-8"))) {
            this.jsonObject = GSON.fromJson(inputStreamReader, JsonObject.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void save() {
        try {
            final FileWriter fileWriter = new FileWriter(file);

            GSON.toJson(jsonObject, fileWriter);

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JsonElement getValue(final String path) {
        return jsonObject.get(path);
    }

    @Override
    public void setValue(final String path, final JsonElement input) {
        jsonObject.add(path, input);

        this.save();
    }

    @Override
    public boolean hasValue(final String path) {
        return jsonObject.has(path);
    }

    @Override
    public void removeValue(final String path) {
        jsonObject.remove(path);

        this.save();
    }

    @Override
    public List<String> listValues() {
        final List<String> values = new ArrayList<>();

        for (final Map.Entry<String, JsonElement> stringJsonElementEntry : jsonObject.entrySet()) {
            values.add(stringJsonElementEntry.getKey());
        }

        return values;
    }

    static {
        GSON = new GsonBuilder().setPrettyPrinting().create();
    }
}
