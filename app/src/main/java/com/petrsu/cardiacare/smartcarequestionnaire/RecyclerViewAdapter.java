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

    public static final int TextField = 0;
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
        } else if (Type == TextField){
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.textfield_card, viewGroup, false);
            return new TextFieldViewHolder(v);
        } else if (Type == Bipolarquestion){
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bipolarquestion_card, viewGroup, false);
            return new BipolarQuestionViewHolder(v);
        } else if (Type == Multiplechoice){
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.multiplechoice_card, viewGroup, false);
            return new MultipleChoiceViewHolder(v);
        } else if (Type == Likertscale ){
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.likertscale_card, viewGroup, false);
            return new LikertScaleViewHolder(v);
        } else {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.singlechoice_card, viewGroup, false);
            return new SingleChoiceViewHolder(v);
        }

            /*
            case Guttmanscale:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.guttmansacle_card, viewGroup, false);
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
            Question question = Questions.get(position);
            Answer answer = question.getAnswer();
            LinkedList<AnswerItem> answeritem = answer.getItems();
            DichotomousViewHolder holder = (DichotomousViewHolder) viewHolder;
            holder.DichotomousQuestion.setText(question.getDescription());
            if (answeritem.size() > 0) {
                AnswerItem Item = answeritem.get(0);
                holder.DichotomousAnswer1.setText(Item.getItemText());
                Item = answeritem.get(1);
                holder.DichotomousAnswer2.setText(Item.getItemText());
            }
        } else if (viewHolder.getItemViewType() == Bipolarquestion) {
            Question question = Questions.get(position);
            //Answer answer = question.getAnswer();
            //LinkedList<AnswerItem> answeritem = answer.getItems();
            BipolarQuestionViewHolder holder = (BipolarQuestionViewHolder) viewHolder;
            holder.BipolarQuestionQuestion.setText(question.getDescription());
        } else if (viewHolder.getItemViewType() == Multiplechoice) {
            Question question = Questions.get(position);
            Answer answer = question.getAnswer();
            LinkedList<AnswerItem> answeritem = answer.getItems();
            MultipleChoiceViewHolder holder = (MultipleChoiceViewHolder) viewHolder;
            holder.MultipleChoiceQuestion.setText(question.getDescription());
            CheckBox[] MultipleChoiceAnswers = new CheckBox[answeritem.size()];
            if (answeritem.size() > 0) {
                for (int j = 0; j < answeritem.size(); j++) {
                    AnswerItem Item = answeritem.get(j);
                    MultipleChoiceAnswers[j] = new CheckBox(context);
                    MultipleChoiceAnswers[j].setId(j);
                    MultipleChoiceAnswers[j].setText(Item.getItemText());
                    holder.MultipleChoiceLayout.addView(MultipleChoiceAnswers[j]);
                }
            }
        } else if (viewHolder.getItemViewType() == Singlechoice) {
            Question question = Questions.get(position);
            Answer answer = question.getAnswer();
            LinkedList<AnswerItem> answeritem = answer.getItems();
            SingleChoiceViewHolder holder = (SingleChoiceViewHolder) viewHolder;
            holder.SingleChoiceQuestion.setText(question.getDescription());
            RadioButton[] SingleChoiceAnswers = new RadioButton[answeritem.size()];
            if (answeritem.size() > 0) {
                for (int j = 0; j < answeritem.size(); j++) {
                    AnswerItem Item = answeritem.get(j);
                    SingleChoiceAnswers[j] = new RadioButton(context);
                    SingleChoiceAnswers[j].setId(j);
                    SingleChoiceAnswers[j].setText(Item.getItemText());
                    holder.SingleChoiceGroup.addView(SingleChoiceAnswers[j]);
                }
            }
        } else if (viewHolder.getItemViewType() == TextField) {
            Question question = Questions.get(position);
            TextFieldViewHolder holder = (TextFieldViewHolder) viewHolder;
            holder.TextFieldQuestion.setText(question.getDescription());
        } else if (viewHolder.getItemViewType() == Likertscale) {
            Question question = Questions.get(position);
            Answer answer = question.getAnswer();
            LinkedList<AnswerItem> answeritem = answer.getItems();
            LikertScaleViewHolder holder = (LikertScaleViewHolder) viewHolder;
            holder.LikertScaleQuestion.setText(question.getDescription());
            RadioButton[] LikertScaleAnswers = new RadioButton[answeritem.size()];
            if (answeritem.size() > 0) {
                for (int j = 0; j < answeritem.size(); j++) {
                    AnswerItem Item = answeritem.get(j);
                    LikertScaleAnswers[j] = new RadioButton(context);
                    LikertScaleAnswers[j].setId(j);
                    LikertScaleAnswers[j].setText(Item.getItemText());
                    holder.LikertScaleGroup.addView(LikertScaleAnswers[j]);
                }
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

    public class TextFieldViewHolder extends ViewHolder {
        TextView TextFieldQuestion;
        EditText TextFieldAnswer;
        public TextFieldViewHolder(View v) {
            super(v);
            this.TextFieldQuestion = (TextView) v.findViewById(R.id.TextQuestion);
            // get answer
            this.TextFieldAnswer = (EditText) v.findViewById(R.id.editText);
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

    public class BipolarQuestionViewHolder extends ViewHolder {
        TextView BipolarQuestionQuestion;
        TextView textView2;
        SeekBar seekBar;

        public BipolarQuestionViewHolder(View v) {
            super(v);
            this.BipolarQuestionQuestion = (TextView) v.findViewById(R.id.BipolarQuestionQuestion);
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

    public class LikertScaleViewHolder extends ViewHolder {
        TextView LikertScaleQuestion;
        RadioGroup LikertScaleGroup;
        RadioButton LikertScaleAnswer;

        public LikertScaleViewHolder(View v) {
            super(v);
            this.LikertScaleQuestion = (TextView) v.findViewById(R.id.LikertScaleQuestion);
            this.LikertScaleGroup = (RadioGroup) v.findViewById(R.id.LikertScaleAnswers);
            this.LikertScaleAnswer = (RadioButton) v.getParent();
        }
    }
}