package com.app_mobile.preferencesandfilesdemo;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText etName, etFileContent;
    Button btnSavePref, btnLoadPref, btnSaveFile, btnReadFile;
    TextView tvResult;

    SharedPreferences sharedPreferences;
    public static final String PREF_NAME = "MyPrefs";
    public static final String KEY_NAME = "name";

    public static final String FILE_NAME = "mydata.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        etName = findViewById(R.id.etName);
        etFileContent = findViewById(R.id.etFileContent);
        btnSavePref = findViewById(R.id.btnSavePref);
        btnLoadPref = findViewById(R.id.btnLoadPref);
        btnSaveFile = findViewById(R.id.btnSaveFile);
        btnReadFile = findViewById(R.id.btnReadFile);
        tvResult = findViewById(R.id.tvResult);

        // SharedPreferences Initialization
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Save to SharedPreferences
        btnSavePref.setOnClickListener(v -> {
            String name = etName.getText().toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_NAME, name);
            editor.apply();
            Toast.makeText(this, "Saved in Preferences", Toast.LENGTH_SHORT).show();
        });

        // Load from SharedPreferences
        btnLoadPref.setOnClickListener(v -> {
            String name = sharedPreferences.getString(KEY_NAME, "No data found");
            tvResult.setText("Loaded: " + name);
        });

        // Save to Internal Storage
        btnSaveFile.setOnClickListener(v -> {
            String text = etFileContent.getText().toString();
            try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
                fos.write(text.getBytes());
                Toast.makeText(this, "Saved to " + FILE_NAME, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error saving file", Toast.LENGTH_SHORT).show();
            }
        });

        // Read from Internal Storage
        btnReadFile.setOnClickListener(v -> {
            try (FileInputStream fis = openFileInput(FILE_NAME)) {
                int c;
                StringBuilder temp = new StringBuilder();
                while ((c = fis.read()) != -1) {
                    temp.append((char) c);
                }
                tvResult.setText("File Content: " + temp.toString());
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error reading file", Toast.LENGTH_SHORT).show();
            }
        });
    }
}