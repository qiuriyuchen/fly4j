package fly4j.common;

import java.util.List;

/**
 * Created by qryc on 2018/12/8.
 */
public class HtmlConvert {

    public static String toRedSpan(List<String> msgResult, String color) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String diff : msgResult) {
            stringBuilder.append("<span style=\"color:" + color + "\">").append(diff).append("</span>").append("<br/>");
        }
        return stringBuilder.toString();
    }
}
