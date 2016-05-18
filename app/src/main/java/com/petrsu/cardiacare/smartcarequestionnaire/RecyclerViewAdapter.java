package com.petrsu.cardiacare.smartcarequestionnaire;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.petrsu.cardiacare.smartcare.*;

import java.util.LinkedList;

import android.widget.EditText;

/**
 * Kristina Shevtsova
 * Kirill Orloff
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    LinkedList <Question> Questions;
    int[] TypesQuestions;
    Context context;

    public static final int Tekst = 0;
    public static final int Multiplechoice = 1;
    public static final int Singlechoice = 2;
    public static final int Bipolarquestion = 3;
    public static final int Guttmanscale = 4;
    public static final int Likertscale = 5;
    public static final int Continuousscale = 6;
    public static final int Dichotomous = 7;
    public static final int DefaultValue = 8;

    public RecyclerViewAdapter(LinkedList <Question> Questions, int[] Types, Context context) {
        this.Questions = Questions;
        TypesQuestions = Types;
        this.context = context;
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
        } else if (Type == Bipolarquestion){
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bipolarquestion_card, viewGroup, false);
            return new BipolarquestionViewHolder(v);
        } else if (Type == Multiplechoice){
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.multiplechoice_card, viewGroup, false);
            return new MultipleChoiceViewHolder(v);
        } else {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.singlechoice_card, viewGroup, false);
            return new SingleChoiceViewHolder(v);
        }

            /*
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
        } else if (viewHolder.getItemViewType() == Bipolarquestion) {
            Question qst = Questions.get(position);
            Answer A = qst.getAnswer();
            LinkedList<AnswerItem> AI = A.getItems();
            BipolarquestionViewHolder holder = (BipolarquestionViewHolder) viewHolder;
            holder.BipolarquestionQuestion.setText(qst.getDescription());
        } else if (viewHolder.getItemViewType() == Multiplechoice) {
            Question qst = Questions.get(position);
            Answer A = qst.getAnswer();
            LinkedList<AnswerItem> AI = A.getItems();
            MultipleChoiceViewHolder holder = (MultipleChoiceViewHolder) viewHolder;
            holder.MultipleChoiceQuestion.setText(qst.getDescription());
            CheckBox[] MultipleChoiceAnswers = new CheckBox[AI.size()];

            if (AI.size() > 0) {
                for (int j = 0; j < AI.size(); j++) {
                    AnswerItem item = AI.get(j);
                    MultipleChoiceAnswers[j] = new CheckBox(context);
                    MultipleChoiceAnswers[j].setId(j);
                    MultipleChoiceAnswers[j].setText(item.getItemText());
                    holder.MultipleChoiceLayout.addView(MultipleChoiceAnswers[j]);
                }
            }
        } else if (viewHolder.getItemViewType() == Singlechoice) {
            Question qst = Questions.get(position);
            Answer A = qst.getAnswer();
            LinkedList<AnswerItem> AI = A.getItems();
            SingleChoiceViewHolder holder = (SingleChoiceViewHolder) viewHolder;
            holder.SingleChoiceQuestion.setText(qst.getDescription());
            RadioButton[] SingleChoiceAnswers = new RadioButton[AI.size()];

            if (AI.size() > 0) {
                for (int j = 0; j < AI.size(); j++) {
                    AnswerItem item = AI.get(j);
                    SingleChoiceAnswers[j] = new RadioButton(context);
                    SingleChoiceAnswers[j].setId(j);
                    SingleChoiceAnswers[j].setText(item.getItemText());
                    holder.SingleChoiceGroup.addView(SingleChoiceAnswers[j]);
                }
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
        RadioGroup SingleChoiceGroup;
        RadioButton SingleChoiceAnswer;

        public SingleChoiceViewHolder(View v) {
            super(v);
            this.SingleChoiceQuestion = (TextView) v.findViewById(R.id.SingleChoiceQuestion);
            this.SingleChoiceGroup = (RadioGroup) v.findViewById(R.id.SingleChoiceAnswers);
            this.SingleChoiceAnswer = (RadioButton) v.getParent();
        }
    }

    public class TekstViewHolder extends ViewHolder {
        TextView TekstQuestion;
        EditText TekstAnswer;
        public TekstViewHolder(View v) {
            super(v);
            this.TekstQuestion = (TextView) v.findViewById(R.id.TekstQuestion);
            // get answer
            this.TekstAnswer = (EditText) v.findViewById(R.id.editText);
        }
    }

    public class MultipleChoiceViewHolder extends ViewHolder {
        TextView MultipleChoiceQuestion;
        LinearLayout MultipleChoiceLayout;
        CheckBox MultipleChoiceAnswer;

        public MultipleChoiceViewHolder(View v) {
            super(v);
            this.MultipleChoiceQuestion = (TextView) v.findViewById(R.id.MultipleChoiceQuestion);
            this.MultipleChoiceLayout = (LinearLayout) v.findViewById(R.id.LinearMultiple);
            this.MultipleChoiceAnswer = (CheckBox) v.getParent();
        }
    }

    public class BipolarquestionViewHolder extends ViewHolder {
        TextView BipolarquestionQuestion;
        TextView textView2;
        SeekBar seekBar;

        public BipolarquestionViewHolder(View v) {
            super(v);
            this.BipolarquestionQuestion = (TextView) v.findViewById(R.id.BipolarquestionQuestion);
            this.seekBar = (SeekBar) v.findViewById(R.id.seekBar);
            this.textView2 = (TextView) v.findViewById(R.id.textView2);
            this.textView2.setText(seekBar.getProgress() + "/" + seekBar.getMax());
            seekBar.setOnSeekBarChangeListener(
                    new SeekBar.OnSeekBarChangeListener() {
                        int progress = 0;

                        public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                            progress = progresValue;
                        }

                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        public void onStopTrackingTouch(SeekBar seekBar) {
                            textView2.setText(progress + "/" + seekBar.getMax());
                        }
                    }
            );
        }
    }
}