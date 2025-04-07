package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddNoteActivity extends AppCompatActivity {

  EditText edTxtTitle, edTxtDesc;
  Button cancel, save;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    getSupportActionBar().setTitle("Add Note");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    setContentView(R.layout.activity_add_note);

    edTxtTitle = findViewById(R.id.edit_text_title);
    edTxtDesc = findViewById(R.id.edit_text_desc);
    cancel = findViewById(R.id.btn_cancel);
    save = findViewById(R.id.btn_save);

    cancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(getApplicationContext(), "Nothing saved!", Toast.LENGTH_SHORT).show();
        finish();
      }
    });

    save.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        saveNote();
      }
    });

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
      (v, insets) -> {
        Insets systemBars = insets.getInsets(
          WindowInsetsCompat.Type.systemBars());
        v.setPadding(systemBars.left, systemBars.top, systemBars.right,
          systemBars.bottom);
        return insets;
      });
  }

  private void saveNote() {
    String title = edTxtTitle.getText().toString();
    String desc = edTxtDesc.getText().toString();

    Intent i = new Intent();
    i.putExtra("noteTitle", title);
    i.putExtra("noteDesc", desc);
    setResult(RESULT_OK, i);
    finish();
  }
}