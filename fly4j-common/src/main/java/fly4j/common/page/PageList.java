package fly4j.common.page;

import java.util.ArrayList;

/**
 * 分页记录的母体的
 */
public class PageList<T> extends ArrayList<T> {

    // 当前页。第一页是1
    public final int curPage;

    //总页数
    public final int totalPage;

    public PageList(int curPage, int totalPage) {
        this.curPage = curPage;
        this.totalPage = totalPage;
    }

    /**
     * 表示是不是第一页
     */
    public boolean isFirstPage() {
        return curPage <= 1;
    }

    public boolean isMiddlePage() {
        return !(isFirstPage() || isLastPage());
    }

    public boolean isLastPage() {
        return curPage >= totalPage;
    }

    public boolean isNextPageAvailable() {
        return !isLastPage();
    }

    public boolean isPreviousPageAvailable() {
        return !isFirstPage();
    }

    /**
     * 下一页号
     */
    public int getNextPage() {
        if (isLastPage()) {
            return totalPage;
        } else {
            return curPage + 1;
        }
    }

    public int getPreviousPage() {
        if (isFirstPage()) {
            return 1;
        } else {
            return curPage - 1;
        }
    }


}
