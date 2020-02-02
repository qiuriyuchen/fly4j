package fly4j.http.ajax;

import fly4j.http.WebUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@FunctionalInterface
public interface FetchSend {
    static final Log log = LogFactory.getLog(FetchSend.class);

    default public void doAjax(HttpServletRequest req, HttpServletResponse resp) {
        PrintWriter writer = WebUtil.getPrintWriter(resp);
        try {
            writer.write(this.getAjaxPrintStr());
        } catch (Exception e) {
            writer.write(e.getMessage());
        } finally {
            if (null != writer) {
                writer.close();
            }
        }

    }


    public abstract String getAjaxPrintStr();
}
