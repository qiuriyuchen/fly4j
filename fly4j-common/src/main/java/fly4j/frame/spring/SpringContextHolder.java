package fly4j.frame.spring;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author guanpanpan
 */
@Slf4j
public class SpringContextHolder implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextHolder.applicationContext = applicationContext;
        log.info("setApplicationContext:" + applicationContext);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 调用者小心死循环,本程序只在程序启动服务时调用，程序运行中不调用
     */
    public static ApplicationContext getApplicationContextWithSleep() {
        while (applicationContext == null) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return applicationContext;
    }

    /**
     * 调用者小心死循环
     */
    public static <T> T getBeanWithSleep(String beanName) {
        return (T) getApplicationContextWithSleep().getBean(beanName);
    }

    public static <T> T getBean(String beanName) {
        return (T) getApplicationContext().getBean(beanName);
    }

    public static <T> List<T> getBeans(String filterNames) {
        List<T> filterList = new ArrayList<>();
        for (var filterName : filterNames.split(",")) {
            T filter = SpringContextHolder.getBean(filterName);
            filterList.add(filter);
        }
        return filterList;
    }
}
