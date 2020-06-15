package com.xbing.app.component.jetpack.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xbing.app.component.R;
import com.xbing.app.component.jetpack.entity.Word;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvWord;
        private final TextView tvDesc;

        private WordViewHolder(View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.tv_word);
            tvDesc = itemView.findViewById(R.id.tv_desc);
        }
    }

    private final LayoutInflater mInflater;
    private List<Word> mWords;

    public WordAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.adapter_jetpack_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        if (mWords != null) {
            Word current = mWords.get(position);
            holder.tvWord.setText("word:" + current.getWord());
            holder.tvDesc.setText("desc:" + current.getDescription());
        } else {
            // Covers the case of data not being ready yet.
            holder.tvWord.setText("No Word");
            holder.tvWord.setText("No Description");
        }
    }

    public void setWords(List<Word> words){
        mWords = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mWords != null){
            return mWords.size();
        } else {
            return 0;
        }
    }
}
