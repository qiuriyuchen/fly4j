package fly4j.cache;

import fly4j.cache.impl.FlyCacheJVM;
import fly4j.cache.impl.LimitRateImpl;
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

}
