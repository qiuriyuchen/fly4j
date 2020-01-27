package fly4j.common;

/**
 * Created by qryc on 2018/12/9.
 */
public class IkString {
    public static String getPlanText(String str) {
        /**
         * [任意]
         * ^不是
         * +多次，正则表达式9+匹配9、99、999等
         */
        return str.replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5-_]+", "");
    }

    public static void main(String[] args) {
        System.out.println(IkString.getPlanText("a  大&.，,。？“”…0…%￥*&）..小"));
    }
}
