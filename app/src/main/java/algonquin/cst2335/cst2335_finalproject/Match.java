package algonquin.cst2335.cst2335_finalproject;

import android.content.Context;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Match {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "title")
    private String matchTitle;

    @ColumnInfo(name = "date")
    private String matchDate;

    @ColumnInfo(name = "competition")
    private String comp;

    @ColumnInfo(name = "url")
    private String url;

    @ColumnInfo(name = "fav")
    private boolean fav;

    public Match() {
        //default empty constructor
    }


    public Match(String matchTitle, String matchDate, String comp, String url, boolean fav){
        this.matchTitle = matchTitle;
        this.matchDate = matchDate;
        this.comp = comp;
        this.url = url;
        this.fav = fav;
    }

    public String getMatchTitle() {
        return matchTitle;
    }

    public void setMatchTitle(String matchTitle) {
        this.matchTitle = matchTitle;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getComp() {
        return comp;
    }

    public void setComp(String comp) {
        this.comp = comp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isFav() {return fav;}

    public void setFav(boolean fav) { this.fav = fav; }
}
