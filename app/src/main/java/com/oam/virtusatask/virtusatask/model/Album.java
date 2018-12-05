package com.oam.virtusatask.virtusatask.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Album extends RealmObject {

    @SerializedName("userId")
    int userId;
    @PrimaryKey
    @SerializedName("id")
    int albumid;
    @SerializedName("title")
    String title;

    public Album() {
    }

    public Album(int userId, int albumid, String title) {
        this.userId = userId;
        this.albumid = albumid;
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getID1() {
        return albumid;
    }

    public void setID1(int albumid) {
        this.albumid = albumid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private static Integer compareAlbum(Album album1, Album album2) {
        return album1.title.compareTo(album2.title);
    }


}
