package algonquin.cst2335.cst2335_finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    public void insertMovie(MovieInfo m);

    @Query("Select * FROM MovieInfo")
    public List<MovieInfo> getAllMovies();

    @Delete
    void deleteMovie(MovieInfo m);
}
