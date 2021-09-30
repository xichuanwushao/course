package com.xichuan.server.req;

public class SortReq {
    /***
     * ID
     */
    private String id;

    /***
     * 当前排序
     */
    private int oldSort;

    /***
     * 新排序
     */
    private int newSort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOldSort() {
        return oldSort;
    }

    public void setOldSort(int oldSort) {
        this.oldSort = oldSort;
    }

    public int getNewSort() {
        return newSort;
    }

    public void setNewSort(int newSort) {
        this.newSort = newSort;
    }

    @Override
    public String toString() {
        return "SortReq{" +
                "id='" + id + '\'' +
                ", oldSort=" + oldSort +
                ", newSort=" + newSort +
                '}';
    }
}
