package com.BSP.bean;

import java.util.List;

public class Page<T> {
    private int pagenum;//当前页
    private int pagesize;//每页显示的数据条数
    private int amount;//总的数据条数

    private int pageamount;//总的页数，通过amount/pagesize可以算出
    private int startindex;//起始位置的索引

    private List<T> list;//每页数据的集合

    private int start;//起始页和终止页的索引
    private int end;

    public Page(int pagenum,int pagesize,int amount){
        this.pagenum=pagenum;
        this.pagesize=pagesize;
        this.amount=amount;

        if(amount%pagesize==0){//如果总数据条数整除每页显示的条数，则pageamount=amount/pagesize，否则+1
            this.pageamount=amount/pagesize;
        }else{
            this.pageamount=amount/pagesize+1;
        }
        this.startindex=(this.pagenum-1)*pagesize; //(pagenum-1)为了处理数据库页码从0开始而前端页码从1开始的问题

        this.start=1;
        this.end=5;
        if(pageamount<=5){
            this.end=pageamount;
        }else{			//总页数大于5时，根据当前页判断start和end
            this.start=pagenum-2;
            this.end=pagenum+2;
            if(start<0){       //当前为第一或第二页时，start=1，end=5
                this.start=1;
                this.end=5;
            }
            if(end>this.pageamount){     //当前为倒数第一页或倒数第二页时，start和end重新赋值
                this.end=pageamount;
                this.start=end-5;
            }
        }

    }

    public int getPagenum() {
        return pagenum;
    }

    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPageamount() {
        return pageamount;
    }

    public void setPageamount(int pageamount) {
        this.pageamount = pageamount;
    }

    public int getStartindex() {
        return startindex;
    }

    public void setStartindex(int startindex) {
        this.startindex = startindex;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}

