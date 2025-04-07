package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

  NoteViewModel noteViewModel;

  ActivityResultLauncher<Intent> activityResultLauncherForAddNote;
  ActivityResultLauncher<Intent> activityResultLauncherForUpdateNote;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);

    registerActivityForAddNote();
    registerActivityForUpdateNote();

    RecyclerView recyclerView = findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    NoteAdapter noteAdapter = new NoteAdapter();
    recyclerView.setAdapter(noteAdapter);

    noteViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
      .create(NoteViewModel.class);

    noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
      @Override
      public void onChanged(List<Note> notes) {
        //Update RecyclerView
        noteAdapter.setNotes(notes);
      }
    });

    new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
      ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
      @Override
      public boolean onMove(@NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            @NonNull RecyclerView.ViewHolder target) {
        return false;
      }

      @Override
      public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
                           int direction) {
        noteViewModel.delete(noteAdapter.getNote(viewHolder.getAdapterPosition()));
        Toast.makeText(getApplicationContext(), "Note deleted!", Toast.LENGTH_SHORT).show();
      }
    }).attachToRecyclerView(recyclerView);

    noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(Note note) {
        Intent intent = new Intent(MainActivity.this, UpdateNoteActivity.class);
        intent.putExtra("id", note.getId());
        intent.putExtra("title", note.getTitle());
        intent.putExtra("desc", note.getDescription());
        activityResultLauncherForUpdateNote.launch(intent);
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

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.new_note, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.top_menu) {
      Intent i = new Intent(MainActivity.this, AddNoteActivity.class);
//      startActivity(i);
      activityResultLauncherForAddNote.launch(i);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void registerActivityForAddNote() {
    activityResultLauncherForAddNote = registerForActivityResult(
      new ActivityResultContracts.StartActivityForResult(),
      new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
          int resultCode = o.getResultCode();
          Intent data = o.getData();

          if (resultCode == RESULT_OK && data != null) {
            String title = data.getStringExtra("noteTitle");
            String desc = data.getStringExtra("noteDesc");

            Note note = new Note(title, desc);
            noteViewModel.insert(note);
          }
        }
      });
  }

  private void registerActivityForUpdateNote() {
    activityResultLauncherForUpdateNote = registerForActivityResult(
      new ActivityResultContracts.StartActivityForResult(),
      new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
          int resultCode = o.getResultCode();
          Intent data = o.getData();

          if (resultCode == RESULT_OK && data != null) {
            String title = data.getStringExtra("lastTitle");
            String desc = data.getStringExtra("lastDesc");
            int noteId = data.getIntExtra("lastId", -1);

            Note note = new Note(title, desc);
            note.setId(noteId);
            noteViewModel.update(note);
          }
        }
      });
  }
}