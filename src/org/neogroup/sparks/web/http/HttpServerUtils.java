
package org.neogroup.sparks.web.http;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class HttpServerUtils {

    private static final String SERVER_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";

    private static DateFormat dateFormatter;

    static {
        dateFormatter = new SimpleDateFormat(SERVER_DATE_FORMAT);
    }

    public static final String formatDate (Date date) {
        return dateFormatter.format(date);
    }

    public static final Date getDate (String dateString) throws ParseException {
        return dateFormatter.parse(dateString);
    }
}