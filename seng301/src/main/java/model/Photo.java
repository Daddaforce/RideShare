package model;

import controllers.Main;

/**
 * Created by Chris on 26/05/2017.
 */
public class Photo {

    private byte[] photo = {};
    private String username;

    public void setPhoto(byte[] photo, String username) {
        this.photo = photo;
        this.username = username;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public String getUsername() {
        return username;
    }
}
