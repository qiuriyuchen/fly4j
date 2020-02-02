package fly4j.common.log;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 统一管理特殊的日志文件
 * Created by guanpanpan on 2016/3/23.
 */
public class LogUtil {
    public static final Log log = LogFactory.getLog(LogUtil.class);

    public static void info(Log log, String msg) {
        log.info(msg);
    }


    /**
     * 应用业务日志
     */
    public static void error(String name, String msg) {
        try {
            Log logger = LogFactory.getLog(name);
            if (null != logger) {
                logger.error(msg);
            }
        } catch (Exception exception) {
            log.error("info in log", exception);
        }
    }

    /**
     * log异常信息打印
     */
    public static void error(String name, String msg, Throwable e) {
        try {
            Log logger = LogFactory.getLog(name);
            if (null != logger) {
                logger.error(msg, e);
            }
        } catch (Throwable throwable) {
            log.error("error in log", throwable);
        }

    }

    /**
     * log日志info打印
     *
     * @param name
     * @param msg
     */
    public static void info(String name, String msg) {
        try {
            Log logger = LogFactory.getLog(name);
            if (null != logger) {
                logger.info(msg);
            }
        } catch (Exception e) {
            log.error("info in log", e);
        }
    }
    public static void debug(String name, String msg) {
        try {
            Log logger = LogFactory.getLog(name);
            if (null != logger) {
                logger.debug(msg);
            }
        } catch (Exception e) {
            log.error("info in log", e);
        }
    }
    public static void printTitle(String title) {
        System.out.println("*******************************************" + title + "*******************************************");
    }
}
