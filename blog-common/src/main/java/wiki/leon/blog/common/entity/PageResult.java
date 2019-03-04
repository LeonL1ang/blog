package wiki.leon.blog.common.entity;

import java.util.List;

/***
 *  返回分页数据帮组类
 * @param <T>
 */
public class PageResult <T>{

    private Integer total;
    private List<T> rows;

    public PageResult() {
    }

    public PageResult(Integer total, List<T> rows) {
        super();
        this.total = total;
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
