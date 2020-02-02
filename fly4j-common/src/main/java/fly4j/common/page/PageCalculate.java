package fly4j.common.page;


/**
 * Created by guanpanpan on 2016/4/28.
 */
public class PageCalculate {

    /**
     * 输入参数
     */

    // 每页大小
    public static final int pageSize = 100;


    // 当前页。第一页是1
    public final int curPage;

    //总记录数
    public final int totalCount;

    /**
     * 根据输入的计算结果
     */
    //1....N
    public final int totalPage;

    // 分页后的记录开始的地方 第一条记录是0-mysql
    public final int startRow;

    public PageCalculate(Integer curPage, int totalCount) {
        if (null == curPage || curPage < 1) {
            curPage = 1;
        }
        if (totalCount <= 0) {
            //如果为空数据
            this.totalCount = 0;
            this.totalPage = 1;
            this.startRow = 0;
            this.curPage = 1;
            return;
        }

        this.curPage = curPage;
        this.totalCount = totalCount;

        //计算总页数
        totalPage = totalCount / pageSize + (totalCount % pageSize > 0 ? 1 : 0);
        //如果当前页大于最大页，设置当前页为最大页码
        if (curPage > totalPage) {
            curPage = totalPage; // 最大页
        }
        //计算开始行
        startRow = (curPage - 1) * pageSize;
    }


}
