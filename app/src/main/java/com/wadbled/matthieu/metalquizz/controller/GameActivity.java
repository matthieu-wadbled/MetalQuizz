package com.wadbled.matthieu.metalquizz.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wadbled.matthieu.metalquizz.R;
import com.wadbled.matthieu.metalquizz.model.Question;
import com.wadbled.matthieu.metalquizz.model.QuestionBank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    //constantes
    public static final String BUNDLE_EXTRA_SCORE = "bundle_score";
    public static final String BUNDLE_STATE_SCORE = "score_courant";
    public static final String BUNDLE_STATE_NBR_QUESTION = "question_courante";

    //elements d'affichage
    private TextView mTextQuestion;
    private Button mChoix1;
    private Button mChoix2;
    private Button mChoix3;
    private Button mChoix4;

    private boolean mEnableTouch;

    private int mNbrQuestion;
    private int mScore;
    private String mUserName;
    private QuestionBank mQuestionBank;
    private Question mQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        System.out.println("GameActivity::onCreate");

        //création de la banque de question
        createQuestionBank();

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNbrQuestion = savedInstanceState.getInt(BUNDLE_STATE_NBR_QUESTION);
        } else {
            mNbrQuestion = 3;
            mScore = 0;
        }

        mEnableTouch = true;

        mUserName = getIntent().getStringExtra(MainActivity.BUNDLE_EXTRA_NAME);


        //branchement des widgets
        mTextQuestion = findViewById(R.id.activity_game_question);
        mChoix1 = findViewById(R.id.activity_game_choix1);
        mChoix2 = findViewById(R.id.activity_game_choix2);
        mChoix3 = findViewById(R.id.activity_game_choix3);
        mChoix4 = findViewById(R.id.activity_game_choix4);

        //utiliser les tags pour nommer les boutons
        mChoix1.setTag(0);
        mChoix2.setTag(1);
        mChoix3.setTag(2);
        mChoix4.setTag(3);

        //ajout du listener sur chaque bouton
        mChoix1.setOnClickListener(this);
        mChoix2.setOnClickListener(this);
        mChoix3.setOnClickListener(this);
        mChoix4.setOnClickListener(this);

        mQuestion = mQuestionBank.getQuestion();
        displayQuestion(mQuestion);

    }

    private void displayQuestion(Question question) {
        mTextQuestion.setText(question.getQuestion());
        mChoix1.setText(question.getChoiceList().get(0));
        mChoix2.setText(question.getChoiceList().get(1));
        mChoix3.setText(question.getChoiceList().get(2));
        mChoix4.setText(question.getChoiceList().get(3));
    }

    private QuestionBank createQuestionBank() {
        Question question1 = new Question("Quel groupe interprète : House of the Rising Sun ?",
                Arrays.asList("The Clash", "The Who", "The Kinks", "The Animals"), 3);
        Question question2 = new Question("Comment se prénomme le chanteur de The Offspring ?",
                Arrays.asList("Leonard", "Dexter", "Harry", "Francois"), 1);
        Question question3 = new Question("En quel année sort l'album Paranoïd de Black Sabbat ?",
                Arrays.asList("1968", "1970", "1972", "1969"), 1);
        Question question4 = new Question("Sur quel guitare Slash enregistre l'album Appetite for Destruction ?",
                Arrays.asList("Explorer", "Stratocaster", "SG", "Les Paul"), 3);
        Question question5 = new Question("Quel est le 3ème album de Metallica ?",
                Arrays.asList("Master of Puppets", "Metallica", "Ride the Lightning", "...And Justice for All"), 0);
        Question question6 = new Question("Quel groupe de doom metal Japonais a pour thème principale les tueurs en série ?",
                Arrays.asList("Envy", "Killie", "Church of Misery", "Boris"), 2);

        List<Question> questionList = Arrays.asList(question1,question2,question3,question4,question5,question6);
        ArrayList<Question> questionArrayList = new ArrayList<>(questionList);
        mQuestionBank = new QuestionBank(questionArrayList);

        return mQuestionBank;
    }

    @Override
    public void onClick(View v) {
        int answerUserIndex = (int) v.getTag();

        if (answerUserIndex == mQuestion.getIndexReponse()) {
            //bonne réponse
            mScore += 2;
            Toast.makeText(this, "Good", Toast.LENGTH_SHORT).show();
        } else {
            //mauvaise réponse
            mScore--;
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
        }

        //désactive la sélection de touche
        mEnableTouch = false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //réactive, après les 2s de la methode run, l'accès aux touches
                mEnableTouch = true;

                //après la réponse, on décremente le nombre total de questions
                // et si il est différent de zéro renvoi une nouvelle question
                if (--mNbrQuestion == 0) {
                    //affichage du score par boite de dialogue
                    endGame();
                } else {
                    mQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mQuestion);
                }
            }
        }, 1500);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_NBR_QUESTION, mNbrQuestion);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //surcharge de dispatchTouchEvent pour qu'il soit appelé que si mEnableTouch est vrai
        return mEnableTouch && super.dispatchTouchEvent(ev);
    }

    private void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Fin de la partie")
                .setMessage(mUserName + " votre score est de : " + mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }).create().show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("GameActivity::onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("GameActivity::onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("GameActivity::onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("GameActivity::onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("GameActivity::onDestroy");
    }
}

