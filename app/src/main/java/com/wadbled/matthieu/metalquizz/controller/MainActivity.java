package com.wadbled.matthieu.metalquizz.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wadbled.matthieu.metalquizz.R;
import com.wadbled.matthieu.metalquizz.model.User;

public class MainActivity extends AppCompatActivity {

    //constante
    public static final String BUNDLE_EXTRA_NAME = "bundle_name";
    public static final int GAME_ACTIVITY_REQUEST_CODE = 77;
    public static final String PREF_KEY_SCORE = "PREF_KEY_SCORE";
    public static final String PREF_KEY_NAME = "PREF_KEY_NAME";

    //variables
    private Button mPlayButton;
    private TextView mWelcomeText;
    private EditText mInputName;
    private User mUser;

    private SharedPreferences mPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("MainActivity::onCreate");

        mUser = new User();
        mPreferences = getPreferences(MODE_PRIVATE);

        mWelcomeText = findViewById(R.id.activity_main_welcome_text);
        mInputName = findViewById(R.id.activity_main_name_input);
        mPlayButton = findViewById(R.id.activity_main_play_button);
        mPlayButton.setEnabled(false);

        demarragePartie();

        mInputName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().length() > 2) {
                        mPlayButton.setEnabled(true);
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser.setUserName(mInputName.getText().toString());
                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);

                mPreferences.edit().putString(PREF_KEY_NAME, mUser.getUserName()).apply();

                gameActivity.putExtra(BUNDLE_EXTRA_NAME, mUser.getUserName());
                startActivityForResult(gameActivity, GAME_ACTIVITY_REQUEST_CODE);


            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && resultCode == RESULT_OK) {

            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);

            mPreferences.edit().putInt(PREF_KEY_SCORE, score).apply();

            demarragePartie();

        }
    }

    private void demarragePartie() {
        String userName = mPreferences.getString(PREF_KEY_NAME, null);

        if (null != userName) {
            int score = mPreferences.getInt(PREF_KEY_SCORE, 0);

            mWelcomeText.setText("Bon retour parmi nous " + userName + " ton score précèdent été de : " + score + "\nessai de faire mieux...");
            mInputName.setText(userName);
            mInputName.setSelection(userName.length());
            mPlayButton.setEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("MainActivity::onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("MainActivity::onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("MainActivity::onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("MainActivity::onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("MainActivity::onDestroy");
    }
}
