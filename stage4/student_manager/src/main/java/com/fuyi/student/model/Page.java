package com.fuyi.student.model;

import java.util.List;

public class Page<T> {

    private  int totalCount;//总记录条数
    private  int totalPage;//总页数
    private List<T> list;//存放每页的数据
    private  int currentPage;//当前页码
    private  int rows;// 每页显示的记录数

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }


    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "Page{" +
                "totalCount='" + totalCount + '\'' +
                ", totalPage='" + totalPage + '\'' +
                ", list=" + list +
                ", currentPage=" + currentPage +
                ", rows=" + rows +
                '}';
    }
}
