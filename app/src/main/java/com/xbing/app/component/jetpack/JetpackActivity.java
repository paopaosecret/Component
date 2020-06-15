package com.xbing.app.component.jetpack;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xbing.app.component.R;
import com.xbing.app.component.jetpack.adapter.WordAdapter;
import com.xbing.app.component.jetpack.entity.Word;
import com.xbing.app.component.jetpack.viewmodel.WordViewModel;

import java.util.List;
import java.util.Random;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class JetpackActivity extends AppCompatActivity {

    private WordViewModel mWordViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jetpack);

        final RecyclerView recyclerView = findViewById(R.id.rv_test);
        final WordAdapter adapter = new WordAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                adapter.setWords(words);
                if(words != null && words.size() > 1){
                    recyclerView.smoothScrollToPosition(words.size()-1);
                }
            }
        });

        FloatingActionButton button = findViewById(R.id.fab_add_word);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int num = new Random().nextInt(100000);
                Word word = new Word("word" + num, "description" + num);
                mWordViewModel.insert(word);
            }
        });
    }
}
