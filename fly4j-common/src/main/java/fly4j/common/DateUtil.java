package fly4j.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

final public class DateUtil {
    public static final int HOUR = 1000 * 60 * 60 * 24;

    public static void main(String[] args) throws Exception {
        System.out.println((TimeUnit.SECONDS.toHours(43200)) + "小时");
        System.out.println((TimeUnit.SECONDS.toDays(1296000)) + "天");
    }


    public static long differSecondFromNow(Date begin) {
        Date now = new Date();
        long between = (now.getTime() - begin.getTime()) / 1000;//
        return Math.abs(between);
    }

    public static String getCurrDateStr() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    public static String getDateStr(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }
    // ////////////////////////////////date转字符串

    /**
     * 得到天的字符串
     *
     * @param date
     * @return
     */
    public static String getDayStr(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }
    public static String getYearStr(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy");
        return df.format(date);
    }
    public static String getDateStrForName(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss");
        return df.format(date);
    }

    public static String getDateHourStr(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
        return df.format(date);
    }

    public static String formatStrinCommon(Date date) {
        if (null == date) {
            return "null";
        }
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return df.format(date);
    }

    public static String format(Date date) {
        if (null == date) {
            return "null";
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

    public static String displayMilliSeconds(long milliSeccond) {
        long seconds = milliSeccond / 1000;
        if (seconds < 60) {
            //一分钟内
            return seconds + "秒";
        } else if (seconds < 60 * 60) {
            //一小时内
            return seconds / 60 + "分" + seconds % 60 + "秒";
        } else {
            return seconds / 60 / 60 + "小时" + seconds / 60 % 60 + "分" + seconds % 60 + "秒";
        }
    }

    // ////////////////////////////////字符串转date
    public static Date getDate(String dateStr) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date begin = null;
        try {
            begin = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return begin;
    }

    public static Date getDate2(String dateStr) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date begin = null;
        try {
            begin = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return begin;
    }

    public static Date getDateDay(String dateStr) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date begin = null;
        try {
            begin = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return begin;
    }

    public static int getBetweenDay(String dayStr1, String dayStr2) {
        Date date = DateUtil.getDateDay(dayStr1);
        Date date2 = DateUtil.getDateDay(dayStr2);
        long between = (date.getTime() - date2.getTime()) / 1000 / 3600 / 24;//
        return Math.abs((int) between);
    }

    public static int getBetweenDayForNow(String dayStr1) {
        Date date = DateUtil.getDateDay(dayStr1);
        Date date2 = new Date();
        long between = (date.getTime() - date2.getTime()) / 1000 / 3600 / 24;//
        return Math.abs((int) between);
    }

    public static void sleepForOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleepForFiveMinute(int times) {
        try {
            Thread.sleep(1000 * 60 * times);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int getCurrHour() {

        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }
}
