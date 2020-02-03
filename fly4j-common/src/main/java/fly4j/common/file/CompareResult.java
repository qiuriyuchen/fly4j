package fly4j.common.file;

import fly4j.common.DateUtil;
import fly4j.common.HtmlConvert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by qryc on 2018/12/1.
 */
public class CompareResult {
    public static volatile boolean run = false;
    public Date startDate = new Date();
    public int oneDirCount = 0;
    public int totalCount = 0;
    public int sameCount = 0;
    public int diffCount = 0;
    public List<String> headResult = new ArrayList<>();
    public List<String> msgResult = new ArrayList<>();

    public boolean isSame() {
        if (this.totalCount == this.sameCount && this.diffCount == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void reSet() {
        startDate = new Date();
        oneDirCount = 0;
        totalCount = 0;
        sameCount = 0;
        diffCount = 0;
        headResult = new ArrayList<>();
        msgResult = new ArrayList<>();

    }

    public void merge(CompareResult mergeCp, String dir2) {
        if (mergeCp.isSame()) {
            headResult.add(dir2 + "对比OK");
        } else {
            headResult.add(dir2 + "对比Error");
        }
        totalCount += mergeCp.totalCount;
        sameCount += mergeCp.sameCount;
        diffCount += mergeCp.diffCount;
        msgResult.addAll(mergeCp.msgResult);
    }

    public String toHtml() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("run:").append(run).append("  ").append(DateUtil.format(startDate)).append("<br/>");
        if (isSame()) {
            stringBuilder.append("<span style=\"color:green\">OK</span>").append("<br/>");
        } else {
            stringBuilder.append("<span style=\"color:red\">ERROR</span>").append("<br/>");
        }
        stringBuilder.append("totalCount:").append(totalCount).append("<br/>");
        stringBuilder.append("sameCount:").append(sameCount).append("<br/>");
        stringBuilder.append("diffCount:").append(diffCount).append("<br/>");
        stringBuilder.append(HtmlConvert.toRedSpan(headResult, "black"));
        if (!isSame()) {
            stringBuilder.append(HtmlConvert.toRedSpan(msgResult, "red"));
        }
        return stringBuilder.toString();
    }


}
