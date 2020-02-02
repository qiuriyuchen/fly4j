package fly4j.http;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

/**
 * web层的一些小功能封装
 *
 * @author xxgw
 */
public class WebUtil {
    private static final Log log = LogFactory.getLog(WebUtil.class);

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

    public static int getParameterInt(HttpServletRequest request, String paramName, int defaultValue) {
        return request.getParameter(paramName) != null ? Integer.parseInt(request.getParameter(paramName)) : defaultValue;

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


}