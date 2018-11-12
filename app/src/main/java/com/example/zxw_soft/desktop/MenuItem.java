package com.example.zxw_soft.desktop;

/**
 * Created by zxw_soft on 2018/11/5.
 */

public class MenuItem {

    private String MenuName ;
    private  int    MenuImageId;

    public MenuItem(String name, int imageId) {
        this.MenuName = name;
        this.MenuImageId = imageId;
    }

    public String getName() {
        return MenuName;
    }

    public int getImageId() {
        return MenuImageId;
    }
}
