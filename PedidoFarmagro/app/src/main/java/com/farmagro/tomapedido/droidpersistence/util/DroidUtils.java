package com.farmagro.tomapedido.droidpersistence.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DroidUtils {
    public static Date convertStringToDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertDateToString(Date date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
