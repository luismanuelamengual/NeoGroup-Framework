
package org.neogroup.websparks.encoding;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public abstract class MimeType {
    
    public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
    public static final String TEXT_HTML = "text/html";
    public static final String TEXT_PLAIN = "text/plain";
    
    private static final String FILE_EXTENSION_REGEX = "\\.(?=[^\\.]+$)";
    
    private static final Map<String,String> mimeTypes;
    
    static {
        mimeTypes = new HashMap<>();
    }
    
    public static String getMimeType (File file) {
        
        String[] nameExtension = file.getName().split(FILE_EXTENSION_REGEX);
        String extension = nameExtension.length == 2 ? nameExtension[1] : null;
        String mimeType = mimeTypes.get(extension);
        if (mimeType == null) {
            try {
                mimeType = Files.probeContentType(file.toPath());
            }
            catch (Exception ex) {
                mimeType = APPLICATION_OCTET_STREAM;
            }
            mimeTypes.put(extension, mimeType);
        }
        return mimeType;
    }
}