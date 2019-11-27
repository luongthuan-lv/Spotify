package vn.edu.poly.spotify.ui.music;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MusicDao {
    @Query("SELECT*FROM music")
    List<Music> getAll();

    @Insert
    long[] insert(Music... music);

    @Delete
    int delete(Music music);
}
