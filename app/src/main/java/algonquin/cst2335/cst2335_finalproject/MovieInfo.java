package algonquin.cst2335.cst2335_finalproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MovieInfo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "title")
    protected String title;

    @ColumnInfo(name = "year")
    protected String year;

    @ColumnInfo(name = "rated")
    protected String rated;

    @ColumnInfo(name = "runtime")
    protected String runtime;

    @ColumnInfo(name = "director")
    protected String director;

    @ColumnInfo(name = "actors")
    protected String actors;

    @ColumnInfo(name = "plot")
    protected String plot;

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getRated() {
        return rated;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getDirector() {
        return director;
    }

    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public MovieInfo(){}

    public MovieInfo(String t,String y, String r, String run, String d, String a, String p ){
        t= title;
        y = year;
        r = rated;
        run = runtime;
        d = director;
        a = actors;
        p = plot;
    }


}
