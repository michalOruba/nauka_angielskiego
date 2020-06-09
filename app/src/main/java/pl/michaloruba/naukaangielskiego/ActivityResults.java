package pl.michaloruba.naukaangielskiego;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        TextView scoreTextView = findViewById(R.id.scoreTextView);

        Intent intent = getIntent();
        scoreTextView.setText(String.format(getResources().getConfiguration().locale,"%d/10", intent.getIntExtra("score", 0)));

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityResults.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
