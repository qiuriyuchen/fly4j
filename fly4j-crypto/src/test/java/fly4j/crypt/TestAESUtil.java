package fly4j.crypt;

import fly4j.crypto.AESUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

/**
 * 生成唯一编号测试
 * UserInfo: guanpanpan
 */
@RunWith(JUnit4ClassRunner.class)
public class TestAESUtil {

    @Before
    public void setup() throws Exception {


    }

    @Test
    public void encryptStringToByte() throws Exception {
        String content = "test中国";
        String password = "12345678";
        // 加密
        byte[] encryptResult = AESUtil.encryptByteToByte(content.getBytes(), password.getBytes());
        // 解密
        byte[] decryptResult = AESUtil.decryptByteToByte(encryptResult, password.getBytes());
        Assert.assertEquals("test中国", new String(decryptResult));


        content = "test中国";
        password = "123sg";
        // 加密
        String encryptResultStr = AESUtil.encryptStr2HexStr(content, password);
        Assert.assertEquals("0EDBEA54308CA2AF16B3EB3D76627975", encryptResultStr);
        // 解密
        String decryptResultStr = AESUtil.decryptHexStrToStr(encryptResultStr, password);
        Assert.assertEquals("test中国", new String(decryptResultStr));


    }

    @Test
    public void testOk2() throws Exception {

    }


    @After
    public void tearDown() throws Exception {
    }
}
