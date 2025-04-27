package com.example.lab4; // tavo package

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextContent;
    private Button buttonSave;

    public static final String PREFS_NAME = "MyNotes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTextTitle.getText().toString().trim();
                String content = editTextContent.getText().toString().trim();

                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(AddNoteActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    saveNote(title, content);
                    Toast.makeText(AddNoteActivity.this, "Note saved!", Toast.LENGTH_SHORT).show();
                    finish(); // Grįžta į MainActivity
                }
            }
        });
    }

    private void saveNote(String title, String content) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(title, content);
        editor.apply(); // išsaugo
    }
}
