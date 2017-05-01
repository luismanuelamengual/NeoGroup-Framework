package org.neogroup.sparks.bundles;

import java.io.File;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class BundlesManager {

    private static final String BUNDLE_NAMESPACE_SEPARATOR = ".";

    private String defaultBundlesPath;
    private String defaultBundleName;

    public BundlesManager() {
        this.defaultBundlesPath = "";
        this.defaultBundleName = "general";
    }

    public String getDefaultBundlesPath() {
        return defaultBundlesPath;
    }

    public void setDefaultBundlesPath(String defaultBundlesPath) {
        this.defaultBundlesPath = defaultBundlesPath;
    }

    public String getDefaultBundleName() {
        return defaultBundleName;
    }

    public void setDefaultBundleName(String defaultBundleName) {
        this.defaultBundleName = defaultBundleName;
    }

    public String getString (String key, Locale locale, Object... args) {

        String bundleName = null;
        String bundleKey = null;
        int separator = key.lastIndexOf(BUNDLE_NAMESPACE_SEPARATOR);
        if (separator >= 0) {
            bundleName = defaultBundlesPath + key.substring(0, separator).replace(BUNDLE_NAMESPACE_SEPARATOR, File.separator);
            bundleKey = key.substring(separator+1);
        }
        else {
            bundleName = defaultBundlesPath + defaultBundleName;
            bundleKey = key;
        }
        return getBundleString(bundleName, bundleKey, locale, args);
    }

    public String getBundleString (String bundleName, String bundleKey, Locale locale, Object... args) {
        return MessageFormat.format(ResourceBundle.getBundle(bundleName, locale).getString(bundleKey), args);
    }
}

