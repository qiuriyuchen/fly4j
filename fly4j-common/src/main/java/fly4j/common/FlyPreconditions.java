package fly4j.common;

import org.apache.commons.lang.StringUtils;

public class FlyPreconditions {
    public static <T> T checkNotNull(T reference, String errorMsg) {
        if (null == reference) {
            throw new NullPointerException(errorMsg);
        }
        if (reference instanceof String) {
            if (StringUtils.isBlank((String) reference)) {
                throw new IllegalArgumentException(errorMsg);
            }
        }
        return reference;
    }

    public static <T> T checkNotNull(T reference) {
        if (null == reference) {
            throw new NullPointerException();
        }
        if (reference instanceof String) {
            if (StringUtils.isBlank((String) reference)) {
                throw new IllegalArgumentException();
            }

        }
        return reference;
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
