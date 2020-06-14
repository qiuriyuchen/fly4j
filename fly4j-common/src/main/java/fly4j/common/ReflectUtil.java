package fly4j.common;


import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

public class ReflectUtil {
    public static Object getPublicFieldValueByFieldName(String fieldName, Object object) {
        try {
            Field field = object.getClass().getField(fieldName);
            return field.get(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getFieldValueByFieldName(String fieldName, Object object) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            throw new RuntimeException("" + object.getClass(), e);
        }
    }

    public static void enhanceSpringSeter(String fieldName, Object object, String value) {
        if (StringUtils.isBlank(value)) {
            return;
        }
        String getGenericType = "";
        try {
            Field field = object.getClass().getField(fieldName);
            getGenericType = field.getGenericType().toString();
            if ("boolean".equals(field.getGenericType().toString()) || Boolean.class.equals(field.getType())) {
                field.set(object, Boolean.valueOf(value));
            } else if ("long".equals(field.getGenericType().toString()) || Long.class.equals(field.getType())) {
                field.set(object, Long.parseLong(value));
            } else if ("int".equals(field.getGenericType().toString()) || Integer.class.equals(field.getType())) {
                field.set(object, Integer.parseInt(value));
            } else {
                field.set(object, value);
            }

        } catch (NoSuchFieldException e) {

        } catch (Exception e) {
            throw new RuntimeException("object " + object + " fieldName " + fieldName + " value " + value + " getGenericType " + getGenericType, e);
        }
    }

    static class TestBean {
        public boolean liBoolean;
        public Boolean bigBoolen;
        public long liLong;
        public Long bigLong;
    }

    public static void main(String[] args) {
        Field[] fields = TestBean.class.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(" fieldName " + field.getName() + " getGenericType " + field.getGenericType().toString() + ":" + field.getType().equals(Long.class));
        }
    }
}
