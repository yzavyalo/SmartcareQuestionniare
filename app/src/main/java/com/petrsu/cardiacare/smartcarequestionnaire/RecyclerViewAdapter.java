package com.petrsu.cardiacare.smartcarequestionnaire;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Kristina Shevtsova
 * Kirill Orloff
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    LinkedList <Question> Questions;

    public RecyclerViewAdapter(LinkedList <Question> Questions) {
        this.Questions = Questions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dichotomous_card, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Question Question = Questions.get(i);
        viewHolder.description.setText(Question.getDescription());
    }

    @Override
    public int getItemCount() {
        return Questions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.DichotomousQuestion);
        }
    }

}