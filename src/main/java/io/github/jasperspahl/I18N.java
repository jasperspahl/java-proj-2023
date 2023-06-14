package io.github.jasperspahl;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18N {
    private static final Locale lang = Locale.getDefault();
    private static final ResourceBundle i18n = ResourceBundle.getBundle("i18n", lang);
    public static String getString(String key) {
        return i18n.getString(key);
    }
}
