package fly4j.common.test;

import fly4j.common.JsonUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 本地配置测试
 * UserInfo: guanpanpan
 */
public class TestJson {


    private String pin = "testpin";

    @Before
    public void setup() throws Exception {


    }


    static private class TestJsonO {
        private String priStr;
        public String pubStr;

        public TestJsonO(String priStr, String pubStr) {
            this.priStr = priStr;
            this.pubStr = pubStr;
        }
    }

    @Test
    public void writeValueAsString2() throws Exception {
        TestJsonO testJsonO = new TestJsonO("priStr", "pubStr");
        String jsonStr = JsonUtils.writeValueAsString(testJsonO);
//		System.out.println(jsonStr);
        Assert.assertEquals("{\"pubStr\":\"pubStr\"}", jsonStr);

    }

    @After
    public void tearDown() throws Exception {

    }
}
