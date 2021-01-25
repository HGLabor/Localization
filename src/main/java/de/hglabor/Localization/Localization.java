package de.hglabor.Localization;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.apache.commons.lang3.text.StrSubstitutor;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class Localization {
    public final static Localization INSTANCE = new Localization();

    private final Map<Locale, Map<String, String>> translations;
    private final Gson gson;

    private Localization() {
        this.translations = new HashMap<>();
        this.gson = new Gson();
    }

    public void loadLanguageFiles(Path folder) throws Exception {
        File[] files = folder.toFile().listFiles();

        if (files == null) {
            throw new Exception("Folder files are null");
        }

        for (File languageFile : files) {
            String name = languageFile.getName();
            System.out.printf("Parsing file %s\n", name);

            if (name.contains("_")) {
                name = name.substring(name.indexOf("_") + 1, name.lastIndexOf(".json"));
                JsonReader reader = new JsonReader(new FileReader(languageFile));
                Map<String, String> json = gson.fromJson(reader, Map.class);

                if (json != null) {
                    translations.put(Locale.forLanguageTag(name), json);
                }
            }
        }
    }

    public String getMessage(String key, Locale locale) {
        return translations.get(locale).getOrDefault(key, key);
    }

    public String getMessage(String key, Map<String, String> values, Locale locale) {
        return StrSubstitutor.replace(getMessage(key, locale), values);
    }
}
