package algonquin.cst2335.cst2335_finalproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {SearchedItem.class}, version=1, exportSchema = false)
abstract class ItemDatabase extends RoomDatabase {
    /**
     * Method to initialize database
     * */
    public abstract SearchedItemDAO siDAO();
}
