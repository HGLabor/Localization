package de.hglabor.utils.localization;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.apache.commons.text.StringSubstitutor;

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
    private String colorReplaceValue;

    private Localization() {
        this.translations = new HashMap<>();
        this.gson = new Gson();
    }

    public static String t(String key, Locale locale) {
        return Localization.INSTANCE.getMessage(key, locale);
    }

    public static String t(String key, Map<String, String> values, Locale locale) {
        return Localization.INSTANCE.getMessage(key, values, locale);
    }

    public void loadLanguageFiles(Path folder, String colorReplaceValue) {
        try {
            this.colorReplaceValue = colorReplaceValue;
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
                        Locale key = Locale.forLanguageTag(name);
                        if (translations.containsKey(key)) {
                            translations.get(key).putAll(json);
                        } else {
                            translations.put(key, json);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMessage(String key, Locale locale) {
        if (translations.containsKey(locale)) {
            return translations.get(locale).getOrDefault(key, key).replaceAll("&", colorReplaceValue);
        } else {
            return translations.getOrDefault(Locale.ENGLISH,Map.of()).getOrDefault(key, key).replaceAll("&", colorReplaceValue);
        }
    }

    public String getMessage(String key, Map<String, String> values, Locale locale) {
        return StringSubstitutor.replace(getMessage(key, locale), values).replaceAll("&", colorReplaceValue);
    }
}
