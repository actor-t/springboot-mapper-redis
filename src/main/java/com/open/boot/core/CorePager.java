package com.open.boot.core;

/**
 * 通用分页对象
 */
public class CorePager {
    //当前页数
    private int pageNum  = 0;

    //每页条数
    private int pageSize  = 0;

    //总条数
    private long totalNum  = 0;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }



}
