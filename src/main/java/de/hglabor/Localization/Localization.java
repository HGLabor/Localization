package de.hglabor.Localization;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Localization {
    public final static Localization INSTANCE = new Localization();
    private static final Logger LOGGER = Logger.getLogger(Localization.class.getName());

    private final Map<Locale, Map<String, String>> translations;
    private final Gson gson;

    private Localization() {
        this.translations = new HashMap<>();
        this.gson = new Gson();
    }

    public static void main(String[] args) {
        try {
            INSTANCE.loadLanguageFiles(Paths.get("lang"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(INSTANCE.getMessage("kit",Locale.GERMAN));
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
        return translations.get(locale).getOrDefault(key,key);
    }
}
