package algonquin.cst2335.cst2335_finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface matchDAO {

    @Insert
    public void insertMatch(Match m);

    @Query("SELECT * FROM `Match`")
    public List<Match> getAllMatches();

    @Delete
    void deleteMatch(Match i);
}
