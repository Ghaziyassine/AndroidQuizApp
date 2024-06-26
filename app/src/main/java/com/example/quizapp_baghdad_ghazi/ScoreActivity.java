package com.example.quizapp_baghdad_ghazi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        getWindow().setFlags(1024, 1024);

        CircularProgressBar circularProgressBar = findViewById(R.id.circularProgressBar);
        TextView tvScoreTitle = findViewById(R.id.tvScoreTitle);
        TextView tvScore = findViewById(R.id.tvScore);
        Button bPlayAgain = findViewById(R.id.bPlayAgain);
        Button btnLogout = findViewById(R.id.btnLogout);

        // Get the score passed from MainActivity
        int totalQuestions = getIntent().getIntExtra("TOTAL_QUESTIONS", 0);
        int correctAnswers = getIntent().getIntExtra("CORRECT_ANSWERS", 0);

        // Calculate progress
        int progress = (correctAnswers * 100) / totalQuestions;

        // Set the progress to CircularProgressBar
        circularProgressBar.setProgressWithAnimation(progress, 1000L); // 1000 is animation duration in milliseconds

        // Set the score text
        tvScore.setText("Score: " + correctAnswers + " / " + totalQuestions);

        bPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to MainActivity to play again
                Intent intent = new Intent(ScoreActivity.this, HomeActivity.class);
                startActivity(intent);
                finish(); // Close this activity to prevent going back to it with the back button
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log out the user
                FirebaseAuth.getInstance().signOut();

                // Redirect to login activity
                Intent intent = new Intent(ScoreActivity.this, loginActivity.class);
                startActivity(intent);
                finish(); // Optional, to close this activity
            }
        });
    }

}