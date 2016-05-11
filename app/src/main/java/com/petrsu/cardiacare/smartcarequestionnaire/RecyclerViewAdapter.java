package com.petrsu.cardiacare.smartcarequestionnaire;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.sql.Types;
import java.util.LinkedList;

/**
 * Kristina Shevtsova
 * Kirill Orloff
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    LinkedList <Question> Questions;
    int[] TypesQuestions;

    public static final int Tekst = 0;
    public static final int Multiplechoice = 1;
    public static final int Singlechoice = 2;
    public static final int Bipolarquestion = 3;
    public static final int Guttmanscale = 4;
    public static final int Likertscale = 5;
    public static final int Continuousscale = 6;
    public static final int Dichotomous = 7;

    public RecyclerViewAdapter(LinkedList <Question> Questions, int[] Types) {
        this.Questions = Questions;
        TypesQuestions = Types;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int Type) {
        View v;
        ViewHolder a;
        if (Type == Dichotomous) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dichotomous_card, viewGroup, false);
            a = new DichotomousViewHolder(v);
            return a;
        } else {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dichotomous_card, viewGroup, false);
            a = new DichotomousViewHolder(v);
            return a;
        }
            /*
            case Tekst:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tekst_card, viewGroup, false);
                return new ViewHolder(v);
                break;
            case Multiplechoise:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.multiplechoice_card, viewGroup, false);
                return new ViewHolder(v);
                break;
            case Singlechoice:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.singllechoice_card, viewGroup, false);
                return new ViewHolder(v);
                break;
            case Bipolarquestion:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bipolarquestion_card, viewGroup, false);
                return new ViewHolder(v);
            break;
            case Guttmanscale:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.guttmansacle_card, viewGroup, false);
                return new ViewHolder(v);
            break;
            case Likertscale:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.likertscale_card, viewGroup, false);
                return new ViewHolder(v);
            break;
            case Continuousscale:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.continuousscale_card, viewGroup, false);
                return new ViewHolder(v);
            break;
            */
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        if (viewHolder.getItemViewType() == Dichotomous) {
            Question qst = Questions.get(position);
            Answer A = qst.getAnswer();
            LinkedList <AnswerItem> AI = A.getItems();
            DichotomousViewHolder holder = (DichotomousViewHolder) viewHolder;
            holder.DichotomousQuestion.setText(qst.getDescription());
            if (AI.size() > 0) {
                AnswerItem Item = AI.get(1);
                holder.DichotomousAnswer1.setText(Item.getItemText());
                Item = AI.get(2);
                holder.DichotomousAnswer2.setText(Item.getItemText());
            }
        }
    }

    @Override
    public int getItemCount() {
        return Questions.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TypesQuestions[position];
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class DichotomousViewHolder extends ViewHolder {
        TextView DichotomousQuestion;
        RadioButton DichotomousAnswer1, DichotomousAnswer2;

        public DichotomousViewHolder(View v) {
            super(v);
            this.DichotomousQuestion = (TextView) v.findViewById(R.id.DichotomousQuestion);
            this.DichotomousAnswer1 = (RadioButton) v.findViewById(R.id.DichotomousAnswer1);
            this.DichotomousAnswer2 = (RadioButton) v.findViewById(R.id.DichotomousAnswer2);
        }
    }

}