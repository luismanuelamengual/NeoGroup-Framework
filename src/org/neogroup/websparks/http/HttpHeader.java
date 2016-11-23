
package org.neogroup.websparks.http;

public interface HttpHeader {

    //Header names
    String ACCEPT = "Accept";
    String ACCEPT_CHARSET = "Accept-Charset";
    String ACCEPT_ENCODING = "Accept-Encoding";
    String ACCEPT_LANGUAGE = "Accept-Language";
    String AUTHORIZATION = "Authorization";
    String EXPECT = "Expect";
    String FROM = "From";
    String HOST = "Host";
    String IF_MATCH = "If-Match";
    String IF_MODIFIED_SINCE = "If-Modified-Since";
    String IF_NONE_MATCH = "If-None-Match";
    String IF_RANGE = "If-Range";
    String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
    String MAX_FORWARDS = "Max-Forwards";
    String PROXY_AUTHORIZATION = "Proxy-Authorization";
    String RANGE = "Range";
    String REFERER = "Referer";
    String TE = "TE";
    String USER_AGENT = "User-Agent";
    String CONTENT_TYPE = "Content-Type";
    String CONTENT_LENGTH = "Content-Length";
    String CONTENT_ENCODING = "Content-Encoding";
    String SERVER = "Server";
    String DATE = "Date";
    String LAST_MODIFIED = "Last-Modified";
    String CONNECTION = "Connection";
    String LOCATION = "Location";
    String VARY = "Vary";

    //Header Values
    String GZIP_CONTENT_ENCODING = "gzip";
    String APPLICATION_FORM_URL_ENCODED = "application/x-www-form-urlencoded";
}
