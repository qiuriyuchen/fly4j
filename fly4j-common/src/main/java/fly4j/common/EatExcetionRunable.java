package fly4j.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程池
 * Created by guanpanpan on 2018-01-11.
 */
@FunctionalInterface
public interface EatExcetionRunable extends Runnable {
    static Logger log = LoggerFactory.getLogger(EatExcetionRunable.class);

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
