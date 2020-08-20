package com.example.waterconsumptioncontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int VOLUME_TO_DRINK = 2000;    //In ml

    ProgressBar progressBar;
    EditText editText;
    Button applyButton, resetButton;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init views
        applyButton = findViewById(R.id.applyButton);
        editText = findViewById(R.id.editText);
        resetButton = findViewById(R.id.resetButton);
        progressBar = findViewById(R.id.progressBar);

        sharedPreferences = getSharedPreferences("storage", MODE_PRIVATE);
        int currentVolumeInMl = sharedPreferences.getInt("currentVolume", 0);
        int currentVolumeInPercent = currentVolumeInMl*100/VOLUME_TO_DRINK;
        progressBar.setProgress(currentVolumeInPercent);

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    int valueToAddInMl = Integer.parseInt(editText.getText().toString());
                    int valueToAddInPercent = valueToAddInMl*100/VOLUME_TO_DRINK;
                    progressBar.setProgress(progressBar.getProgress() + valueToAddInPercent);
                    SharedPreferences.Editor editor
                            = sharedPreferences.edit();
                    editor.putInt("currentVolume", progressBar.getProgress()*VOLUME_TO_DRINK/100);
                    editor.apply();
                }
                catch (Exception ex){
                    Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                finally {
                    editText.setText("");
                }

            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editText.setText("");
                progressBar.setProgress(0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("currentVolume", 0);
                editor.apply();

                Toast.makeText(MainActivity.this, "refreshed", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
