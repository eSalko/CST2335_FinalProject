package algonquin.cst2335.cst2335_finalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SearchedItem {

    @PrimaryKey
    @ColumnInfo(name="id")
    protected int id;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    protected byte[] image;

    @ColumnInfo(name="photographer")
    protected String photographer;

    @ColumnInfo(name="picHeight")
    protected int height;

    @ColumnInfo(name="picWidth")
    protected int width;

    @ColumnInfo(name="url")
    protected String url;


    public SearchedItem(){}

    public SearchedItem(int id, byte[] image, String photographer, int height, int width, String url){
        this.id = id;
        this.image = image;
        this.photographer = photographer;
        this.height = height;
        this.width = width;
        this.url = url;
    }

    public byte[] getImage() {
        return image;
    }

    public Bitmap getImagePic() {
        Bitmap bm = BitmapFactory.decodeByteArray(image, 0, image.length);
        return bm;
    }

    public int getHeight() {
        return height;
    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public String getPhotographer() {
        return photographer;
    }

    public String getUrl() {
        return url;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
