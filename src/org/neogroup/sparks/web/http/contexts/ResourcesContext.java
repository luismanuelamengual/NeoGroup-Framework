package org.neogroup.sparks.web.http.contexts;

import org.neogroup.sparks.util.MimeTypes;
import org.neogroup.sparks.web.http.HttpRequest;
import org.neogroup.sparks.web.http.HttpResponse;
import org.neogroup.sparks.web.http.HttpResponseCode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

public class ResourcesContext extends DataContext {

    private static final String URI_FOLDER_SEPARATOR = "/";

    private final String resourcesPath;

    public ResourcesContext(String path, String resourcesPath) {
        super(path);
        this.resourcesPath = resourcesPath;
    }

    @Override
    public void onContext(HttpRequest request, HttpResponse response) {

        String path = request.getPath().substring(getPath().length());
        String resourceName = resourcesPath + path.replaceAll(URI_FOLDER_SEPARATOR, File.separator);
        byte[] resourceBytes = getResourceBytes(resourceName);
        if (resourceBytes != null) {
            handleResourceResponse(request, response, resourceBytes, MimeTypes.getMimeType(resourceName), null);
        }
        else {
            handleResourceNotFoundResponse (request, response, resourceName);
        }
    }

    protected byte[] getResourceBytes (String resourceName) {

        byte[] resourceBytes = null;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourceName);
        if (inputStream != null) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            try {
                int nRead;
                byte[] data = new byte[1024];
                while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                buffer.flush();
            }
            catch (Exception ex) {}
            resourceBytes = buffer.toByteArray();
            try {inputStream.close();} catch (Exception ex) {}
        }
        return resourceBytes;
    }

    protected void handleResourceNotFoundResponse (HttpRequest request, HttpResponse response, String resourceName) {

        response.setResponseCode(HttpResponseCode.NOT_FOUND);
        response.setBody("Resource \"" + resourceName + "\" not found !!");
    }
}