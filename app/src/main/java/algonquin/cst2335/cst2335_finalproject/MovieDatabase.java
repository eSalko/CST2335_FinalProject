package algonquin.cst2335.cst2335_finalproject;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MovieInfo.class},version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao mDao();
}
