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

public class UpdateNoteActivity extends AppCompatActivity {

  EditText edTxtTitle, edTxtDesc;
  Button cancel, save;

  int noteId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    getSupportActionBar().setTitle("Edit Note");
    setContentView(R.layout.activity_update_note);

    edTxtTitle = findViewById(R.id.edit_text_title_update);
    edTxtDesc = findViewById(R.id.edit_text_desc_update);
    cancel = findViewById(R.id.btn_cancel_update);
    save = findViewById(R.id.btn_save_update);

    getData();

    cancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(getApplicationContext(), "Nothing updated!", Toast.LENGTH_SHORT).show();
        finish();
      }
    });

    save.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        updateNote();
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

  private void updateNote() {
    String lastTitle = edTxtTitle.getText().toString();
    String lastDesc = edTxtDesc.getText().toString();
    Intent i = new Intent();
    i.putExtra("lastTitle", lastTitle);
    i.putExtra("lastDesc", lastDesc);
    if (noteId != -1) {
      i.putExtra("lastId", noteId);
      setResult(RESULT_OK, i);
      finish();
    }

  }

  public void getData() {
    Intent i = getIntent();
    noteId = i.getIntExtra("id", -1);
    String title = i.getStringExtra("title");
    String desc = i.getStringExtra("desc");

    edTxtTitle.setText(title);
    edTxtDesc.setText(desc);
  }
}