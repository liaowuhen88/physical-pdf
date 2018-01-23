package com.bean;

/**
 * Created by liaowuhen on 2018/1/23.
 */
public class MyRendItem {
    private float x;
    private float y;
    private String text;
    private int page;

    public MyRendItem(float x, float y, String text, int page) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.page = page;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
