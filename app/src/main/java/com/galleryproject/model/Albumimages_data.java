package com.galleryproject.model;


/**
 * Created by Ahsan Malik on 25/04/2017.
 */
public class Albumimages_data{

    String path,albumname,albumcount;


    public Albumimages_data(String path, String albumname, String albumcount) {
        this.path = path;
        this.albumname = albumname;
        this.albumcount = albumcount;
    }

    public Albumimages_data(String path) {
        this.path = path;
    }


    public String getPath() {
        return path;
    }

    public String getAlbumname() {
        return albumname;
    }

    public String getAlbumcount() {
        return albumcount;
    }
}
