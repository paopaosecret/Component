package com.xbing.app.component.jetpack.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_word")
public class Word {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "word")
    private String word;

    @ColumnInfo(name = "description")
    private String description;

    public Word(String word, String description) {
        this.word = word;
        this.description = description;
    }

    public String getWord(){return this.word;}

    @NonNull
    public long getId() {
        return id;
    }

    public void setId(@NonNull long mId) {
        this.id = mId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String mDescription) {
        this.description = mDescription;
    }
}
