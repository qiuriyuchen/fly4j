package fly4j.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by guanpanpan on 2015/7/24.
 */
@Slf4j
public class JsonUtils {
    public static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
    public static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    }

    public static String writeValueAsString(Object entity) {
        try {
            return objectMapper.writeValueAsString(entity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
            //throw new RepocitoryCallException(RESULT_CODE.ERROR_READ_JSON);

        }
    }

    public static <T> T readValue(String josnStr, Class<T> cls) {
        if (StringUtils.isEmpty(josnStr)) {
            return null;
        }
        try {
            return (T) objectMapper.readValue(josnStr, cls);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static HashMap<String, String> readStringStringHashMap(String md5Str) throws IOException {
        TypeReference<HashMap<String, String>> typeRef
                = new TypeReference<HashMap<String, String>>() {
        };
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(md5Str, typeRef);
    }
}
