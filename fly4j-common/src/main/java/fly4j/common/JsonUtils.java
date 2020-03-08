package fly4j.common;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by guanpanpan on 2015/7/24.
 */
public class JsonUtils {
    private static final Log log = LogFactory.getLog(JsonUtils.class);
    public static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
    public static ObjectMapper objectMapper = new ObjectMapper();
    static{
//        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//        SerializationConfig serConfig = objectMapper.getSerializationConfig();
//        serConfig.setDateFormat(new SimpleDateFormat(DATEFORMAT));
//        serConfig.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
//        DeserializationConfig deserializationConfig = objectMapper.getDeserializationConfig();
//        deserializationConfig.setDateFormat(new DateFormatUtil(DATEFORMAT));
//
//        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
//
//        //for quoted
//        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
//        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
//        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
//        objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
//        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);

    }
    public static String writeValueAsString(Object entity){
        try {
            return objectMapper.writeValueAsString(entity);
        }catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
            //throw new RepocitoryCallException(RESULT_CODE.ERROR_READ_JSON);

        }
    }
    public static <T>  T readValue(String josnStr, Class<T> cls){
        if(StringUtils.isEmpty(josnStr)){
            return null;
        }
        try {
            return (T)objectMapper.readValue(josnStr, cls);
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
