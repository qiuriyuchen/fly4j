package fly4j.cache;

import fly4j.common.cache.FlyCache;
import fly4j.common.cache.LimitRate;
import fly4j.common.cache.impl.FlyCacheJVM;
import fly4j.common.cache.impl.LimitRateImpl;
import org.junit.Assert;
import org.junit.Test;

public class TestLimitRate {
    @Test
    public void isHotLimit() throws Exception {
        FlyCache flyCache = new FlyCacheJVM(1000);
        LimitRate limitRate = new LimitRateImpl(flyCache, 20, 2);
        for (int i = 0; i < 2; i++) {
            Assert.assertFalse(limitRate.isHotLimit("127.0.0.1"));
        }

        for (int i = 0; i < 2; i++) {
            Assert.assertTrue(limitRate.isHotLimit("127.0.0.1"));
        }
        for (int i = 0; i < 2; i++) {
            Assert.assertFalse(limitRate.isHotLimit("127.0.0.2"));
        }

        Thread.sleep(21);
        for (int i = 0; i < 2; i++) {
            Assert.assertFalse(limitRate.isHotLimit("127.0.0.1"));
        }
    }

    public void demo() {
        FlyCache flyCache = new FlyCacheJVM(1000);
        // 20 is the between time ,2 is the limitNum
        LimitRate limitRate = new LimitRateImpl(flyCache, 20, 2);
        // return is limit
        boolean isHot = limitRate.isHotLimit("127.0.0.1");
    }
}
