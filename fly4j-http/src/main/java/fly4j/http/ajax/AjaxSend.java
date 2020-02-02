package fly4j.http.ajax;

import fly4j.http.WebUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@FunctionalInterface
public interface AjaxSend {
    static final Log log = LogFactory.getLog(AjaxSend.class);

    default public void doAjax(HttpServletRequest req, HttpServletResponse resp) {
        PrintWriter writer = WebUtil.getPrintWriter(resp);
        try {
            if (!"XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) {
                throw new IllegalStateException("not Ajax Request");
            }
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
