
package org.neogroup.sparks.web.http.contexts;

import org.neogroup.sparks.util.encoding.GZIPCompression;
import org.neogroup.sparks.web.http.*;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;

public abstract class DataContext extends Context {

    private static final String DEFAULT_DIGEST_ENCRYPTION = "MD5";

    public DataContext(String path) {
        super(path);
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