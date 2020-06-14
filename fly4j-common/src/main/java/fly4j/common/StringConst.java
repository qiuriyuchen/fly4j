package fly4j.common;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 字符串常量
 * Created by guanpanpan on 2017/5/17.
 */
public class StringConst {
    /**
     * 1.关于换行符号
     * \n是换行，英文是New line，表示使光标到行首
     * \r是回车，英文是Carriage return，表示使光标下移一格
     * \r\n表示回车换行
     * 2.文件中的换行
     * linux,unix: \r\n
     * windows : \n
     * Mac OS ： \r
     */
    //br textarea
    public static String BR_TEXTAREA_HTML = "&#xd;";
    public static String N_BR = "<br/>";


    public static void appendLine(StringBuilder stringBuilder, String line) {
        stringBuilder.append(line).append(CharUtils.LF);
    }

    public static String getConsoleTitle(String title) {
        return "*******************************************" + title + "*******************************************";
    }

    public static void main(String[] args) {
        System.out.println("a\nb".replaceAll(StringUtils.LF, N_BR));
    }
}
