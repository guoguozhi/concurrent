package com.guoguozhi.waitnotify;

// 快递类
public class Express {
    // 起点
    private String start;
    // 终点
    private String end;
    // 总里程数
    private float totalKilometres;
    // 当前里程数
    private float currentKilometres;
    // 当前位置
    private String currentSite;

    public Express(){
        super();
    }

    public Express(String start, String end, float totalKilometres){
        this.start = start;
        this.end = end;
        this.totalKilometres = totalKilometres;
        this.currentKilometres = 0;
        this.currentSite = this.start;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public float getTotalKilometres() {
        return totalKilometres;
    }

    public float getCurrentKilometres() {
        return currentKilometres;
    }

    public String getCurrentSite() {
        return currentSite;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setTotalKilometres(float totalKilometres) {
        this.totalKilometres = totalKilometres;
    }

    public void setCurrentKilometres(float currentKilometres) {
        this.currentKilometres = currentKilometres;
    }

    public void setCurrentSite(String currentSite) {
        this.currentSite = currentSite;
    }
}