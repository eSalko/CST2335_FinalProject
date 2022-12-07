package algonquin.cst2335.cst2335_finalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SearchedItem {

    /**
     * id of photo retrieved from Pexels API
     * */
    @PrimaryKey
    @ColumnInfo(name="id")
    protected int id;

    /**
     * BLOB format of image
     * */
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    protected byte[] image;

    /**
     * photographer's name from Pexels API
     * */
    @ColumnInfo(name="photographer")
    protected String photographer;

    /**
     * photo height in pixels from Pexels API
     * */
    @ColumnInfo(name="picHeight")
    protected int height;

    /**
     * photo width in pixels from Pexels API
     * */
    @ColumnInfo(name="picWidth")
    protected int width;

    /**
     * photo url from Pexels API
     * */
    @ColumnInfo(name="url")
    protected String url;


    /**
     * no-args constructor
     * */
    public SearchedItem(){}

    /**
     * constructor to initializes all class variables
     * */
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

    /**
     * Decodes byte array stored in database to be used as Bitmap to be display
     * @return decoded byte array
     * */
    public Bitmap getImagePic() {
        Bitmap bm = BitmapFactory.decodeByteArray(image, 0, image.length);
        return bm;
    }

    /**
     * Accessor method for height
     * @return height of image
     * */
    public int getHeight() {
        return height;
    }

    /**
     * Accessor method for id
     * @return id of image
     * */
    public int getId() {
        return id;
    }

    /**
     * Accessor method for width
     * @return width of image
     * */
    public int getWidth() {
        return width;
    }

    /**
     * Accessor method for photographer
     * @return photographer of image
     * */
    public String getPhotographer() {
        return photographer;
    }

    /**
     * Accessor method for url
     * @return url of image
     * */
    public String getUrl() {
        return url;
    }

    /**
     * Mutator method for
     * @param height height of image
     * */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Mutator method for width
     * @param width width of image
     * */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Mutator method for id
     * @param id id of image
     * */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Mutator method for image
     * @param image converted bitmap to byte array
     * */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     * Mutator method for photographer
     * @param photographer photographer of image
     * */
    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    /**
     * Mutator method for url
     * @param url url of image
     * */
    public void setUrl(String url) {
        this.url = url;
    }


}
