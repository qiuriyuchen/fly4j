package fly4j.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * web层的一些小功能封装
 *
 * @author xxgw
 */
@Slf4j
public class WebUtil {

    public static PrintWriter getPrintWriter(HttpServletResponse resp) {
        PrintWriter writer = null;
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=utf-8");
        try {
            writer = resp.getWriter();
        } catch (Exception e) {
        }
        return writer;
    }

    public static boolean getParameterBoolean(HttpServletRequest request, String paramName) {
        String value = request.getParameter(paramName);
        return "true".equals(value);

    }

    public static String getParameterStr(HttpServletRequest request, String paramName, String defaultValue) {
        return request.getParameter(paramName) != null ? request.getParameter(paramName) : defaultValue;

    }

    public static String getParameterStr(HttpServletRequest request, String paramName) {
        return request.getParameter(paramName);

    }

    public static boolean equalsParameter(HttpServletRequest request, String paramName, String compValue) {
        return (request.getParameter(paramName) != null)
                && (request.getParameter(paramName).equals(compValue));

    }

    public static Integer getParameterInt(HttpServletRequest request, String paramName, Integer defaultValue) {
        return request.getParameter(paramName) != null ? Integer.valueOf(request.getParameter(paramName)) : defaultValue;
    }

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static boolean isFromPhone(HttpServletRequest req) {
        String userAgent = req.getHeader("user-agent").toLowerCase();
        if (userAgent.contains("iphone"))
            return true;
        if (userAgent.toLowerCase().contains("android"))
            return true;
        return false;
    }

    public static boolean isFromPc(HttpServletRequest req) {
        return !isFromPhone(req);
    }


    public static void sendRedirect(HttpServletResponse resp, String returnUrl) {
        try {
            resp.sendRedirect(URLDecoder.decode(returnUrl, "utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDomain(HttpServletRequest req) {
        String urlStr = req.getRequestURL().toString();
        URL url = null;
        try {
            url = new URL(urlStr);
            String host = url.getHost();
            return host;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";

    }

    public static String getDomainUrl(HttpServletRequest req) {
        StringBuilder domainUrl = new StringBuilder();
        domainUrl.append(getProtocol(req)).append("://").append(getDomain(req));
        int port = getPort(req);
        if (port >= 80) {
            domainUrl.append(":").append(port);
        }
        domainUrl.append("/");
        return domainUrl.toString();
    }

    public static String getProtocol(HttpServletRequest req) {
        String urlStr = req.getRequestURL().toString();
        URL url = null;
        try {
            url = new URL(urlStr);
            String host = url.getProtocol();
            return host;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";

    }

    public static Integer getPort(HttpServletRequest req) {
        String urlStr = req.getRequestURL().toString();
        URL url = null;
        try {
            url = new URL(urlStr);
            return url.getPort();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;

    }
}
