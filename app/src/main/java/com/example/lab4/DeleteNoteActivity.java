package com.example.lab4; // Patikrink savo package

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Map;

public class DeleteNoteActivity extends AppCompatActivity {

    private Spinner spinnerNotes;
    private Button buttonDelete;
    private ArrayList<String> notesTitles;
    private ArrayAdapter<String> adapter;
    public static final String PREFS_NAME = "MyNotes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        spinnerNotes = findViewById(R.id.spinnerNotes);
        buttonDelete = findViewById(R.id.buttonDelete);

        notesTitles = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, notesTitles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNotes.setAdapter(adapter);

        loadNotesTitles();

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spinnerNotes.getSelectedItem() == null) {
                    Toast.makeText(DeleteNoteActivity.this, "No note selected!", Toast.LENGTH_SHORT).show();
                } else {
                    String selectedTitle = spinnerNotes.getSelectedItem().toString();
                    deleteNote(selectedTitle);
                }
            }
        });
    }

    private void loadNotesTitles() {
        notesTitles.clear();
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Map<String, ?> allNotes = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allNotes.entrySet()) {
            notesTitles.add(entry.getKey());
        }
        adapter.notifyDataSetChanged();
    }

    private void deleteNote(String title) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(title);
        editor.apply();

        Toast.makeText(this, "Note deleted!", Toast.LENGTH_SHORT).show();

        // Po ištrynimo grįžtam į pagrindinį langą
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
