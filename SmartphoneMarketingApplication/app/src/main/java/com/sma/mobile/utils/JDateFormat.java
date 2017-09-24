package com.sma.mobile.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by longtran on 14/02/2017.
 */

public class JDateFormat {

    /****
     *
     * @return
     */
    public static String getCurrentTimeStamp(long timestamp) {
        return new SimpleDateFormat("EEE, MMM d, yyyy").format(new Date(timestamp));
    }
}
