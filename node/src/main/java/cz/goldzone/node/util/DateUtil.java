package cz.goldzone.node.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd-kk:mm:ss");

    public static String getNow() {
        Date d = new Date();
        return format.format(d);
    }

    public static String get30days() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(5, 30);
        return format.format(cal.getTime());
    }

    public static boolean isPurget(String DateEx) {
        Date date = new Date();
        Date date1 = null;
        boolean expire = false;
        try {
            date1 = (new SimpleDateFormat("yyyy-MM-dd")).parse(DateEx);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date.before(date1)) {
            expire = false;
        } else {
            expire = true;
        }
        return expire;
    }
}
