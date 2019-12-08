package com.example.sur.model;

import java.util.ArrayList;

public class Score {
    private ArrayList<Page> pages;

    public Score() {
        pages = new ArrayList<>();
    }

    public ArrayList<Page> getPages() {
        return pages;
    }

    public void setPages(ArrayList<Page> pages) {
        this.pages = pages;
    }

    public void addPage(Page page) {
        this.pages.add(page);
    }

}
