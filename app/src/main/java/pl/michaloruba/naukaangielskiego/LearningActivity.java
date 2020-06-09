package pl.michaloruba.naukaangielskiego;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class LearningActivity extends AppCompatActivity implements View.OnClickListener {

    private Boolean isTestMode;
    private Boolean isEnglishSelected;
    private String [] englishWords;
    private String [] polishWords;
    private int amountOfWords;
    Iterator<Integer> iterator;
    private Random random = new Random();

    private EditText polishEditText;
    private EditText englishEditText;
    private int testQuestionCounter;
    private int testCorrectAnswers;
    private String currentCorrectAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        TextView modeTextView = findViewById(R.id.modeTextView);
        Button nextWordButton = findViewById(R.id.nextWordButton);
        Button backButton = findViewById(R.id.backButton);
        polishEditText = findViewById(R.id.polishEditText);
        englishEditText = findViewById(R.id.englishEditText);
        nextWordButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        prepareFields();
        modeTextView.setText(getString(isTestMode ? R.string.modeTest : R.string.modeLearn));
        shuffleAnswersInButtons();
        if (isTestMode){
            startNewTestMode();
        }
        else {
            startNewLearnMode();
        }
    }

    private void prepareFields() {
        Intent intent = getIntent();
        isTestMode = "TST".equals(intent.getStringExtra("mode"));
        isEnglishSelected = "ENG".equals(intent.getStringExtra("language"));
        englishWords = getResources().getStringArray(R.array.englishWords);
        polishWords = getResources().getStringArray(R.array.polishWords);
        amountOfWords = englishWords.length;
    }

    private void shuffleAnswersInButtons() {
        Set<Integer> wordsOrder = new LinkedHashSet<>();
        do {
            wordsOrder.add(random.nextInt(amountOfWords));
        }
        while (wordsOrder.size() < amountOfWords);
        iterator = wordsOrder.iterator();
    }

    private void startNewTestMode() {
        testQuestionCounter = 0;
        testCorrectAnswers = 0;
        selectNextQuestion();
    }

    private void selectNextQuestion() {
        if (testQuestionCounter < 10) {
            int currentQuestion = iterator.next();
            if (isEnglishSelected) {
                currentCorrectAnswer = polishWords[currentQuestion];
                englishEditText.setFocusable(false);
                englishEditText.setText(englishWords[currentQuestion]);
            } else {
                currentCorrectAnswer = englishWords[currentQuestion];
                polishEditText.setFocusable(false);
                polishEditText.setText(polishWords[currentQuestion]);
            }
            testQuestionCounter++;
        }
        else {
            startResultActivity();
        }
    }

    private void checkAnswer() {
        String answer = isEnglishSelected ? polishEditText.getText().toString() : englishEditText.getText().toString();
        if (answer.trim().toLowerCase().equals(currentCorrectAnswer.trim().toLowerCase())) testCorrectAnswers++;
        polishEditText.setText("");
        englishEditText.setText("");
    }

    private void startResultActivity() {
        Intent intent = new Intent (this, ActivityResults.class);
        intent.putExtra("score", testCorrectAnswers);
        startActivity(intent);
        finish();
    }

    private void displayAlert() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this)
                .setMessage(getString(R.string.alertMessage))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") int id) {
                        returnToMainActivity();
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, @SuppressWarnings("unused") int id) {

                    }
                });
        final AlertDialog alert = dialogBuilder.create();
        alert.show();
    }

    private void startNewLearnMode() {
        englishEditText.setFocusable(false);
        polishEditText.setFocusable(false);
        displayNextWord();
    }

    private void displayNextWord() {
        if (iterator.hasNext()) {
            int currentWord = iterator.next();
            englishEditText.setText(englishWords[currentWord]);
            polishEditText.setText(polishWords[currentWord]);
        }
        else {
            Toast.makeText(this, "Wyświetlono już wszystkie słowa.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backButton:
                if (isTestMode){
                    displayAlert();
                }
                else{
                    returnToMainActivity();
                }
                break;
            case R.id.nextWordButton:
                if (isTestMode){
                    checkAnswer();
                    selectNextQuestion();
                }
                else {
                    displayNextWord();
                }
                break;
        }
    }

    private void returnToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
