package fr.tom.midyie.ItemsList;

import android.media.Image;

public class Item {

    private String name;
    private String minecraft_id;
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMinecraft_id() {
        return minecraft_id;
    }

    public void setMinecraft_id(String minecraft_id) {
        this.minecraft_id = minecraft_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
