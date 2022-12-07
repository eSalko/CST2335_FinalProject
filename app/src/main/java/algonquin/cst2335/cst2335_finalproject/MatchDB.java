package algonquin.cst2335.cst2335_finalproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {match.class}, version = 1)
abstract class MatchDB extends RoomDatabase {
    public abstract matchDAO mDAO();
}
