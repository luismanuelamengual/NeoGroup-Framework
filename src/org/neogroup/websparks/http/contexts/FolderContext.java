
package org.neogroup.websparks.http.contexts;

import org.neogroup.websparks.util.MimeTypes;
import org.neogroup.websparks.http.HttpHeader;
import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;
import org.neogroup.websparks.util.encoding.GZIPCompression;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FolderContext extends Context {

    private static final String URI_FOLDER_SEPARATOR = "/";
    private static final String FOLDER_HTML_DOCUMENT_TEMPLATE = "<!DOCTYPE html><html><head><title>%s</title><body>%s</body></html></head>";
    private static final String FOLDER_HTML_LIST_TEMPLATE = "<ul style=\"list-style-type: none;\">%s</ul>";
    private static final String FOLDER_HTML_ITEM_TEMPLATE = "<li><a href=\"%s\">%s</a></li>";
    
    private final Path folder;
    
    public FolderContext(String path, String folder) {
        this(path, Paths.get(folder));
    }
    
    public FolderContext(String path, Path folder) {
        super(path);
        this.folder = folder;
    }

    public Path getFolder() {
        return folder;
    }
    
    @Override
    public void onContext(HttpRequest request, HttpResponse response) {
        
        String path = request.getPath().substring(getPath().length());
        String[] pathElements = path.split(URI_FOLDER_SEPARATOR);
        
        Path filePath = folder.toAbsolutePath();
        for(String element : pathElements) {
            filePath = filePath.resolve(element);
        }

        File file = filePath.toFile();
        if(file.exists()) {
            byte[] body = null;
            
            if (file.isDirectory()) {
                StringBuilder list = new StringBuilder();
                File[] subFiles = file.listFiles();
                Arrays.sort(subFiles);
                for (File subFile : subFiles) {
                    String subFileLink = filePath.relativize(folder).resolve(request.getPath()).resolve(subFile.getName()).toString();
                    String subFileName = subFile.getName();
                    if (subFile.isDirectory()) {
                        subFileName += File.separator;
                    }
                    list.append(String.format(FOLDER_HTML_ITEM_TEMPLATE, subFileLink, subFileName));
                }
                String htmlBody = String.format(FOLDER_HTML_LIST_TEMPLATE, list.toString());
                String document = String.format(FOLDER_HTML_DOCUMENT_TEMPLATE, file.getName(), htmlBody);
                body = document.getBytes();
                response.addHeader(HttpHeader.CONTENT_TYPE, MimeTypes.TEXT_HTML);
            } 
            else {
                response.addHeader(HttpHeader.CONTENT_TYPE, MimeTypes.getMimeType(file));

                try { 
                    body = Files.readAllBytes(filePath);
                } 
                catch (IOException ex) {
                    throw new RuntimeException("Error reading file \"" + file + "\" !!");
                }

                String acceptedEncoding = request.getHeaders().getFirst(HttpHeader.ACCEPT_ENCODING);
                if (acceptedEncoding != null) {

                    if (acceptedEncoding.indexOf(HttpHeader.GZIP_CONTENT_ENCODING) >= 0) {
                        try {
                            response.addHeader(HttpHeader.CONTENT_ENCODING, HttpHeader.GZIP_CONTENT_ENCODING);
                            response.addHeader(HttpHeader.VARY, HttpHeader.ACCEPT_ENCODING);
                            body = GZIPCompression.compress(body);
                        } catch (IOException ex) {
                            throw new RuntimeException("Error compressing file \"" + file + "\" !!");
                        }
                    }
                }
            }
            
            response.setBody(body);
        } 
        else {
            throw new IllegalArgumentException("File \"" + file + "\" not found !!");
        }
    }
}