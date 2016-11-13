
package org.neogroup.web.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HttpResponse {
    
    public static final int RESPONSE_CODE_CONTINUE = 100;
    public static final int RESPONSE_CODE_SWITCHING_PROTOCOLS = 101;
    public static final int RESPONSE_CODE_OK = 200;
    public static final int RESPONSE_CODE_CREATED = 201;
    public static final int RESPONSE_CODE_ACCEPTED = 202;
    public static final int RESPONSE_CODE_NON_AUTHORITATIVE_INFOTMATION = 203;
    public static final int RESPONSE_CODE_NO_CONTENT = 204;
    public static final int RESPONSE_CODE_RESET_CONTENT = 205;
    public static final int RESPONSE_CODE_PARTIAL_CONTENT = 206;
    public static final int RESPONSE_CODE_MULTIPLE_CHOICES = 300;
    public static final int RESPONSE_CODE_MOVED_PERMANENTLY = 301;
    public static final int RESPONSE_CODE_FOUND = 302;
    public static final int RESPONSE_CODE_SEE_OTHER = 303;
    public static final int RESPONSE_CODE_NOT_MODIFIED = 304;
    public static final int RESPONSE_CODE_USE_PROXY = 305;
    public static final int RESPONSE_CODE_TEMPORARY_REDIRECT = 307;
    public static final int RESPONSE_CODE_BAD_REQUEST = 400;
    public static final int RESPONSE_CODE_UNAUTHORIZED = 401;
    public static final int RESPONSE_CODE_PAYMENT_REQUIRED = 402;
    public static final int RESPONSE_CODE_FORBIDDEN = 403;
    public static final int RESPONSE_CODE_NOT_FOUND = 404;
    public static final int RESPONSE_CODE_METHOD_NOT_ALLOWED = 405;
    public static final int RESPONSE_CODE_NOT_ACCEPTABLE = 406;
    public static final int RESPONSE_CODE_REQUEST_TIMEOUT = 408;
    public static final int RESPONSE_CODE_CONFLICT = 409;
    public static final int RESPONSE_CODE_GONE = 410;
    public static final int RESPONSE_CODE_LENGTH_REQUIRED = 411;
    public static final int RESPONSE_CODE_PRECONDITION_FAILED = 412;
    public static final int RESPONSE_CODE_REQUEST_ENTITY_TOO_LARGE = 413;
    public static final int RESPONSE_CODE_REQUEST_URI_TOO_LARGE = 414;
    public static final int RESPONSE_CODE_UNSUPPORTED_MEDIA_TYPE = 415;
    public static final int RESPONSE_CODE_REQUESTED_RANGE_NOT_SATISFIABLE = 416;
    public static final int RESPONSE_CODE_EXPECTATION_FAILED = 417;
    public static final int RESPONSE_CODE_INTERNAL_SERVER_ERROR = 500;
    public static final int RESPONSE_CODE_NOT_IMPLEMENTED = 501;
    public static final int RESPONSE_CODE_BAD_GATEWAY = 502;
    public static final int RESPONSE_CODE_SERVICE_UNAVAILABLE = 503;
    public static final int RESPONSE_CODE_GATEWAY_TIMEOUT = 504;
    public static final int RESPONSE_CODE_HTTP_VERSION_NOT_SUPPORTED = 505;
    
    private final List<HttpHeader> headers;
    private int responseCode;
    private byte[] body;

    public HttpResponse() {
        this(RESPONSE_CODE_OK);
    }
    
    public HttpResponse(int responseCode) {
        this(responseCode, null);
    }
    
    public HttpResponse(byte[] body) {
        this(RESPONSE_CODE_OK, body);
    }
    
    public HttpResponse(int responseCode, byte[] body) {
        this.responseCode = responseCode;
        this.body = body;
        headers = new ArrayList<>();
    }
    
    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public List<HttpHeader> getHeaders() {
        return Collections.unmodifiableList(headers);
    }
    
    public void addHeader (HttpHeader header) {
        this.headers.add(header);
    }
}
