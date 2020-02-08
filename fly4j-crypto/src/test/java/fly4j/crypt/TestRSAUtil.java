package fly4j.crypt;

import fly4j.crypto.RSAUtils;
import fly4j.crypto.RsaKey;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

/**
 * 生成唯一编号测试
 * UserInfo: guanpanpan
 */
@RunWith(JUnit4ClassRunner.class)
public class TestRSAUtil {

    @Before
    public void setup() throws Exception {


    }

    @Test
    public void encryptStringToByte() throws Exception {
        RsaKey rsaKey = RSAUtils.getKeys();
        //明文
        String ming = "123456789";
        //使用模和指数生成公钥和私钥
        System.out.println("pub_modulus:" + rsaKey.pub_modulus);
        System.out.println("pri_modulus:" + rsaKey.pri_modulus);
        System.out.println("公钥:" + rsaKey.pubKey);
        System.out.println("私钥:" + rsaKey.priKey);
        //加密后的密文
        String mi = RSAUtils.encryptByPublicKey(ming, rsaKey.pub_modulus, rsaKey.pubKey);
        System.err.println("加密后:" + mi);
        //解密后的明文
        ming = RSAUtils.decryptByPrivateKey(mi, rsaKey.pri_modulus, rsaKey.priKey);
        System.err.println("解密后:" + ming);

    }

    @After
    public void tearDown() throws Exception {
    }
}
