package fly4j.common;


import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class FlyPreconditions {
    public static <T> T requireNotEmpty(T obj, String message) {
        if (obj == null) {
            throw new NullPointerException(message);
        }
        if (obj instanceof String) {
            if (StringUtils.isBlank((String) obj)) {
                throw new IllegalArgumentException(message);
            }
        }
        return obj;
    }

    public static <T> T requireNotEmpty(T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        if (obj instanceof String) {
            if (StringUtils.isBlank((String) obj)) {
                throw new IllegalArgumentException();
            }
        }
        return obj;
    }

    public static void checkArgumentFalse(boolean expression, String errorMsg) {
        if (!expression) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    public static void checkStateTrue(boolean expression, String errorMsg) {
        if (expression) {
            throw new IllegalStateException(errorMsg);
        }
    }
}
