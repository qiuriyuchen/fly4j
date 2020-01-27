package fly4j.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qryc on 2018/12/8.
 */
public class MsgResult {

    public List<String> msgResult = new ArrayList<>();

    @Override
    public String toString() {
        return HtmlConvert.toRedSpan(msgResult, "black");
    }

    public MsgResult append(Object msg) {
        msgResult.add(msg.toString());
        return this;
    }

    public MsgResult appendLine(Object msg) {
        int lastIndex = msgResult.size() - 1;
        msgResult.set(lastIndex, msgResult.get(lastIndex) + msg);
        return this;
    }
}
