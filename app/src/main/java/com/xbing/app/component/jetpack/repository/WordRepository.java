package com.xbing.app.component.jetpack.repository;

import android.app.Application;

import com.xbing.app.component.jetpack.dao.WordDao;
import com.xbing.app.component.jetpack.entity.Word;
import com.xbing.app.component.jetpack.room.WordRoomDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class WordRepository {
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    public WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getWords();
    }

    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public void insert(final Word word) {
        WordRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mWordDao.insert(word);
            }
        });
    }
}
