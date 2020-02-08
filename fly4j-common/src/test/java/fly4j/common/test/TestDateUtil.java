package fly4j.common.test;

import fly4j.common.DateUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * @author guanpanpan
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class TestDateUtil {

    @Before
    public void setup() throws Exception {

    }

    @Test
    public void updateBlog() throws Exception {
        Assert.assertEquals(0, DateUtil.getBetweenDay("2015-05-14", "2015-05-14"));
        assertEquals(13, DateUtil.getBetweenDay("2015-05-01", "2015-05-14"));
        assertEquals(13, DateUtil.getBetweenDay("2015-05-14", "2015-05-01"));
    }

    @After
    public void tearDown() throws Exception {
    }
}
