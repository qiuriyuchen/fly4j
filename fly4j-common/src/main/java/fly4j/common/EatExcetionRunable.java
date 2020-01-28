package fly4j.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 线程池
 * Created by guanpanpan on 2018-01-11.
 */
@FunctionalInterface
public interface EatExcetionRunable extends Runnable {
    Log log = LogFactory.getLog(EatExcetionRunable.class);

    @Override
    default void run() {
        try {
            this.runNoExcetion();
        } catch (Exception e) {
            log.error("run Exception", e);
        }
    }

    void runNoExcetion();
}
