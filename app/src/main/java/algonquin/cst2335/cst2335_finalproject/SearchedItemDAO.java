package algonquin.cst2335.cst2335_finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SearchedItemDAO {

    /**
     * Inserts SearchedItem into database
     * */
    @Insert
    void insertItem(SearchedItem i);

    /**
     * Retrieves all SearchedItem objects from database
     * */
    @Query("SELECT * FROM SearchedItem")
    List<SearchedItem> getAllItems();

    /**
     * Deletes SearchedItem from database
     * */
    @Delete
    void deleteItem(SearchedItem i);
}
