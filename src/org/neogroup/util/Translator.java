
package org.neogroup.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Translator {

    private String defaultBundleName;

    public String getDefaultBundleName() {
        return defaultBundleName;
    }

    public void setDefaultBundleName(String defaultBundleName) {
        this.defaultBundleName = defaultBundleName;
    }

    public String getString (String key, Object... args) {
        return getString(key, Locale.getDefault(), args);
    }

    public String getString (String key, Locale locale, Object... args) {
        return getBundleString(defaultBundleName, key, locale, args);
    }

    public String getBundleString (String bundleName, String key, Object... args) {
        return getBundleString(bundleName, key, Locale.getDefault(), args);
    }

    public String getBundleString (String bundleName, String key, Locale locale, Object... args) {
        return MessageFormat.format(ResourceBundle.getBundle(bundleName, locale).getString(key), args);
    }
}
