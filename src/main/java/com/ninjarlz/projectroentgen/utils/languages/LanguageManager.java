package com.ninjarlz.projectroentgen.utils.languages;

import lombok.Getter;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class responsible for managing the languages and locales in the app.
 */
public class LanguageManager {

    /**
     * Stores an English locale.
     */
    private Locale englishLocale = new Locale("en", "EN");
    /**
     * Stores an English resource bundle.
     */
    private ResourceBundle englishBundle = ResourceBundle.getBundle("i18n.AppBundle", englishLocale);
    /**
     * Stores a Polish resource bundle.
     */
    private ResourceBundle polishBundle = ResourceBundle.getBundle("i18n.AppBundle");
    /**
     * Stores the currently used language resource bundle.
     */
    @Getter
    private ResourceBundle currentBundle = englishBundle;

    private LanguageManager() {}

    private static LanguageManager instance;

    public static LanguageManager getInstance() {
        if (instance == null) {
            instance = new LanguageManager();
        }
        return instance;
    }

    public enum Language {ENGLISH, POLISH}

    /**
     * Changes the currently used language resource bundle.
     *
     * @param language used language resource bundle.
     */
    public void changeLanguage(Language language) {
        switch (language) {
            case ENGLISH:
                currentBundle = englishBundle;
                break;
            case POLISH:
                currentBundle = polishBundle;
                break;
        }
    }

}
