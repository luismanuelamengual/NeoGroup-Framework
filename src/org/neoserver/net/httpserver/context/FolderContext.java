
package org.neoserver.net.httpserver.context;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.neoserver.net.httpserver.HttpHeader;
import org.neoserver.net.httpserver.HttpRequest;
import org.neoserver.net.httpserver.HttpResponse;

public class FolderContext extends Context {

    private static final String URI_FOLDER_SEPARATOR = "/";
    
//    private static final String START_CONTEXT = "^";
//    private static final String END_CONTEXT = ".*";
//    private static final String URI_FOLDER_SEPARATOR = "/";
//    private static final String[] FORBIDDEN_CHARACTERS = {"..", "~"};
//    private static final String FILE_EXTENSION_REGEX = "\\.(?=[^\\.]+$)";
//    private static final String FOLDER_DEFAULT_HTML_DOCUMENT = "<!DOCTYPE html><html><head><title>%s</title><body>%s</body></html></head>";
//    private static final String FOLDER_DEFAULT_HTML_BODY = "<table>%s</table>";
//    private static final String FOLDER_DEFAULT_HTML_ROW = "<tr><th><a href=\"%s\">%s</a></th></tr>";
    
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
    public HttpResponse onContext(HttpRequest request) {
        
        String path = request.getPath().substring(getPath().length());
        String[] pathElements = path.split(URI_FOLDER_SEPARATOR);
        
        Path filesPath = folder.toAbsolutePath();
        for(String element : pathElements) {
            filesPath = filesPath.resolve(element);
        }
        
        HttpResponse response = new HttpResponse();
        
        File file = filesPath.toFile();
        if(file.exists()) {
            if (file.isDirectory()) {
//                StringBuilder list = new StringBuilder();
//                for(File subFile : file.listFiles()) {
//                    list.append(String.format(FOLDER_DEFAULT_HTML_ROW,
//                            path.relativize(baseFolder).resolve(request.getContext()).resolve(subFile.getName()).toString(),
//                            subFile.getName()));
//                }
//                String htmlBody = String.format(FOLDER_DEFAULT_HTML_BODY, list.toString());
//                String document = String.format(FOLDER_DEFAULT_HTML_DOCUMENT, file.getName(), htmlBody);
//                byte[] body = document.getBytes();
//                response.setReasonPhrase(file.getName());
//                response.addHeader(new HttpHeader(HttpHeader.CONTENT_LENGTH, Integer.toString(body.length)));
//                response.addHeader(new HttpHeader(HttpHeader.CONTENT_TYPE, MimeType.HTML));
//                response.setResponseCode(HttpResponseCode.OK);
//                response.setBody(body);
            } 
            else {
                try {
                    String mimeType = Files.probeContentType(filesPath);
                    response.addHeader(new HttpHeader("Content-type", mimeType));
                    response.setBody(Files.readAllBytes(filesPath));
                }
                catch (Exception ex) {}
            }
        } else {
            throw new IllegalArgumentException("File not found !!");
        }

        return response;
    }
}