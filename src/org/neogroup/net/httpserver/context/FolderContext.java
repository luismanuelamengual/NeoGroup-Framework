
package org.neogroup.net.httpserver.context;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import org.neogroup.net.httpserver.HttpHeader;
import org.neogroup.net.httpserver.HttpRequest;
import org.neogroup.net.httpserver.HttpResponse;

public class FolderContext extends Context {

    private static final String URI_FOLDER_SEPARATOR = "/";
    private static final String FOLDER_HTML_DOCUMENT_TEMPLATE = "<!DOCTYPE html><html><head><title>%s</title><body>%s</body></html></head>";
    private static final String FOLDER_HTML_LIST_TEMPLATE = "<ul style=\"list-style-type: none;\">%s</ul>";
    private static final String FOLDER_HTML_ITEM_TEMPLATE = "<li><a href=\"%s\">%s</a></li>";
    
    private final Path folder;
    
    public FolderContext(String name, String folder) {
        this(name, Paths.get(folder));
    }
    
    public FolderContext(String name, Path folder) {
        super("/" + name + "/");
        this.folder = folder;
    }

    public Path getFolder() {
        return folder;
    }
    
    @Override
    public HttpResponse onContext(HttpRequest request) throws Exception {
        
        String path = request.getPath().substring(getPath().length());
        String[] pathElements = path.split(URI_FOLDER_SEPARATOR);
        
        Path filePath = folder.toAbsolutePath();
        for(String element : pathElements) {
            filePath = filePath.resolve(element);
        }
        
        HttpResponse response = new HttpResponse();
        File file = filePath.toFile();
        if(file.exists()) {
            if (file.isDirectory()) {
                StringBuilder list = new StringBuilder();
                File[] subFiles = file.listFiles();
                Arrays.sort(subFiles);
                for (File subFile : subFiles) {
                    String subFileLink = filePath.relativize(folder).resolve(request.getPath()).resolve(subFile.getName()).toString();
                    String subFileName = subFile.getName();
                    if (subFile.isDirectory()) {
                        subFileName += "/";
                    }
                    list.append(String.format(FOLDER_HTML_ITEM_TEMPLATE, subFileLink, subFileName));
                }
                String htmlBody = String.format(FOLDER_HTML_LIST_TEMPLATE, list.toString());
                String document = String.format(FOLDER_HTML_DOCUMENT_TEMPLATE, file.getName(), htmlBody);
                byte[] body = document.getBytes();
                response.addHeader(new HttpHeader("Content-type", "text/html"));
                response.setBody(body);
            } 
            else {
                String mimeType = Files.probeContentType(filePath);
                response.addHeader(new HttpHeader("Content-type", mimeType));
                response.setBody(Files.readAllBytes(filePath));
            }
        } else {
            throw new IllegalArgumentException("File \"" + path.toString() + "\" not found !!");
        }

        return response;
    }
}