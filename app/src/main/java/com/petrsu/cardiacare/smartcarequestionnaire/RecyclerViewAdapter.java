package com.petrsu.cardiacare.smartcarequestionnaire;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.petrsu.cardiacare.smartcare.*;

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
    public static final int DefaultValue = 8;

    public RecyclerViewAdapter(LinkedList <Question> Questions, int[] Types) {
        this.Questions = Questions;
        TypesQuestions = Types;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int Type) {
        View v;
        if (Type == Dichotomous) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dichotomous_card, viewGroup, false);
            return new DichotomousViewHolder(v);
        } else if (Type == Singlechoice){
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.singlechoice_card, viewGroup, false);
            return new SingleChoiceViewHolder(v);
        } else if (Type == Tekst){
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tekst_card, viewGroup, false);
            return new TekstViewHolder(v);
        } else {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.singlechoice_card, viewGroup, false);
            return new SingleChoiceViewHolder(v);
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
            LinkedList<AnswerItem> AI = A.getItems();
            DichotomousViewHolder holder = (DichotomousViewHolder) viewHolder;
            holder.DichotomousQuestion.setText(qst.getDescription());
            if (AI.size() > 0) {
                AnswerItem Item = AI.get(0);
                holder.DichotomousAnswer1.setText(Item.getItemText());
                Item = AI.get(1);
                holder.DichotomousAnswer2.setText(Item.getItemText());
            }
        } else if (viewHolder.getItemViewType() == Singlechoice) {
            Question qst = Questions.get(position);
            Answer A = qst.getAnswer();
            LinkedList<AnswerItem> AI = A.getItems();
            SingleChoiceViewHolder holder = (SingleChoiceViewHolder) viewHolder;
            holder.SingleChoiceQuestion.setText(qst.getDescription());
            if (AI.size() > 0) {
                AnswerItem Item = AI.get(0);
                holder.SingleChoiceAnswer1.setText(Item.getItemText());
                Item = AI.get(1);
                holder.SingleChoiceAnswer2.setText(Item.getItemText());
            }
        } else if (viewHolder.getItemViewType() == Tekst) {
            Question qst = Questions.get(position);
            TekstViewHolder holder = (TekstViewHolder) viewHolder;
            holder.TekstQuestion.setText(qst.getDescription());
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

    public class SingleChoiceViewHolder extends ViewHolder {
        TextView SingleChoiceQuestion;
        RadioButton SingleChoiceAnswer1, SingleChoiceAnswer2;

        public SingleChoiceViewHolder(View v) {
            super(v);
            this.SingleChoiceQuestion = (TextView) v.findViewById(R.id.SingleChoiceQuestion);
            this.SingleChoiceAnswer1 = (RadioButton) v.findViewById(R.id.SingleChoiceAnswer1);
            this.SingleChoiceAnswer2 = (RadioButton) v.findViewById(R.id.SingleChoiceAnswer2);
        }
    }
    /*
    *Text Question
     */
    public class TekstViewHolder extends ViewHolder {
        TextView TekstQuestion;

        public TekstViewHolder(View v) {
            super(v);
            this.TekstQuestion = (TextView) v.findViewById(R.id.TekstQuestion);
        }
    }
}