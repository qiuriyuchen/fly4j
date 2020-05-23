package fly4j.crypt;

import fly4j.common.crypto.ByteUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import java.nio.charset.Charset;

/**
 * @author guanpanpan
 */
@RunWith(JUnit4ClassRunner.class)
public class TestByteUtil {

    @Before
    public void setup() throws Exception {

    }

    @Test
    public void hexOpration() throws Exception {
        Assert.assertTrue(ByteUtil.isHexStr("73C58BAFE578C59366D8C995CD0B9D6D"));
        Assert.assertFalse(ByteUtil.isHexStr("73C58BAFE578C59366D8C995CD0大B9D6D"));
        Assert.assertFalse(ByteUtil.isHexStr("73C58BAFE578C59366D8C995CD0B9wD6D"));

        String hexStr = ByteUtil.parseByte2HexStr("abc中国".getBytes(Charset.forName("utf-8")));
        Assert.assertEquals("616263E4B8ADE59BBD", hexStr);
        String str = new String(ByteUtil.parseHexStr2Byte(hexStr), Charset.forName("utf-8"));
        Assert.assertEquals("abc中国", str);

    }

    @After
    public void tearDown() throws Exception {
    }
}
