package algonquin.cst2335.cst2335_finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface matchDAO {

    @Insert
    public void insertMatch(match m);

    @Query("SELECT * FROM `match`")
    public List<match> getAllMatches();

    @Delete
    void deleteMatch(match i);
}
