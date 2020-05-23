package fly4j.common.test;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fly4j.common.JsonUtils;
import lombok.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地配置测试
 * UserInfo: guanpanpan
 */
public class TestJson {


    @Before
    public void setup() throws Exception {
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class User {
        private String name;
        private int age;
        @JsonIgnore
        private String remark;
    }

    @Test
    public void writeValueAsString2() throws Exception {

        User user = new User("pin1", 20,"sdf");
        String jsonStr = JsonUtils.writeValueAsString(user);
        Assert.assertEquals("{\"name\":\"pin1\",\"age\":20}", jsonStr);
        user= JsonUtils.readValue("{\"name\":\"pin1\",\"age\":20,\"age2\":20}", User.class);
        Assert.assertEquals("pin1", user.getName());
        Assert.assertEquals(20, user.getAge());

        //Map
        Map<String,String> map=new HashMap<>();
        map.put("a","aa");
        jsonStr=JsonUtils.writeValueAsString(map);
        Assert.assertEquals("{\"a\":\"aa\"}", jsonStr);
        map=JsonUtils.readStringStringHashMap(jsonStr);
        Assert.assertEquals(1, map.size());
        Assert.assertEquals("aa", map.get("a"));

    }

    @After
    public void tearDown() throws Exception {

    }
}
