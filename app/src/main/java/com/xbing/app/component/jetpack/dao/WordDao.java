package com.xbing.app.component.jetpack.dao;

import com.xbing.app.component.jetpack.entity.Word;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    @Query("DELETE FROM tb_word")
    void deleteAll();

    @Update
    void update(Word word);

    @Query("SELECT * from tb_word ORDER BY id ASC")
    LiveData<List<Word>> getWords();
}
