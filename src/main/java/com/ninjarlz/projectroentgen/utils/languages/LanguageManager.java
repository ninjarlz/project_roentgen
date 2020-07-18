package com.ninjarlz.projectroentgen.utils.languages;

import lombok.Getter;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {

    private Locale englishLocale = new Locale("en", "EN");
    private ResourceBundle englishBundle = ResourceBundle.getBundle("i18n.AppBundle", englishLocale);
    private ResourceBundle polishBundle = ResourceBundle.getBundle("i18n.AppBundle");
    @Getter
    private ResourceBundle currentBundle = englishBundle;

    public enum Language {ENGLISH, POLISH}

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
