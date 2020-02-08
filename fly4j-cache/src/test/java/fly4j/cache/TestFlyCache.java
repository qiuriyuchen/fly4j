package fly4j.cache;

import fly4j.cache.impl.FlyCacheJVM;
import org.junit.Assert;
import org.junit.Test;

public class TestFlyCache {


    @Test
    public void testCache() throws Exception {
        FlyCache flyCache = new FlyCacheJVM(1000);
        flyCache.put("akey", "avalue", 2);
        Assert.assertEquals("avalue", flyCache.get("akey"));
        Assert.assertTrue(flyCache.ttl("akey") > 0);
        Thread.sleep(3);
        Assert.assertNull(flyCache.get("akey"));
        Assert.assertTrue(flyCache.ttl("akey") < 0);
    }

}
