package fly4j.common.os;

/**
 * 封装对操作系统相关命令，比如dump数据库
 * Created by guanpanpan on 2015/9/10.
 */
public class OsUtil {
    public static boolean isLinuxLike() {
        return !isWindows();
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().startsWith("windows");
    }

    public static boolean isLinuxReal() {
        return System.getProperty("os.name").toLowerCase().startsWith("linux");
    }

    public static boolean isMac() {
        return System.getProperty("os.name").toLowerCase().startsWith("mac");
    }

    public static String getSimpleOsName() {
        if (isWindows()) {
            return "window";
        } else if (isMac()) {
            return "mac";
        } else if (isLinuxReal()) {
            return "linux";
        } else {
            return "linuxLike";
        }

    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("os.name"));
        System.out.println(OsUtil.isMac());
    }
}
