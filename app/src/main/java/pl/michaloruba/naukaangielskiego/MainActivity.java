package pl.michaloruba.naukaangielskiego;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private RadioGroup languageRadioGroup;
    private RadioGroup modeRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        languageRadioGroup = findViewById(R.id.languageRadioGroup);
        modeRadioGroup = findViewById(R.id.modeRadioGroup);
        Button startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedLanguage = languageRadioGroup.getCheckedRadioButtonId();
                int selectedMode = modeRadioGroup.getCheckedRadioButtonId();

                if (selectedMode == R.id.testRadioButton && selectedLanguage == -1){
                    Toast.makeText(MainActivity.this, "Brak wybranego języka", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, LearningActivity.class);
                    intent.putExtra("language", selectedLanguage == R.id.englishRadioButton ? "ENG" : "PL");
                    intent.putExtra("mode", selectedMode == R.id.testRadioButton ? "TST" : "LRN");
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about){
            displayAlert();
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayAlert() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this)
                .setTitle("O programie")
                .setMessage(
                        "Nauka Angielskiego\n" +
                        "Wersja: 1.0.0\n" +
                        "Autor: Michał Oruba"
                )
                .setCancelable(true)
                .setNeutralButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") int id) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog alert = dialogBuilder.create();
        alert.show();
    }
}
