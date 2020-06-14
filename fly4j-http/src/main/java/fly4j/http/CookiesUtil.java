package fly4j.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by guanpanpan on 2015/8/19.
 */
@Slf4j
final public class CookiesUtil {
    static DateFormat df = new SimpleDateFormat("EEE, d-MMM-yyyy HH:mm:ss z", Locale.US);
    public static final String COOKIE_PATH = "/";

    /**
     * 解码并得到cookies的值
     *
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValueDecode(HttpServletRequest request, String cookieName) {
        var cookieValue = getCookieValue(request, cookieName);
        if (StringUtils.isBlank(cookieValue)) {
            return cookieValue;
        }
        try {
            return URLDecoder.decode(cookieValue, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("getCookieValueDecode", e);
        }


    }

    /**
     * 从requst中得到cookies的值，cookies不存在返回空串
     *
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        var cookies = request.getCookies();
        if (cookies == null)
            return null;
        for (var c : cookies) {
            if (c.getName().equals(cookieName)) {
                return c.getValue();
            }
        }
        return "";
    }


    /**
     * 清除cookie
     *
     * @param cookieName
     * @param request
     * @param response
     */
    public static void clearCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        var domain = WebUtil.getDomain(request);
        if (org.apache.commons.lang.StringUtils.isEmpty(cookieName)) {
            return;
        }

        var cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (var cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    cookie.setMaxAge(0);
                    cookie.setValue(null);
                    cookie.setPath("/");
                    cookie.setDomain(domain);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }

    /**
     * 添加cookies
     *
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxAge
     */
    public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, int cookieMaxAge, boolean httpOnly) {
        // HttpOnly
        StringBuilder cookieStr = new StringBuilder(128);
        cookieStr.append(cookieName).append("=").append(cookieValue).append(";");
        if (cookieMaxAge >= 0) {
            Calendar cal = Calendar.getInstance(Locale.US);
            cal.add(Calendar.SECOND, cookieMaxAge);
            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            String expires = df.format(cal.getTime());
            if (cookieMaxAge == 0) { // 清除cookie
                expires = "Thu, 01-Jan-1970 00:00:10 GMT";
            }
            cookieStr.append(" Expires=").append(expires).append(";");
        }

        cookieStr.append(" Path=/;");
        if (httpOnly) {
            cookieStr.append(" HttpOnly");
        }
        response.addHeader("Set-Cookie", cookieStr.toString());
    }


    public static void addHttpOnlyCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, int cookieMaxAge) {
        String domain = WebUtil.getDomain(request);
        // 单点登录跨域用的 获取持久化cookie
        response.setHeader("P3P",
                "CP=\"CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR\"");
        // HttpOnly
        StringBuilder cookieStr = new StringBuilder(380);
        cookieStr.append(cookieName).append("=").append(cookieValue).append(";");
        cookieStr.append(" Domain=").append(domain).append(";");
        if (cookieMaxAge >= 0) {
            Calendar cal = Calendar.getInstance(Locale.US);
            cal.add(Calendar.SECOND, cookieMaxAge);
            DateFormat df = new SimpleDateFormat("EEE, d-MMM-yyyy HH:mm:ss z", Locale.US);
            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            String expires = df.format(cal.getTime());
            if (cookieMaxAge == 0) { // 清除cookie
                expires = "Thu, 01-Jan-1970 00:00:10 GMT";
            }
            cookieStr.append(" Expires=").append(expires).append(";");
        }
        cookieStr.append(" Path=").append(COOKIE_PATH);
        cookieStr.append(";");
        cookieStr.append(" HttpOnly");

        response.addHeader("Set-Cookie", cookieStr.toString());
    }

    /**
     * 使用javascript方式生成 cookie
     *
     * @return
     */
    public static String addCookieUseJsWithEncry(String name, String value) {

        if (StringUtils.isBlank(name) || StringUtils.isBlank(value)) {
            return null;
        }

        try {
            return "<script type='text/javascript'>document.cookie='" + name + "=" + value + "; Path=/;'</script>";
        } catch (Exception e) {
            return null;
        }
    }

    public static void addCookieWithEncryWithoutHttpOnly(HttpServletResponse response, String cookieName, String cookieValue,
                                                         int cookieMaxAge) {

        try {
            // HttpOnly
            StringBuilder cookieStr = new StringBuilder(128);
            cookieStr.append(cookieName).append("=").append(cookieValue).append(";");
            if (cookieMaxAge >= 0) {
                Calendar cal = Calendar.getInstance(Locale.US);
                cal.add(Calendar.SECOND, cookieMaxAge);
                df.setTimeZone(TimeZone.getTimeZone("GMT"));
                String expires = df.format(cal.getTime());
                if (cookieMaxAge == 0) { // 清除cookie
                    expires = "Thu, 01-Jan-1970 00:00:10 GMT";
                }
                cookieStr.append(" Expires=").append(expires).append(";");
            }

            cookieStr.append(" Path=/;");
            response.addHeader("Set-Cookie", cookieStr.toString());
        } catch (Exception e) {
            log.error("--addCookieWithEncry error--", e);
        }
    }

    public static void addHttpOnlyCookieEncode(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, int cookieMaxAge) {
        try {
            if (null != cookieValue)
                cookieValue = URLEncoder.encode(cookieValue, "utf-8");
            addHttpOnlyCookie(request, response, cookieName, cookieValue, cookieMaxAge);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    public static void addNotHttpOnlyCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue,
                                            int cookieMaxAge) {
        String domain = WebUtil.getDomain(request);
        Cookie newCookie = new Cookie(cookieName, cookieValue);
        newCookie.setMaxAge(cookieMaxAge);
        newCookie.setDomain(domain);
        newCookie.setPath(COOKIE_PATH);
        response.addCookie(newCookie);
        return;
    }
}
