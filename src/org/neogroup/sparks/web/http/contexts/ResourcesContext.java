package org.neogroup.sparks.web.http.contexts;

import org.neogroup.sparks.util.MimeTypes;
import org.neogroup.sparks.util.encoding.GZIPCompression;
import org.neogroup.sparks.web.http.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

public class ResourcesContext extends Context {

    private static final String DEFAULT_DIGEST_ENCRYPTION = "MD5";
    private static final String URI_FOLDER_SEPARATOR = "/";
    private static final String FOLDER_HTML_DOCUMENT_TEMPLATE = "<!DOCTYPE html><html><head><title>%s</title><body>%s</body></html></head>";
    private static final String FOLDER_HTML_LIST_TEMPLATE = "<ul style=\"list-style-type: none;\">%s</ul>";
    private static final String FOLDER_HTML_ITEM_TEMPLATE = "<li><a href=\"%s\">%s</a></li>";

    private final String resourcesPath;
    private boolean isLocalDirectory = false;

    public ResourcesContext(String path, String resourcesPath) {
        super(path);
        this.resourcesPath = resourcesPath;

        File localDirectory = new File(resourcesPath);
        if (localDirectory.exists() && localDirectory.isDirectory()) {
            isLocalDirectory = true;
        }
    }

    @Override
    public void onContext(HttpRequest request, HttpResponse response) {

        String path = request.getPath().substring(getPath().length());
        String resourceName = resourcesPath + path.replaceAll(URI_FOLDER_SEPARATOR, File.separator);

        if (isLocalDirectory) {
            File file = new File(resourceName);
            if(file.exists()) {
                if (file.isDirectory()) {
                    handleDirectoryResponse (request, response, file);
                }
                else {
                    handleFileResponse (request, response, file);
                }
            }
            else {
                handleFileNotFoundResponse (request, response, file);
            }
        }
        else {
            byte[] resourceBytes = getResourceBytes(resourceName);
            if (resourceBytes != null) {
                handleResourceResponse(request, response, resourceBytes, MimeTypes.getMimeType(resourceName), null);
            } else {
                handleResourceNotFoundResponse(request, response, resourceName);
            }
        }
    }

    protected byte[] getResourceBytes (String resourceName) {

        byte[] resourceBytes = null;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourceName);
        if (inputStream != null) {
            try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
                int nRead;
                byte[] data = new byte[1024];
                while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                buffer.flush();
                resourceBytes = buffer.toByteArray();
            }
            catch (Exception ex) {}
        }
        return resourceBytes;
    }

    protected void handleDirectoryResponse (HttpRequest request, HttpResponse response, File file) {

        StringBuilder list = new StringBuilder();
        Path filePath = file.toPath();
        Path baseFilePath = Paths.get(resourcesPath);
        File[] subFiles = file.listFiles();
        Arrays.sort(subFiles);
        for (File subFile : subFiles) {
            String subFileLink = filePath.relativize(baseFilePath).resolve(request.getPath()).resolve(subFile.getName()).toString();
            String subFileName = subFile.getName();
            if (subFile.isDirectory()) {
                subFileName += File.separator;
            }
            list.append(String.format(FOLDER_HTML_ITEM_TEMPLATE, subFileLink, subFileName));
        }
        String htmlBody = String.format(FOLDER_HTML_LIST_TEMPLATE, list.toString());
        String document = String.format(FOLDER_HTML_DOCUMENT_TEMPLATE, file.getName(), htmlBody);
        response.addHeader(HttpHeader.CONTENT_TYPE, MimeTypes.TEXT_HTML);
        response.setBody(document.getBytes());
    }

    protected void handleFileResponse (HttpRequest request, HttpResponse response, File file) {

        byte[] fileBytes = null;
        try {
            fileBytes = Files.readAllBytes(file.toPath());
        }
        catch (Exception ex) {
            throw new RuntimeException("Error reading file \"" + file + "\" !!");
        }

        Date lastModifiedDate = new Date(file.lastModified());
        String mimeType = MimeTypes.getMimeType(file);
        handleResourceResponse(request, response, fileBytes, mimeType, lastModifiedDate);
    }

    protected void handleFileNotFoundResponse (HttpRequest request, HttpResponse response, File file) {

        response.setResponseCode(HttpResponseCode.NOT_FOUND);
        response.setBody("File \"" + file.getName() + "\" not found !!");
    }

    protected void handleResourceNotFoundResponse (HttpRequest request, HttpResponse response, String resourceName) {

        response.setResponseCode(HttpResponseCode.NOT_FOUND);
        response.setBody("Resource \"" + resourceName + "\" not found !!");
    }

    protected final void handleResourceResponse (HttpRequest request, HttpResponse response, byte[] resourceBytes, String mimeType, Date lastModifiedDate) {

        String checksum = null;
        try {
            checksum = Base64.getEncoder().encodeToString(MessageDigest.getInstance(DEFAULT_DIGEST_ENCRYPTION).digest(resourceBytes));
        }
        catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Error obtaining file checksum", ex);
        }

        int responseCode = HttpResponseCode.OK;
        String modifiedSinceHeader = request.getHeaders().getFirst(HttpHeader.IF_MODIFIED_SINCE);
        if (modifiedSinceHeader != null && lastModifiedDate != null) {
            Date modifiedSinceDate = null;
            try {
                modifiedSinceDate = HttpServerUtils.getDate(modifiedSinceHeader);
                if (!lastModifiedDate.after(modifiedSinceDate)) {
                    responseCode = HttpResponseCode.NOT_MODIFIED;
                }
            }
            catch (ParseException ex) {}
        }
        else {
            String nonModifiedChecksum = request.getHeaders().getFirst(HttpHeader.IF_NONE_MATCH);
            if (nonModifiedChecksum != null) {
                if (checksum.equals(nonModifiedChecksum)) {
                    responseCode = HttpResponseCode.NOT_MODIFIED;
                }
            }
        }

        response.setResponseCode(responseCode);
        response.addHeader(HttpHeader.CONTENT_TYPE, mimeType);
        response.addHeader(HttpHeader.E_TAG, checksum);
        if (lastModifiedDate != null) {
            response.addHeader(HttpHeader.LAST_MODIFIED, HttpServerUtils.formatDate(lastModifiedDate));
        }

        if (responseCode == HttpResponseCode.OK) {
            String acceptedEncoding = request.getHeaders().getFirst(HttpHeader.ACCEPT_ENCODING);
            if (acceptedEncoding != null) {

                if (acceptedEncoding.indexOf(HttpHeader.GZIP_CONTENT_ENCODING) >= 0) {
                    try {
                        response.addHeader(HttpHeader.CONTENT_ENCODING, HttpHeader.GZIP_CONTENT_ENCODING);
                        response.addHeader(HttpHeader.VARY, HttpHeader.ACCEPT_ENCODING);
                        resourceBytes = GZIPCompression.compress(resourceBytes);
                    }
                    catch (IOException ex) {
                        throw new RuntimeException("Error compressing file !!", ex);
                    }
                }
            }
            response.setBody(resourceBytes);
        }
    }
}