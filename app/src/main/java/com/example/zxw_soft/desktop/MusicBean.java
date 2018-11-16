package com.example.zxw_soft.desktop;

/**
 * Created by zxw_soft on 2018/11/15.
 */

public class MusicBean {
    /*
    * 工欲善其事必先利其器，數據集合要安排好
    * */
    private int id ;
    private String title;
    private String artist;
    private String url;
    private String time;
    private String size;
    private int albumId;
    private String album;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSize() {
        return size;
    }

    /*
    * size 大小進行處理。
    * */
    public void setSize(Long size) {
        long kb = 1024;
        long mb = kb*1024;
        long gb = mb*1024;
        if (size >= gb) {
            this.size = String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            this.size = String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            this.size = String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            this.size = String.format("%d B", size);
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    //格式化時間
    public void setTime(int time){
        time /= 1000;  //毫秒 轉換成秒
        int minute = time / 60;
        int hour =minute / 60;
        int second =time % 60;
        minute %= 60;
        this.time =String.format("%02d:%02d", minute, second);
    }



}
