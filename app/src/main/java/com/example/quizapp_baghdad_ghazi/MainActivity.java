package com.example.quizapp_baghdad_ghazi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private LinearLayout quizLayout;
    private TextView tvQuestionNumber;
    private ImageView imageView;  // Added this line
    private TextView tvQuestion;
    private RadioGroup rgAnswers;
    private Button bNext;
    private ProgressBar progressBar;

    private String[] questions;
    private String[][] options;
    private int[] answers;

    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        quizLayout = findViewById(R.id.quizLayout);
        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        imageView = findViewById(R.id.imageView); // Initialize imageView
        tvQuestion = findViewById(R.id.tvQuestion);
        rgAnswers = findViewById(R.id.rgAnswers);
        bNext = findViewById(R.id.bNext);
        progressBar = findViewById(R.id.progressBar);

        // Initialize Questions, Options, and Answers
        questions = getResources().getStringArray(R.array.questions);
        options = new String[][]{
                getResources().getStringArray(R.array.options1),
                getResources().getStringArray(R.array.options2),
                getResources().getStringArray(R.array.options3)
                // Add more arrays as needed
        };
        answers = getResources().getIntArray(R.array.answers);

        // Display initial question
        displayQuestion(currentQuestionIndex);

        bNext.setOnClickListener(v -> {
            // Check if an answer is selected
            if (rgAnswers.getCheckedRadioButtonId() == -1) {
                Toast.makeText(MainActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                return;
            }

            // Get selected answer index
            int selectedAnswerIndex = rgAnswers.indexOfChild(findViewById(rgAnswers.getCheckedRadioButtonId()));

            // Check if answer is correct
            if (selectedAnswerIndex == answers[currentQuestionIndex]) {
                score++;
            }

            // Move to next question or show score
            if (currentQuestionIndex < questions.length - 1) {
                currentQuestionIndex++;
                displayQuestion(currentQuestionIndex);
            } else {
                // Quiz completed, show score
                showScore();
            }
        });
    }

    private void displayQuestion(int questionIndex) {
        tvQuestionNumber.setText(getString(R.string.question_number, questionIndex + 1, questions.length));
        tvQuestion.setText(questions[questionIndex]);

        // Load image for the current question
        String[] imageNames = getResources().getStringArray(R.array.image_names);
        if (questionIndex < imageNames.length) {
            String imageName = imageNames[questionIndex];
            int imageResource = getResources().getIdentifier(imageName, "drawable", getPackageName());
            if (imageResource != 0) {
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageResource(imageResource);
            } else {
                // If no image for this question, hide the ImageView
                imageView.setVisibility(View.GONE);
            }
        } else {
            // If no image name for this question, hide the ImageView
            imageView.setVisibility(View.GONE);
        }

        rgAnswers.removeAllViews();
        for (String option : options[questionIndex]) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(option);
            rgAnswers.addView(radioButton);
        }

        rgAnswers.clearCheck();
    }


    private void showScore() {
        quizLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        int maxScore = questions.length;
        int progress = (int) ((score / (float) maxScore) * 100);

        progressBar.setProgress(progress);
        progressBar.setIndeterminate(false);

        // Show score
        new AlertDialog.Builder(this)
                .setTitle("Quiz Completed")
                .setMessage("Your score is: " + score + " out of " + maxScore)
                .setPositiveButton("OK", (dialog, which) -> finish())
                .show();
    }
}
